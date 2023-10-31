package com.example.clients.iiko.models

import com.example.clients.iiko.models.ProgramType.ProgramType

import java.util.UUID

/** Information about guest balance.
  */
case class GuestBalanceInfo(
    /* Wallet id. */
    id: Option[UUID] = None,
    /* Wallet name. */
    name: Option[String] = None,
    `type`: Option[ProgramType] = None,
    /* Wallet balance. */
    balance: Option[Double] = None,
  )
