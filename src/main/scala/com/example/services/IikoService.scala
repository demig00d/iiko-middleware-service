package com.example.services

import com.example.clients.iiko.IikoClient
import com.example.clients.iiko.models._
import com.example.utils.RequestSender._
import com.example.{AppError, IikoConfig}
import sttp.client3.SttpBackend
import sttp.client3.logging.slf4j.Slf4jLoggingBackend
import zio._

import java.time.LocalDate

trait IikoService {
  def getIikoCustomerInfo(
      getCustomerInfoRequest: GetCustomerInfoRequest
    ): ZIO[Any, AppError, GetCustomerInfoResponse]

  def getNutritionReport(
      corporateNutritionReportRequest: CorporateNutritionReportRequest
    ): ZIO[Any, AppError, Vector[CorporateNutritionReportItem]]

  def getOrganizationPrograms: ZIO[Any, AppError, Vector[ExtendedCorparateNutritionInfo]]

  def getTransactionsReport(
      dateFrom: LocalDate,
      dateTo: LocalDate,
    ): ZIO[Any, AppError, Vector[TransactionsReportItem]]

  def getShortGuestInfo(
      organizationAndPeriod: OrganizationAndPeriod
    ): ZIO[Any, AppError, Vector[ShortGuestInfo]]

  def listOrganizations: ZIO[Any, AppError, Vector[OrganizationInfo]]

}

object IikoService {
  lazy val live: ZLayer[SttpBackend[Task, Any] with IikoConfig, AppError, IikoService] = {
    val transportTokenDurationExpire = 59.minutes
    val bizTokenDurationExpire       = 15.minutes

    ZLayer.fromZIO {
      for {
        sttpBackend <- ZIO.service[SttpBackend[Task, Any]]
        config      <- ZIO.service[IikoConfig]
        backend = Slf4jLoggingBackend(
          sttpBackend,
          logRequestHeaders = true,
          logRequestBody = true,
          logResponseHeaders = true,
          logResponseBody = true,
          sensitiveHeaders = Set("api-key"),
        )

        refAccessToken <- TokenRefresher.getAccessTokenAndStartRefresh(
          sttpBackend,
          config.iikoTransport.apiLogin,
          transportTokenDurationExpire,
        )

        refBizToken <- TokenRefresher.getBizTokenAndStartRefresh(
          sttpBackend,
          config.iikoBiz.userId,
          config.iikoBiz.userSecret,
          bizTokenDurationExpire,
        )

      } yield IikoServiceLive(backend, refAccessToken, refBizToken, config)
    }
  }

}

case class IikoServiceLive(
    sttpBackend: SttpBackend[Task, Any],
    accessToken: Ref[AccessToken],
    bizToken: Ref[BizToken],
    config: IikoConfig,
  ) extends IikoService {
  override def getIikoCustomerInfo(
      getCustomerInfoRequest: GetCustomerInfoRequest
    ): ZIO[Any, AppError, GetCustomerInfoResponse] =
    for {
      token <- accessToken.get
      response <-
        IikoClient
          .transportApi
          .api1LoyaltyIikoCustomerInfoPost(token.value, getCustomerInfoRequest)
          .sendReq(sttpBackend)
    } yield response

  override def getNutritionReport(
      corporateNutritionReportRequest: CorporateNutritionReportRequest
    ): ZIO[Any, AppError, Vector[CorporateNutritionReportItem]] =
    for {
      token <- bizToken.get
      response <-
        IikoClient
          .bizApi
          .api0CorporateNutritionReportGet(token, corporateNutritionReportRequest)
          .sendReq(sttpBackend)
    } yield response

  override def getOrganizationPrograms: ZIO[Any, AppError, Vector[ExtendedCorparateNutritionInfo]] =
    for {
      token <- bizToken.get
      response <-
        IikoClient
          .bizApi
          .api0OrganizationProgramsGet(
            bizToken = token,
            organizationId = config.iikoBiz.organizationId,
          )
          .sendReq(sttpBackend)
    } yield response

  override def getTransactionsReport(
      dateFrom: LocalDate,
      dateTo: LocalDate,
    ): ZIO[Any, AppError, Vector[TransactionsReportItem]] =
    for {
      token <- bizToken.get
      response <-
        IikoClient
          .bizApi
          .api0TransactionsReportGet(
            token,
            TransactionReportRequest(
              organizationId = config.iikoBiz.organizationId,
              dateFrom = dateFrom,
              dateTo = dateTo,
            ),
          )
          .sendReq(sttpBackend)
    } yield response

  def getShortGuestInfo(
      organizationAndPeriod: OrganizationAndPeriod
    ): ZIO[Any, AppError, Vector[ShortGuestInfo]] =
    for {
      token <- bizToken.get
      response <-
        IikoClient
          .bizApi
          .api0GetCustomersByOrganizationAndByPeriodGet(token, organizationAndPeriod)
          .sendReq(sttpBackend)
    } yield response

  def listOrganizations: ZIO[Any, AppError, Vector[OrganizationInfo]] =
    for {
      token <- bizToken.get
      response <-
        IikoClient
          .bizApi
          .api0OrganizationsList(token)
          .sendReq(sttpBackend)
    } yield response

}
