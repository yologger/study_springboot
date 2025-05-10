package com.yologger.study.springboot_jpa.service

import com.yologger.study.springboot_jpa.entity.Board
import com.yologger.study.springboot_jpa.entity.User
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InitService(
    @Autowired private val entityManager: EntityManager
) {
    @Transactional
    fun init() {
        val userA = User(name="ronaldo", nation="portugal", age=40)
        val userB = User(name="messi", nation="argentina", age=38)
        entityManager.persist(userA)
        entityManager.persist(userB)

        for (i in 1..100) {
            val selectedUser = if (i % 2 == 0) userA else userB
            entityManager.persist(Board(title = "title$i", body = "board$i", user = selectedUser))
        }
    }
}