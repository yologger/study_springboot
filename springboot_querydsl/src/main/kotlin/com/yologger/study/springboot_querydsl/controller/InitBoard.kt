package com.yologger.study.springboot_querydsl.controller

import com.yologger.study.springboot_querydsl.service.InitBoardService
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("local")
class InitBoard (
    @Autowired private val initBoardService: InitBoardService
){
    @PostConstruct
    fun setup() {
        initBoardService.setup()
    }
}