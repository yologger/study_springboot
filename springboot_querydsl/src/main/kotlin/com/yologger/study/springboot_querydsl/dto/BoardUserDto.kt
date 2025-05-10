package com.yologger.study.springboot_querydsl.dto

import com.querydsl.core.annotations.QueryProjection

data class BoardUserDto @QueryProjection constructor(
    val boardId: Long,
    val title: String,
    val body: String,
    val username: String
) {
    override fun toString(): String {
        return "($username, $boardId, $title, $body)"
    }
}