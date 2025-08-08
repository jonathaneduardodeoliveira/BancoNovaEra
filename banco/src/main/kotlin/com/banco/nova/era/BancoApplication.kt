package com.banco.nova.era

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BancoApplication

fun main(args: Array<String>) {
	runApplication<BancoApplication>(*args)
}
