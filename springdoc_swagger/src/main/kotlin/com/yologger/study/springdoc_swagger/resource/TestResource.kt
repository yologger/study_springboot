package com.yologger.study.springdoc_swagger.resource

import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Test API", description = "Test API 입니다")
@RestController
@RequestMapping("/api/test/v1")
class TestResource {


    @Operation(summary = "test1 문자열 반환", description = "'test1' 라는 문자열을 반환합니다")
    @GetMapping("/test1")
    fun test1(): String {
        return "test1"
    }

    @GetMapping("/test2")
    fun test2(): String {
        return "test2"
    }

    @Hidden
    @GetMapping("/test3")
    fun test3(): String {
        return "test3"
    }

    @GetMapping("/echo/{message}")
    fun echo(@PathVariable message: String): String {
        return message
    }
}
