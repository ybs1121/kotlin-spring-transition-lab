package com.example.lab

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class KotlinLabApplication

fun main(args: Array<String>) {
    runApplication<KotlinLabApplication>(*args)
}
