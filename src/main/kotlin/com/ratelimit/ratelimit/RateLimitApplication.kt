package com.ratelimit.ratelimit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class RateLimitApplication

fun main(args: Array<String>) {
    runApplication<RateLimitApplication>(*args)
}
