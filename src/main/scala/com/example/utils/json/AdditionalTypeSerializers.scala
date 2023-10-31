package com.example.utils.json

import java.net.{URI, URISyntaxException}
import scala.util.{Failure, Success}

trait AdditionalTypeSerializers {
  import io.circe._

  final implicit lazy val URIDecoder: Decoder[URI] = Decoder
    .decodeString
    .emap(string =>
      try Right(new URI(string))
      catch {
        case _: URISyntaxException =>
          Left("String could not be parsed as a URI reference, it violates RFC 2396.")
        case _: NullPointerException =>
          Left("String is null.")
      }
    )

  final implicit lazy val URIEncoder: Encoder[URI] = new Encoder[URI] {
    final def apply(a: URI): Json = Json.fromString(a.toString)

  }

  def decodeIntToEnumeration[E <: Enumeration](e: E): Decoder[E#Value] =
    Decoder.decodeInt.emapTry {
      case num if num >= 0 && num < e.values.knownSize => Success(e.apply(num))
      case wrong =>
        Failure(
          new Exception(s"${e.toString} got $wrong should be in 0..${e.values.knownSize - 1} range")
        )
    }

}
