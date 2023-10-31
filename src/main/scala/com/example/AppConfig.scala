package com.example

import com.example.clients.iiko.models.ApiLogin
import zio.ZLayer
import zio.config._
import zio.config.magnolia.{Descriptor, descriptor}
import zio.http._

import java.util.UUID

case class IikoConfig(
    iikoBiz: IikoBizConfig,
    iikoTransport: IikoTransportConfig,
  )
case class IikoTransportConfig(apiLogin: ApiLogin, organizationId: UUID)
case class IikoBizConfig(
    userId: String,
    userSecret: String,
    organizationId: UUID,
  )
case class DBConfig(
    host: String,
    port: Int,
    name: String,
    user: String,
    password: String,
  ) {
  val url = s"jdbc:postgresql://$host:$port/$name"

}
case class ServerConfig(
    port: Int
  ) {
  def toZIOHttp: Server.Config =
    Server
      .Config
      .default
      .port(port = 8088)

}

object AppConfig {
  implicit val apiLoginDesc: Descriptor[ApiLogin] =
    Descriptor[String].transform(ApiLogin.apply, _.value)

  private def toSnakeCaseAddPrefix(prefix: String): String => String =
    toSnakeCase
      .compose(
        addPrefixToKey(
          prefix
        )
      )

  private val bizDesc: ConfigDescriptor[IikoBizConfig] =
    descriptor[IikoBizConfig]
      .mapKey(toSnakeCaseAddPrefix("iiko_biz_"))
  private val transportDesc: ConfigDescriptor[IikoTransportConfig] =
    descriptor[IikoTransportConfig]
      .mapKey(toSnakeCaseAddPrefix("iiko_transport_"))

  private val dbDesc: ConfigDescriptor[DBConfig] =
    descriptor[DBConfig]
      .mapKey(toSnakeCaseAddPrefix("db_"))

  private val serverDesc: ConfigDescriptor[ServerConfig] =
    descriptor[ServerConfig]
      .mapKey(toSnakeCaseAddPrefix("server_"))

  type AppConfigurations = IikoConfig with DBConfig with Server.Config

  // TODO fix: system variables are probably read twice
  // first to configSource, then to fromSystemEnv(dbDesc)
  def fromEnv: ZLayer[Any, ReadError[String], AppConfigurations] = {
    val configSource = ConfigSource.fromSystemEnv()
    val iikoConfig = for {
      iikoBiz       <- read(bizDesc.from(configSource))
      iikoTransport <- read(transportDesc.from(configSource))
    } yield IikoConfig(iikoBiz, iikoTransport)

    val db     = read(dbDesc.from(configSource))
    val server = read(serverDesc.from(configSource))

    ZLayer.fromZIO(iikoConfig) ++ ZLayer.fromZIO(db) ++ ZLayer.fromZIO(server.map(_.toZIOHttp))
  }

}
