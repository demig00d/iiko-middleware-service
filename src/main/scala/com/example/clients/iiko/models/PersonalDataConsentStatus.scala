package com.example.clients.iiko.models

object PersonalDataConsentStatus extends Enumeration {
  type PersonalDataConsentStatus = PersonalDataConsentStatus.Value
  val Unknown = Value(0)
  val Given   = Value(1)
  val Revoked = Value(2)

}
