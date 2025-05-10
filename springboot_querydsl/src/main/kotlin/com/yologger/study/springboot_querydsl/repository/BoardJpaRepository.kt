package com.yologger.study.springboot_querydsl.repository

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.yologger.study.springboot_querydsl.dto.BoardSearchCondition
import com.yologger.study.springboot_querydsl.dto.BoardUserDto
import com.yologger.study.springboot_querydsl.dto.QBoardUserDto
import com.yologger.study.springboot_querydsl.entity.Board
import com.yologger.study.springboot_querydsl.entity.QBoard
import com.yologger.study.springboot_querydsl.entity.QBoard.board
import com.yologger.study.springboot_querydsl.entity.QUser.user
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils

@Repository
class BoardJpaRepository(
    @Autowired private val entityManager: EntityManager,

) {

    private val jpaQueryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)
    private val qBoard = QBoard.board

    fun save(board: Board) {
        entityManager.persist(board)

    }

    fun findById(id: Long): Board? {
        return entityManager.find(Board::class.java, id)
    }

    fun findAll(): List<Board>? {
        return jpaQueryFactory.selectFrom(qBoard).fetch()
    }

    fun findByName(title: String):  List<Board>? {
        return jpaQueryFactory
            .selectFrom(qBoard)
            .where(qBoard.title.eq(title))
            .fetch()
    }

    fun search(condition: BoardSearchCondition): List<BoardUserDto>? {

        return jpaQueryFactory
            .select(QBoardUserDto(
                board.id.`as`("boardId"),
                board.title,
                board.body,
                user.name.`as`("username")
            ))
            .from(board)
            .leftJoin(board.user, user)
            .where(usernameEq(condition.username), titleEq(condition.title), bodyEq(condition.body))
            .fetch()
    }

    private fun usernameEq(username: String): BooleanExpression? {
        return if (StringUtils.hasText(username)) user.name.eq(username) else null
    }

    private fun titleEq(title: String): BooleanExpression? {
        return if (StringUtils.hasText(title)) board.title.eq(title) else null
    }

    private fun bodyEq(body: String): BooleanExpression? {
        return if (StringUtils.hasText(body)) board.body.eq(body) else null
    }
}