package com.yologger.study.springboot

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class AppRunner (

): ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        println("Hello World")
    }
}