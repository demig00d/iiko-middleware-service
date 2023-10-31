package com.example.utils.json

import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime, OffsetDateTime}

trait DateSerializers {
  import io.circe.{Decoder, Encoder}
  implicit val isoOffsetDateTimeDecoder: Decoder[OffsetDateTime] =
    Decoder.decodeOffsetDateTimeWithFormatter(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
  implicit val isoOffsetDateTimeEncoder: Encoder[OffsetDateTime] =
    Encoder.encodeOffsetDateTimeWithFormatter(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

  implicit val localDateDecoder: Decoder[LocalDate] =
    Decoder.decodeLocalDateWithFormatter(DateTimeFormatter.ISO_LOCAL_DATE)
  implicit val localDateEncoder: Encoder[LocalDate] =
    Encoder.encodeLocalDateWithFormatter(DateTimeFormatter.ISO_LOCAL_DATE)

  private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
  implicit val dateTimeDecoder: Decoder[LocalDateTime] =
    Decoder.decodeLocalDateTimeWithFormatter(formatter)
  implicit val dateTimeEncoder: Encoder[LocalDateTime] =
    Encoder.encodeLocalDateTimeWithFormatter(formatter)

}
