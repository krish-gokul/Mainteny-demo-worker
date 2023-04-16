package com.mainteny.worker.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Payments(
  @Id
  @GeneratedValue
  var id: Long = 0,
  var clientRequestId: String = "",
  var amount: Long = 0,
  var createdAt: LocalDateTime = LocalDateTime.now()

)
