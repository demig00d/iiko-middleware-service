package com.example.clients.iiko

import com.example.clients.iiko.JsonSupport._
import com.example.clients.iiko.models._
import sttp.client3._

import java.util.UUID

object BizApi {
  def apply(baseUrl: String = "https://iiko.biz:9900") = new BizApi(baseUrl)

}

class BizApi(baseUrl: String) {

  /** Expected answers:
    *   code 200 : GetOrganizationsResponse (Success)
    *   code 400 : ErrorResponse (Bad Request)
    *   code 401 : ErrorResponse (Unauthorized)
    *   code 500 : ErrorResponse (Server Error)
    *   code 408 : ErrorResponse (Request Timeout)
    *
    * @param timeout Timeout in seconds.
    * @param authRequest request
    */
  def api0Auth(
      authRequest: AuthRequest,
      timeout: Option[Int] = None,
    ): Request[Either[String, String], Any] = {
    import authRequest._
    val ps = Map(
      "user_id"     -> userId,
      "user_secret" -> userSecret,
    )

    basicRequest
      .get(uri"$baseUrl/api/0/auth/access_token?$ps")
      .header("Timeout", timeout.toString)
      .response(asString)
  }

  /** Expected answers:
    *   code 200 : Vector[CorporateNutritionReportItem] (Success)
    *   code 400 : ErrorResponse (Bad Request)
    *   code 401 : ErrorResponse (Unauthorized)
    *   code 500 : ErrorResponse (Server Error)
    *   code 408 : ErrorResponse (Request Timeout)
    *
    * @param bizToken Authorization token.
    * @param timeout Timeout in seconds.
    * @param corporateNutritionReportRequest request
    */
  def api0CorporateNutritionReportGet(
      bizToken: BizToken,
      corporateNutritionReportRequest: CorporateNutritionReportRequest,
      timeout: Option[Int] = None,
    ): Request[Either[ResponseException[String, Exception], Vector[CorporateNutritionReportItem]], Any] = {
    import corporateNutritionReportRequest._

    val ps = Map(
      "corporate_nutrition_id" -> corporateNutritionProgramId,
      "date_from"              -> dateFrom,
      "date_to"                -> dateTo,
      "access_token"           -> bizToken.value,
    )

    basicRequest
      .get(uri"$baseUrl/api/0/organization/${organizationId}/corporate_nutrition_report?$ps")
      .header("Timeout", timeout.toString)
      .response(asJson[Vector[CorporateNutritionReportItem]])
  }

  // Get programs by organization or network
  def api0OrganizationProgramsGet(
      bizToken: BizToken,
      timeout: Option[Int] = None,
      organizationId: UUID,
    ): Request[Either[ResponseException[String, Exception], Vector[ExtendedCorparateNutritionInfo]], Any] =
    basicRequest
      .get(
        uri"$baseUrl/api/0/organization/programs?access_token=${bizToken.value}&organization=$organizationId"
      )
      .header("Timeout", timeout.toString)
      .response(asJson[Vector[ExtendedCorparateNutritionInfo]])

  // Get a report on transactions of the organization's guests for specific period
  def api0TransactionsReportGet(
      bizToken: BizToken,
      transactionReportRequest: TransactionReportRequest,
      timeout: Option[Int] = None,
    ): Request[Either[ResponseException[String, Exception], Vector[TransactionsReportItem]], Any] = {
    import transactionReportRequest._

    val ps = Map(
      "date_from"    -> dateFrom,
      "date_to"      -> dateTo,
      "user_id"      -> userId,
      "access_token" -> bizToken.value,
    )

    basicRequest
      .get(uri"$baseUrl/api/0/organization/$organizationId/transactions_report?$ps")
      .header("Timeout", timeout.toString)
      .response(asJson[Vector[TransactionsReportItem]])
  }

  // Get information on guests for the period (maximum 100 days)
  def api0GetCustomersByOrganizationAndByPeriodGet(
      bizToken: BizToken,
      organizationAndPeriod: OrganizationAndPeriod,
      timeout: Option[Int] = None,
    ): Request[Either[ResponseException[String, Exception], Vector[ShortGuestInfo]], Any] = {
    import organizationAndPeriod._

    val ps = Map(
      "date_from"    -> dateFrom,
      "date_to"      -> dateTo,
      "access_token" -> bizToken.value,
      "organization" -> organizationId,
    )

    basicRequest
      .get(uri"$baseUrl/api/0/customers/get_customers_by_organization_and_by_period?$ps")
      .header("Timeout", timeout.toString)
      .response(asJson[Vector[ShortGuestInfo]])
  }

  // Get list of organizations
  def api0OrganizationsList(
      bizToken: BizToken
    ): Request[Either[ResponseException[String, Exception], Vector[OrganizationInfo]], Any] =
    basicRequest
      .get(uri"$baseUrl/api/0/organization/list?access_token=${bizToken.value}")
      .response(asJson[Vector[OrganizationInfo]])

}
