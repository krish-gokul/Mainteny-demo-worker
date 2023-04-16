package com.mainteny.worker.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class PaymentStates(
  @Id
  @GeneratedValue
  var id: Long = 0,
  var state: String = "",
  @ManyToOne
  var payment: Payments = Payments(),
  var createdAt: LocalDateTime = LocalDateTime.now()

)
