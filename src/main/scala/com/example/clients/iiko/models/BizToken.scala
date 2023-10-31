package com.example.clients.iiko.models

class BizToken(val value: String) extends AnyVal
object BizToken {
  def apply(value: String): BizToken = new BizToken(value.replace("\"", ""))

}
