package com.example.clients.iiko.models

import java.time.LocalDateTime
import java.util.UUID

case class TransactionsReportItem(
    PosBalanceBefore: Option[Double],
    apiClientLogin: Option[String],
    balanceAfter: Option[Double],
    balanceBefore: Option[Double],
    cardNumbers: Option[String], // The number of the customer's card in the organization. If there are several cards, they are stored separated by commas.
    certificateBlockReason: Option[String],
    certificateCounteragent: Option[String],
    certificateCounteragentType: Option[String],
    certificateEmitentName: Option[String],
    certificateNominal: Option[Double],
    certificateNumber: Option[String],
    certificateStatus: String,
    certificateType: Option[String],
    comment: Option[String],
    couponNumber: Option[String],
    couponSeries: Option[String],
    iikoBizUser: Option[String],
    marketingCampaignName: Option[String],
    networkId: UUID,
    orderCreateDate: Option[LocalDateTime],
    orderNumber: Option[Long],
    orderSum: Double,
    organizationId: Option[UUID],
    phoneNumber: String, // The phone number of the customer who placed the order.
    programName: Option[String],
    transactionCreateDate: LocalDateTime,
    transactionSum: Double,
    transactionType: String,
  )
