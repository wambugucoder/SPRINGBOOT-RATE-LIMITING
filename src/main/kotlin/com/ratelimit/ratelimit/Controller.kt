package com.ratelimit.ratelimit

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {

    @GetMapping("/start")
    fun sayHello():String{
        return "Try Me 3 times within 30 seconds"
    }
}