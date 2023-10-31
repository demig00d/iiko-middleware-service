package com.example.clients.iiko.models

/** Corporate program type.
  */

object ProgramType extends Enumeration {
  type ProgramType = ProgramType.Value
  val DepositOrCorporateNutrition = Value(0)
  val BonusProgram                = Value(1)
  val ProductsProgram             = Value(2)
  val DiscountProgram             = Value(3)
  val CertificateProgram          = Value(4)

}
