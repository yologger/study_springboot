package com.yologger.study.springboot_jpa.controller

import com.yologger.study.springboot_jpa.entity.User
import com.yologger.study.springboot_jpa.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    @Autowired private val userRepository: UserRepository
) {

    @GetMapping("/test")
    fun test(): String {
        val users = userRepository.findAll()
        for (user in users) {
            for (board in user.boards) println(board)
        }
        return "Test"
    }
}