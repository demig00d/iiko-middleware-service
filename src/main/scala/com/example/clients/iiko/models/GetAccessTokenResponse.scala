package com.example.clients.iiko.models

import java.util.UUID

/** Response to authentication token request.
  */
case class GetAccessTokenResponse(
    /* Operation ID. */
    correlationId: UUID,
    /* Authentication token. The standard token lifetime is 1 hour. */
    token: String,
  )
