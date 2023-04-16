package com.mainteny.worker.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class PendingPaymentStates(
  @Id
  @GeneratedValue
  var id: Long = 0,
  @ManyToOne
  var paymentStates: PaymentStates = PaymentStates()
)
