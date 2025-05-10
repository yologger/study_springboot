package com.yologger.study.springboot_querydsl.repository

import com.yologger.study.springboot_querydsl.dto.BoardSearchCondition
import com.yologger.study.springboot_querydsl.dto.BoardUserDto
import com.yologger.study.springboot_querydsl.entity.Board
import com.yologger.study.springboot_querydsl.entity.User
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class BoardJpaRepositoryTest(
    @Autowired private val entityManager: EntityManager,
    @Autowired private val boardJpaRepository: BoardJpaRepository
) {

    @BeforeEach
    fun beforeEach() {
        val user = User(name = "ronaldo", age = 40, nation = "portugal")
        entityManager.persist(user)

        val board1 = Board(title = "title a", body = "body1", user = user)
        val board2 = Board(title = "title a", body = "body2", user = user)
        val board3 = Board(title = "title b", body = "body3", user = user)
        val board4 = Board(title = "title c", body = "body4", user = user)
        entityManager.persist(board1)
        entityManager.persist(board2)
        entityManager.persist(board3)
        entityManager.persist(board4)
    }

    @Test
    fun test() {
        val results1: List<BoardUserDto>? = boardJpaRepository.search(BoardSearchCondition(username = "ronaldo"))
        val results2: List<BoardUserDto>? = boardJpaRepository.search(BoardSearchCondition(username = "ronaldo", title = "title a"))
        val results3: List<BoardUserDto>? = boardJpaRepository.search(BoardSearchCondition(username = "ronaldo", title = "title a", body = "body1"))

        println(results1)
        println(results2)
        println(results3)
    }
}