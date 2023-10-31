package com.example

import com.example.repository.{QuillDataSource, TransactionRepository}
import com.example.server.AppServer
import com.example.services.{IikoService, UpdaterService}
import io.getquill.CamelCase
import io.getquill.jdbczio.Quill
import sttp.client3.httpclient.zio.HttpClientZioBackend
import zio._
import zio.http._

object Main extends ZIOAppDefault {
  private lazy val app =
    for {
      updater <- ZIO.service[UpdaterService]
      _ <- updater
        .updateTodayTransactions
        .repeat(Schedule.spaced(1.hour))
        .fork
      _ <- AppServer.start

    } yield ()

  def run: ZIO[Any, Serializable, ExitCode] = app
    .provide(
      AppConfig.fromEnv,
      HttpClientZioBackend.layer(),
      IikoService.live,
      Quill.Postgres.fromNamingStrategy(CamelCase),
      QuillDataSource.layer,
      Server.live,
      TransactionRepository.live,
      UpdaterService.live,
    )
    .debug("Results")
    .exitCode

}
