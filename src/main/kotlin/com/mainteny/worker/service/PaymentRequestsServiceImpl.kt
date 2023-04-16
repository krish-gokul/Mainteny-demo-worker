package com.mainteny.worker.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.mainteny.worker.entity.PaymentStates
import com.mainteny.worker.entity.Payments
import com.mainteny.worker.entity.PendingPaymentStates
import com.mainteny.worker.repo.PaymentsRepo
import com.mainteny.worker.repo.PendingPaymentsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.util.*
import javax.transaction.Transactional

@Service
@EnableScheduling
class PaymentRequestsServiceImpl(
  @Autowired private val paymentsRepo: PaymentsRepo,
  @Autowired private var mapper: ObjectMapper,
  @Autowired private var pendingPaymentStatesRepository: PendingPaymentsRepository
) :
  PaymentRequestsService {

  @Scheduled(cron = "\${post.payment.cron.expression}")
  @Transactional
  override fun postPaymentRequests() {

    var payment = Payments()
    val clientRequestId = UUID.randomUUID().toString()

    payment.clientRequestId = clientRequestId
    payment.amount = (100..10000).random().toLong()

    println("Initiating Payment request")
    val restTemplate = RestTemplate()
    val headers = HttpHeaders()
    headers.contentType = MediaType.APPLICATION_JSON
    val entity = HttpEntity(payment, headers)
    val response = restTemplate.postForObject("http://localhost:8080/payment", entity, String::class.java)
    var paymentResponse: Payments = mapper.readValue(response, Payments::class.java)

    val statusResponse: ResponseEntity<PaymentStates> = restTemplate.getForEntity<PaymentStates>("http://localhost:8080/payment/status/$clientRequestId", entity, String::class.java)
    val status: PaymentStates = statusResponse.body!!

    when (status.state) {
      "ACCEPTED" -> println("Payment request under process : $paymentResponse")
      "SUCCESS" -> println("Payment successful for request : $paymentResponse")
      "FAILED" -> println("Payment failed for request : $paymentResponse")
    }
  }

  @Scheduled(cron = "\${process.successful.payment.cron.expression}")
  @Transactional
  override fun processSuccessfulPaymentRequests() {
    println("Cron job started to fetch successful payment requests")
    var pendingSuccessfulPaymentStates: List<PendingPaymentStates> =
      pendingPaymentStatesRepository.findPendingPaymentStatesByPaymentStatesState("SUCCESS")
      pendingSuccessfulPaymentStates.forEach{
        println("Payment successful and order placed for payment request : $it")
        pendingPaymentStatesRepository.delete(it)
    }
  }

  @Scheduled(cron = "\${process.failed.payment.cron.expression}")
  @Transactional
  override fun processFailedPaymentRequests() {
    println("Cron job started to fetch failed payment requests")
    var pendingFailedPaymentStates: List<PendingPaymentStates> =
      pendingPaymentStatesRepository.findPendingPaymentStatesByPaymentStatesState("FAILED")
    pendingFailedPaymentStates.forEach {
      println("Payment failed and order cancelled for payment request : $it")
      pendingPaymentStatesRepository.delete(it)
    }
  }
}
