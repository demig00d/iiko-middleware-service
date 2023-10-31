package com.example.server

import caliban._
import caliban.interop.tapir.HttpInterpreter
import com.example.repository.TransactionRepository
import sttp.tapir.json.circe._
import zio._
import zio.http._
import zio.stream._

trait AppServer {
  def start: ZIO[TransactionRepository with Server, CalibanError.ValidationError, Unit]

}

object AppServer {
  private val graphiql =
    Handler.fromStream(ZStream.fromResource("graphiql.html")).toHttp.withDefaultErrorResponse

  def start: ZIO[TransactionRepository with Server, CalibanError.ValidationError, Unit] = {
    val api: GraphQL[TransactionRepository] = TransactionSchema.api
    for {
      interpreter <- api.interpreter
      _ <- Server.serve(
        Http.collectHttp[Request] {
          case _ -> Root / "api" / "graphql" =>
            ZHttpAdapter.makeHttpService(HttpInterpreter(interpreter))
          case _ -> Root / "graphiql" => AppServer.graphiql
        }
      )
    } yield ()

  }

}
