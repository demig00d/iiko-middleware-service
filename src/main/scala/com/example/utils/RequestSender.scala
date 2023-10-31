package com.example.utils

import com.example.{AppError, RequestError, ValidationError}
import sttp.client3.{Request, SttpBackend}
import zio.{Task, ZIO}

object RequestSender {
  implicit class Sender[Err, Out](request: Request[Either[Err, Out], Any]) {
    def sendReq(sttpBackend: SttpBackend[Task, Any]): ZIO[Any, AppError, Out] =
      sttpBackend
        .send(request)
        .refineOrDie { case e: Exception => RequestError(e) }
        .map(_.body)
        .flatMap {
          case Left(error) => ZIO.fail(ValidationError(error.toString))
          case Right(data) => ZIO.succeed(data)
        }

  }

}
