package com.yologger.study.springboot_webflux.dto

data class ProductDto(
    var uid: Long,
    var pid: Long,
    var title: String,
    var description: String,
)