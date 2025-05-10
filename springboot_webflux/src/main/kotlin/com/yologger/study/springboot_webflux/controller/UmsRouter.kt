package com.yologger.study.springboot_webflux.controller

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class UmsRouter {

    @Bean
    fun umsRoute(umsHandler: UmsHandler): RouterFunction<ServerResponse> {
        return org.springframework.web.reactive.function.server.RouterFunctions
            .route()
            .GET("/api/ums/user/{uid}", umsHandler::getUserById)
            .GET("/api/ums/users", umsHandler::getUsers)
            .build()
    }
}