package com.mainteny.worker.service

interface PaymentRequestsService {
  fun postPaymentRequests()
  fun processSuccessfulPaymentRequests()
  fun processFailedPaymentRequests()
}
