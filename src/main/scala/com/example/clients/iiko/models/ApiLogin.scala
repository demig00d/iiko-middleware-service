package com.example.clients.iiko.models

case class ApiLogin(value: String) extends AnyVal {
  def toGetAccessTokenRequest: GetAccessTokenRequest =
    GetAccessTokenRequest(value)

}
