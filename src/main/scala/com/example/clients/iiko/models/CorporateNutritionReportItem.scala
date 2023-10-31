package com.example.clients.iiko.models

import java.util.UUID

case class CorporateNutritionReportItem(
    balanceOnPeriodEnd: Double,
    balanceOnPeriodStart: Double,
    balanceRefillSum: Double,
    balanceResetSum: Double,
    guestCardTrack: Option[String],
    guestCategoryNames: String,
    guestId: UUID,
    guestName: String,
    guestPhone: String,
    paidOrdersCount: Double,
    payFromWalletSum: Double,
  )
