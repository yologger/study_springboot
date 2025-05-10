package com.yologger.study.springboot_querydsl.dto

import com.querydsl.core.annotations.QueryProjection

data class UserDto @QueryProjection constructor(
    var name: String,
    var age: Long,
)