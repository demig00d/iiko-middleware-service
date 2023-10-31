package com.example.clients.iiko.models

/** Authentication token request.
  */
case class GetAccessTokenRequest(
    /* API key. It is set in iikoWeb. */
    apiLogin: String
  )
