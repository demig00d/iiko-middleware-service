package com.example.clients.iiko

import com.example.clients.iiko.JsonSupport._
import com.example.clients.iiko.models.{
  ApiLogin,
  GetAccessTokenResponse,
  GetCustomerInfoRequest,
  GetCustomerInfoResponse,
}
import sttp.client3._
import sttp.model.Method

object TransportApi {
  def apply(baseUrl: String = "https://api-ru.iiko.services") = new TransportApi(baseUrl)

}

class TransportApi(baseUrl: String) {

  /** Expected answers:
    *   code 200 : GetAccessTokenResponse (Success)
    *   code 400 : ErrorResponse (Bad Request)
    *   code 401 : ErrorResponse (Unauthorized)
    *   code 500 : ErrorResponse (Server Error)
    *   code 408 : ErrorResponse (Request Timeout)
    *
    * @param apiLogin
    * @param timeout Timeout in seconds.
    */
  def api1AccessTokenPost(
      apiLogin: ApiLogin,
      timeout: Option[Int] = None,
    ): Request[Either[ResponseException[String, Exception], GetAccessTokenResponse], Any] =
    basicRequest
      .method(Method.POST, uri"$baseUrl/api/1/access_token")
      .contentType("application/json")
      .header("Timeout", timeout.toString)
      .body(apiLogin.toGetAccessTokenRequest)
      .response(asJson[GetAccessTokenResponse])

  /** Get customer info by specified criterion.
    *
    * Expected answers:
    * code 200 : GetCustomerInfoResponse (Success)
    * code 400 : ErrorResponse (Bad Request)
    * code 401 : ErrorResponse (Unauthorized)
    * code 500 : ErrorResponse (Server Error)
    * code 408 : ErrorResponse (Request Timeout)
    *
    * @param authorization Authorization token.
    * @param getCustomerInfoRequest
    * @param timeout       Timeout in seconds.
    */
  def api1LoyaltyIikoCustomerInfoPost(
      authorization: String,
      getCustomerInfoRequest: GetCustomerInfoRequest,
      timeout: Option[Int] = None,
    ): Request[Either[ResponseException[String, Exception], GetCustomerInfoResponse], Any] =
    basicRequest
      .method(Method.POST, uri"$baseUrl/api/1/loyalty/iiko/customer/info")
      .contentType("application/json")
      .header("Authorization", s"Bearer $authorization")
      .header("Timeout", timeout.toString)
      .body(getCustomerInfoRequest)
      .response(asJson[GetCustomerInfoResponse])

}
