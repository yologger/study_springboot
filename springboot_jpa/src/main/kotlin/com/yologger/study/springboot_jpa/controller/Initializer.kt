package com.yologger.study.springboot_jpa.controller

import com.yologger.study.springboot_jpa.service.InitService
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

// @Component
class Initializer(
    @Autowired private val initService: InitService
) {
    @PostConstruct
    fun setup() {
        initService.init()
    }
}