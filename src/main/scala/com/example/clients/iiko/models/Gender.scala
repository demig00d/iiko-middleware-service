package com.example.clients.iiko.models

/** Sex.
  */
object Gender extends Enumeration {
  type Gender = Gender.Value
  val NotSpecified = Value(0)
  val Male         = Value(1)
  val Female       = Value(2)

}
