package com.example

sealed trait AppError

final case class RepositoryError(cause: Exception) extends AppError
final case class ValidationError(msg: String)      extends AppError
final case class RequestError(cause: Throwable)    extends AppError
