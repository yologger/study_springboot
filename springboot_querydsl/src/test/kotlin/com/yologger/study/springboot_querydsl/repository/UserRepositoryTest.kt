package com.yologger.study.springboot_querydsl.repository

import com.yologger.study.springboot_querydsl.dto.BoardSearchCondition
import com.yologger.study.springboot_querydsl.dto.BoardUserDto
import com.yologger.study.springboot_querydsl.entity.Board
import com.yologger.study.springboot_querydsl.entity.QBoard
import com.yologger.study.springboot_querydsl.entity.QBoard.board
import com.yologger.study.springboot_querydsl.entity.User
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class UserRepositoryTest(
    @Autowired private val entityManager: EntityManager,
    @Autowired private val userRepository: UserRepository
) {

    @BeforeEach
    fun beforeEach() {
        entityManager.persist(User(name = "ronaldo", age = 40, nation = "portugal"))
        entityManager.persist(User(name = "messi", age = 38, nation = "argentina"))
        entityManager.persist(User(name = "son", age = 33, nation = "south korea"))
        entityManager.persist(User(name = "kane", age = 32, nation = "england"))
    }

    @Test
    fun test() {
        val users = userRepository.selectOrderByAge()

        users.forEach { println(it) }
    }
}