package com.globo.businesscontrol

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(considerNestedRepositories = true)
class BusinessControlApplication

fun main(args: Array<String>) {
	runApplication<BusinessControlApplication>(*args)
}
