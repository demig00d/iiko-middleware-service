package com.example.clients.iiko

import com.example.clients.iiko.models.{Gender, PersonalDataConsentStatus, ProgramType}
import com.example.utils.json.{AdditionalTypeSerializers, DateSerializers}
import io.circe.generic.AutoDerivation
import io.circe.{Decoder, Encoder}
import sttp.client3.circe.SttpCirceApi

object JsonSupport
    extends SttpCirceApi
       with AutoDerivation
       with DateSerializers
       with AdditionalTypeSerializers {
  implicit val PersonalDataConsentStatusDecoder
      : Decoder[PersonalDataConsentStatus.PersonalDataConsentStatus] = decodeIntToEnumeration(
    PersonalDataConsentStatus
  )
  implicit val PersonalDataConsentStatusEncoder
      : Encoder[PersonalDataConsentStatus.PersonalDataConsentStatus] =
    Encoder.encodeInt.contramap(_.id)
  implicit val ProgramTypeDecoder: Decoder[ProgramType.ProgramType] = decodeIntToEnumeration(
    ProgramType
  )
  implicit val ProgramTypeEncoder: Encoder[ProgramType.ProgramType] =
    Encoder.encodeInt.contramap(_.id)

  implicit val GenderDecoder: Decoder[Gender.Gender] = decodeIntToEnumeration(Gender)
  implicit val GenderEncoder: Encoder[Gender.Gender] = Encoder.encodeInt.contramap(_.id)

}
