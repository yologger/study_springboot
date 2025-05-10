package com.yologger.study.springdoc_swagger.resource

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Sample API", description = "Sample API 입니다")
@RestController
@RequestMapping("/api/sample/v1")
class SampleResource {

    @GetMapping("/echo/{message}")
    fun echo(@PathVariable message: String): String {
        return message
    }
}