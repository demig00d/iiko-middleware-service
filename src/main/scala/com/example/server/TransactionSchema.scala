package com.example.server

import caliban.schema.Annotations.GQLDescription
import caliban.schema.ArgBuilder.auto._
import caliban.schema.GenericSchema
import caliban.schema.Schema.auto._
import caliban.{ GraphQL, RootResolver, graphQL }
import com.example.clients.iiko.models.TransactionsReportItem
import com.example.repository.TransactionRepository
import zio.ZIO

import java.time.LocalDate

// Type alias required to generate Schema for R in ZIO[R, E, A]
// details https://ghostdogpr.github.io/caliban/docs/schema.html#effects
object Env {
  type TransactionRepo = TransactionRepository

}
import com.example.server.Env._
object TransactionSchema extends GenericSchema[TransactionRepo] {
  import TransactionSchema.auto._

  // schema
  case class TransactionPeriod(
      dateFrom: LocalDate,
      dateTo: LocalDate,
      limit: Int,
      offset: Int,
    )

  case class Queries(
      // transactions
      @GQLDescription("Return all for specific period with pagination")
      transactions: TransactionPeriod => ZIO[TransactionRepo, Nothing, List[
        TransactionsReportItem
      ]]
    )

  // quaries
  def transactionsForPeriod(
      period: TransactionPeriod
    ): ZIO[TransactionRepo, Nothing, List[TransactionsReportItem]] =
    TransactionRepository
      .listTransactionsForPeriod(
        period.dateFrom,
        period.dateTo,
        period.limit,
        period.offset,
      )
      .mapError(_.cause)
      .orDie

  // resolver
  val api: GraphQL[TransactionRepo] =
    graphQL(
      RootResolver(
        Queries(transactions = transactionsForPeriod)
      )
    )

}
