package com.yologger.study.springboot_webflux.controller

import com.yologger.study.springboot_webflux.dto.UserDto
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class UmsHandler {

    fun getUserById(request: ServerRequest): Mono<ServerResponse> {
        val uid = request.pathVariable("uid").toLong()
        val user = UserDto(uid = uid, name = "user$uid", email = "user$uid@gmail.com")
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(user)
    }

    fun getUsers(request: ServerRequest): Mono<ServerResponse> {
        val users = (1L..10L).map {
            UserDto(uid = it, name = "user$it", email = "user$it@gmail.com")
        }
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(users)
    }
}