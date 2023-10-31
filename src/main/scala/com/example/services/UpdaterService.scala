package com.example.services

import com.example.AppError
import com.example.repository.TransactionRepository
import zio.{ZIO, ZLayer}

import java.time.LocalDate

trait UpdaterService {
  def updateTransactionsForPeriod(
      dateFrom: LocalDate,
      dateTo: LocalDate,
    ): ZIO[Any, AppError, List[Long]]

  def updateTodayTransactions: ZIO[Any, AppError, List[Long]]

}

object UpdaterService {
  lazy val live: ZLayer[IikoService with TransactionRepository, Nothing, UpdaterService] =
    ZLayer.fromFunction(UpdaterServiceLive.apply _)

}

case class UpdaterServiceLive(
                               iikoService: IikoService,
                               transactionStorage: TransactionRepository,
  ) extends UpdaterService {
  def updateTransactionsForPeriod(
      dateFrom: LocalDate,
      dateTo: LocalDate,
    ): ZIO[Any, AppError, List[Long]] =
    for {
      report <- iikoService.getTransactionsReport(dateFrom, dateTo)
      res    <- transactionStorage.addTransactions(report.toList)
    } yield res

  def updateTodayTransactions: ZIO[Any, AppError, List[Long]] = {
    val today = LocalDate.now()
    updateTransactionsForPeriod(today, today)
  }

}
