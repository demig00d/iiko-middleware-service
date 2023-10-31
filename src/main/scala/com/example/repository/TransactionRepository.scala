package com.example.repository

import com.example.RepositoryError
import com.example.clients.iiko.models.TransactionsReportItem
import io.getquill._
import io.getquill.jdbczio.Quill
import zio._

import java.time.LocalDate

trait TransactionRepository {
  def listTransactions: ZIO[Any, RepositoryError, List[TransactionsReportItem]]
  def listTransactionsForPeriod(
      dateFrom: LocalDate,
      dateTo: LocalDate,
      limit: Int,
      offset: Int,
    ): ZIO[Any, RepositoryError, List[TransactionsReportItem]]

  def addTransactions(
      transactionsReportItem: List[TransactionsReportItem]
    ): ZIO[Any, RepositoryError, List[Long]]

}

class TransactionRepositoryLive(quill: Quill.Postgres[CamelCase]) extends TransactionRepository {
  import quill._

  private val transactionsSchema = quote {
    querySchema[TransactionsReportItem]("transactions")
  }

  def listTransactions: ZIO[Any, RepositoryError, List[TransactionsReportItem]] =
    run(transactionsSchema).refineOrDie { case e: Exception => RepositoryError(e) }

  def listTransactionsForPeriod(
      dateFrom: LocalDate,
      dateTo: LocalDate,
      limit: Int,
      offset: Int,
    ): ZIO[Any, RepositoryError, List[TransactionsReportItem]] =
    run(
      transactionsSchema
//        .filter(t => t.transactionCreateDate > lift(dateFrom.atTime(0, 0, 0)))
//        .filter(t => t.transactionCreateDate.isBefore(lift(dateTo.atTime(0, 0, 0))))
        .drop(lift(offset))
        .take(lift(limit))
    ).refineOrDie {
      case e: Exception => RepositoryError(e)
    }

  def addTransactions(
      transactionReportItems: List[TransactionsReportItem]
    ): ZIO[Any, RepositoryError, List[Long]] =
    run(
      quote(liftQuery(transactionReportItems)).foreach(r =>
        transactionsSchema.insertValue(r).onConflictIgnore
      )
    ).either.flatMap {
      case Left(e)  => ZIO.fail(RepositoryError(e))
      case Right(x) => ZIO.succeed(x)
    }

}

object TransactionRepository {
  lazy val live: ZLayer[Quill.Postgres[CamelCase], Nothing, TransactionRepository] =
    ZLayer.fromFunction(new TransactionRepositoryLive(_))

  def listTransactions: ZIO[TransactionRepository, RepositoryError, List[TransactionsReportItem]] =
    ZIO.serviceWithZIO[TransactionRepository](_.listTransactions)

  def listTransactionsForPeriod(
      dateFrom: LocalDate,
      dateTo: LocalDate,
      limit: Int,
      offset: Int,
    ): ZIO[TransactionRepository, RepositoryError, List[TransactionsReportItem]] =
    ZIO.serviceWithZIO[TransactionRepository](
      _.listTransactionsForPeriod(
        dateFrom = dateFrom,
        dateTo = dateTo,
        limit = limit,
        offset = offset,
      )
    )

  def addTransactions(
      transactionsReportItem: List[TransactionsReportItem]
    ): ZIO[TransactionRepository, RepositoryError, Unit] =
    ZIO.serviceWithZIO[TransactionRepository](_.addTransactions(transactionsReportItem).ignore)

}
