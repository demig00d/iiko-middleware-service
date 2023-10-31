package com.example.clients.iiko.models

import java.time.LocalDate
import java.util.UUID

case class ExtendedCorparateNutritionInfo(
    id: UUID,                    // Program identifier (required when updating the program)
    name: String,                // Program name
    description: Option[String], // Program description
    serviceFrom: LocalDate,      // Program start date
    serviceTo: Option[LocalDate], // The end of the program. If null then program is not time-limited.
    walletId: Option[UUID],       // Identifier of the wallet linked to the program
    organizationId: Option[UUID], // Organisation identifier
    networkId: UUID,              // Network identifier
    notifyAboutBalanceChanges: Boolean, // An indication that the program sends as sms-notifications about wallet balance changes
    // 0 - Cash program (deposit or corporate meals),
    // 1 - Bonus program (bonuses are accumulated on the account),
    // 2 - Grocery program (the account stores the quantity of goods, e.g. cups of coffee),
    // 3 - Discounts, gifts, combos (no score)
    programType: Int,  // Program type
    isActive: Boolean, // Program status
    // marketingCampaigns: Vector[MarketingCampaignInfo, // Marketing promotions under the program
    appliedOrganizations: Option[Vector[UUID]],
  )
