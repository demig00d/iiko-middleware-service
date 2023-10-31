package com.example.clients.iiko.models

import java.util.UUID

/** Guest card info.
  */
case class GuestCardInfo(
    /* Card id. */
    id: Option[UUID] = None,
    /* Card track. */
    track: Option[String] = None,
    /* Card number. */
    number: Option[String] = None,
    /* Card valid to date. */
    validToDate: Option[String] = None,
  )
