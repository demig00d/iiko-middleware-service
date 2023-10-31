package com.example.clients.iiko.models

import java.time.LocalDate
import java.util.UUID

case class OrganizationAndPeriod(
    organizationId: UUID,
    dateFrom: LocalDate,
    dateTo: LocalDate,
  )
