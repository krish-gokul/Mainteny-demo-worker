package com.mainteny.worker.repo

import com.mainteny.worker.entity.PendingPaymentStates
import org.springframework.data.repository.CrudRepository

interface PendingPaymentsRepository : CrudRepository<PendingPaymentStates, Long> {
  fun findPendingPaymentStatesByPaymentStatesState(state: String): List<PendingPaymentStates>
}
