package com.example.services

import com.example.AppError
import com.example.clients.iiko.IikoClient
import com.example.clients.iiko.models.{AccessToken, ApiLogin, AuthRequest, BizToken}
import com.example.utils.RequestSender._
import sttp.client3.SttpBackend
import zio._

object TokenRefresher {
  def getAccessTokenAndStartRefresh(
      sttpBackend: SttpBackend[Task, Any],
      apiLogin: ApiLogin,
      refreshAfter: Duration,
    ): ZIO[Any, AppError, Ref[AccessToken]] =
    for {
      token          <- getAccessToken(sttpBackend, apiLogin)
      refAccessToken <- Ref.make(token)
      _ <- {
        ZIO.sleep(refreshAfter) *> getAccessToken(sttpBackend, apiLogin)
          .flatMap(refAccessToken.set)
          .repeat(Schedule.spaced(refreshAfter))
      }.fork

    } yield refAccessToken

  def getBizTokenAndStartRefresh(
      sttpBackend: SttpBackend[Task, Any],
      userId: String,
      userSecret: String,
      refreshAfter: Duration,
    ): ZIO[Any, AppError, Ref[BizToken]] =
    for {
      token       <- getBizToken(sttpBackend, userId, userSecret)
      refBizToken <- Ref.make(token)
      _ <- {
        ZIO.sleep(refreshAfter) *> getBizToken(
          sttpBackend,
          userId,
          userSecret,
        ).flatMap(refBizToken.set)
          .repeat(Schedule.spaced(refreshAfter))
      }.fork

    } yield refBizToken

  private def getAccessToken(
      sttpBackend: SttpBackend[Task, Any],
      apiLogin: ApiLogin,
    ): ZIO[Any, AppError, AccessToken] =
    IikoClient
      .transportApi
      .api1AccessTokenPost(apiLogin)
      .sendReq(sttpBackend)
      .map(data => AccessToken(data.token))

  private def getBizToken(
      sttpBackend: SttpBackend[Task, Any],
      bizUserId: String,
      bizUserSecret: String,
    ): ZIO[Any, AppError, BizToken] =
    IikoClient
      .bizApi
      .api0Auth(AuthRequest(bizUserId, bizUserSecret))
      .sendReq(sttpBackend)
      .map(data => BizToken(data))

}
