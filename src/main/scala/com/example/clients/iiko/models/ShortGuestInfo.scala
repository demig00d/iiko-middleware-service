package com.example.clients.iiko.models

import com.example.clients.iiko.models.Gender.Gender

import java.time.{LocalDate, LocalDateTime}
import java.util.UUID

case class ShortGuestInfo(
    id: UUID,
    name: String,
    phone: String,
    birthday: LocalDate,
    email: Option[String],
    surname: String,
    sex: Gender,
    additionalPhones: Vector[String],
    comment: Option[String],
    whenCreated: LocalDateTime,
    lastVisitDate: LocalDateTime,
  )
