package com.yologger.study.springboot_querydsl.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
// @RequestMapping("/test")
class TestController {

    @GetMapping("/test")
    fun test(): String {
        return "test"
    }
}