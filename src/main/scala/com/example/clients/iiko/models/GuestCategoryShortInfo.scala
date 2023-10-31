package com.example.clients.iiko.models

import java.util.UUID

/** Guest category info.
  */
case class GuestCategoryShortInfo(
    /* Category id. */
    id: Option[UUID] = None,
    /* Category name. */
    name: Option[String] = None,
    /* Is category active or not. */
    isActive: Option[Boolean] = None,
    /* Is category default for new guests or not. */
    isDefaultForNewGuests: Option[Boolean] = None,
  )
