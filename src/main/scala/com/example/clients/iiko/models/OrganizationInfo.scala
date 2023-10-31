package com.example.clients.iiko.models

import java.util.UUID

case class OrganizationInfo(
    // contact: ContactInfo, // Free-form contact information.
    // latitude Option[BidDecimal]
    // longitude:  Option[BidDecimal]
    organizationType: Int,
    address: String,
    administratorEmail: String, // Administrator's e-mail address
    administratorName: String,
    averageCheque: Option[String],
    currencyIsoName: String,
    description: String, // Description of the organization given by the owner
    fullName: Option[String],
    homePage: Option[String], // URL
    id: UUID,
    isActive: Boolean,
    logo: Option[String],      // URL
    logoImage: Option[String], // URL
    maxBonus: Int,
    minBonus: Int,
    name: String,            //
    networkId: UUID,         // Network identifier, if the organization is part of a network
    phone: Option[String],   //
    website: Option[String], // URL
    workTime: Option[String], // Work time, is a string consisting of the entries start of work-end of work, separated by ;. The output is denoted by 00:00-00:
  )
