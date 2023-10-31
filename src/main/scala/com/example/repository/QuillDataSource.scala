package com.example.repository

import com.example.DBConfig
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import zio._

object QuillDataSource {
  private def mkDataSource(config: DBConfig): Task[HikariDataSource] =
    for {
      pgDataSource <- ZIO.attempt {
        val dataSource = new org.postgresql.ds.PGSimpleDataSource()
        dataSource.setURL(config.url)
        dataSource.setUser(config.user)
        dataSource.setPassword(config.password)
        dataSource
      }
      hikariConfig <- ZIO.attempt {
        val config = new HikariConfig()
        config.setDataSource(pgDataSource)
        config
      }
      dataSource <- ZIO.attempt(new HikariDataSource(hikariConfig))
    } yield dataSource

  val layer = ZLayer.fromZIO(ZIO.serviceWithZIO[DBConfig](mkDataSource))

}
