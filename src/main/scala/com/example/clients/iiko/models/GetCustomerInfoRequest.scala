package com.example.clients.iiko.models

import java.util.UUID

/** Base class for customer info request.
  */
case class GetCustomerInfoRequest(
    `type`: String,
    /* Customer phone number. Can be null */
    phone: Option[String] = None,
    /* Customer card track. Can be null */
    cardTrack: Option[String] = None,
    /* Customer card number. Can be null */
    cardNumber: Option[String] = None,
    /* Customer email. Can be null */
    email: Option[String] = None,
    /* Customer id. Can be null */
    id: Option[String] = None,
    /* Organization id. */
    organizationId: Option[UUID] = None,
  )
