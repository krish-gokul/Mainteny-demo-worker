package com.mainteny.worker.repo

import com.mainteny.worker.entity.Payments
import org.springframework.data.repository.CrudRepository

interface PaymentsRepo : CrudRepository<Payments, Long>
