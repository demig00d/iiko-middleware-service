package com.example.clients.iiko.models

import com.example.clients.iiko.models.Gender.Gender
import com.example.clients.iiko.models.PersonalDataConsentStatus.PersonalDataConsentStatus

import java.util.UUID

/** Get customer info response.
  */
case class GetCustomerInfoResponse(
    /* Guest id. */
    id: Option[UUID] = None,
    /* Guest referrer id. */
    referrerId: Option[UUID] = None,
    /* Guest name. Can be null. */
    name: Option[String] = None,
    /* Guest surname. Can be null. */
    surname: Option[String] = None,
    /* Guest middle name. Can be null. */
    middleName: Option[String] = None,
    /* Guest comment. Can be null. */
    comment: Option[String] = None,
    /* Main customer's phone. Can be null. */
    phone: Option[String] = None,
    /* Guest culture name. Can be null. */
    cultureName: Option[String] = None,
    /* Guest birthday. */
    birthday: Option[String] = None,
    /* Guest email. Can be null. */
    email: Option[String] = None,
    sex: Option[Gender] = None,
    consentStatus: Option[PersonalDataConsentStatus] = None,
    /* Guest anonymized. */
    anonymized: Option[Boolean] = None,
    /* Customer's cards. */
    cards: Option[Seq[GuestCardInfo]] = None,
    /* Customer categories. */
    categories: Option[Seq[GuestCategoryShortInfo]] = None,
    /* Customer's user wallets. Contains bonus balances of different loyalty programs. */
    walletBalances: Option[Seq[GuestBalanceInfo]] = None,
    /* Technical user data, customizable by restaurateur. Can be null. */
    userData: Option[String] = None,
    /* Customer get promo messages (email, sms). If null - unknown. */
    shouldReceivePromoActionsInfo: Option[Boolean] = None,
    /* Guest should receive loyalty info. */
    shouldReceiveLoyaltyInfo: Option[Boolean] = None,
    /* Guest should receive order status info. */
    shouldReceiveOrderStatusInfo: Option[Boolean] = None,
    /* Guest personal data consent from. */
    personalDataConsentFrom: Option[String] = None,
    /* Guest personal data consent to. */
    personalDataConsentTo: Option[String] = None,
    /* Guest personal data processing from. */
    personalDataProcessingFrom: Option[String] = None,
    /* Guest personal data processing to. */
    personalDataProcessingTo: Option[String] = None,
    /* Customer marked as deleted. */
    isDeleted: Option[Boolean] = None,
  )

object GetCustomerInfoResponseEnums {}
