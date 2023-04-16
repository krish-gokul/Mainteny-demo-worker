package com.mainteny

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
@EnableTransactionManagement
class MaintenyDemoWorkerApplication

fun main(args: Array<String>) {
  runApplication<MaintenyDemoWorkerApplication>(*args)
}
