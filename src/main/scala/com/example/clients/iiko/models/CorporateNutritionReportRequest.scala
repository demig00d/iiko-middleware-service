package com.example.clients.iiko.models

import java.time.LocalDate
import java.util.UUID

case class CorporateNutritionReportRequest(
    organizationId: UUID,
    corporateNutritionProgramId: UUID,
    // dateTo-dateFrom < 365 days
    dateFrom: LocalDate,
    dateTo: LocalDate,
  )
