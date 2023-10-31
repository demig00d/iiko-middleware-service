package com.example.clients.iiko.models

import java.time.LocalDate
import java.util.UUID

case class TransactionReportRequest(
    organizationId: UUID,
    // dateTo-dateFrom < 365
    dateFrom: LocalDate,
    dateTo: LocalDate,
    userId: Option[UUID] = None,
  )
