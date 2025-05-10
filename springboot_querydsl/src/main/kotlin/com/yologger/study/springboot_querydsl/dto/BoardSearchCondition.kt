package com.yologger.study.springboot_querydsl.dto

data class BoardSearchCondition(
    val username: String = "",
    val title: String = "",
    val body: String = "",
)