package com.yologger.study.springboot_querydsl.repository

 import com.querydsl.core.QueryResults
import com.querydsl.core.types.dsl.BooleanExpression
 import com.querydsl.jpa.JPQLQuery
 import com.querydsl.jpa.impl.JPAQuery
 import com.querydsl.jpa.impl.JPAQueryFactory
import com.yologger.study.springboot_querydsl.dto.BoardSearchCondition
import com.yologger.study.springboot_querydsl.dto.BoardUserDto
import com.yologger.study.springboot_querydsl.dto.QBoardUserDto
import com.yologger.study.springboot_querydsl.entity.Board
import com.yologger.study.springboot_querydsl.entity.QBoard
import com.yologger.study.springboot_querydsl.entity.QUser
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils

class BoardRepositoryImpl (
    // @Autowired private val entityManager: EntityManager,
): BoardRepositoryCustom, QuerydslRepositorySupport(Board::class.java) {

    private var entityManager = getEntityManager()

    // 생략 가능
    //  private val jpaQueryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    private val qBoard = QBoard.board

    override fun search(condition: BoardSearchCondition): List<BoardUserDto>? {

//        return jpaQueryFactory
//            .select(
//                QBoardUserDto(
//                    qBoard.id.`as`("boardId"),
//                    qBoard.title,
//                    qBoard.body,
//                    QUser.user.name.`as`("username")
//                )
//            )
//            .from(QBoard.board)
//            .leftJoin(QBoard.board.user, QUser.user)
//            .where(usernameEq(condition.username), titleEq(condition.title), bodyEq(condition.body))
//            .fetch()

        return from(qBoard)
            .leftJoin(QBoard.board.user, QUser.user)
            .where(usernameEq(condition.username), titleEq(condition.title), bodyEq(condition.body))
            .select(
                QBoardUserDto(
                    qBoard.id.`as`("boardId"),
                    qBoard.title,
                    qBoard.body,
                    QUser.user.name.`as`("username")
                )
            )
            .fetch()

    }

    private fun usernameEq(username: String): BooleanExpression? {
        return if (StringUtils.hasText(username)) QUser.user.name.eq(username) else null
    }

    private fun titleEq(title: String): BooleanExpression? {
        return if (StringUtils.hasText(title)) QBoard.board.title.eq(title) else null
    }

    private fun bodyEq(body: String): BooleanExpression? {
        return if (StringUtils.hasText(body)) QBoard.board.body.eq(body) else null
    }

    override fun search(condition: BoardSearchCondition, pageable: Pageable): Page<BoardUserDto> {

        //     val queryResults: QueryResults<BoardUserDto> = jpaQueryFactory
        //         .select(
        //             QBoardUserDto(
        //                 qBoard.id.`as`("boardId"),
        //                 qBoard.title,
        //                 qBoard.body,
        //                 QUser.user.name.`as`("username")
        //             )
        //         )
        //         .from(QBoard.board)
        //         .leftJoin(QBoard.board.user, QUser.user)
        //         .where(usernameEq(condition.username), titleEq(condition.title), bodyEq(condition.body))
        //         .offset(pageable.offset)
        //         .limit(pageable.pageSize.toLong())
        //         .fetchResults()
        //
        //     val boards = queryResults.results
        //     val total: Long = queryResults.total
        //
        //     return PageImpl(boards, pageable, total)

            val jpaQuery: JPQLQuery<BoardUserDto> = from(QBoard.board)
            .leftJoin(QBoard.board.user, QUser.user)
            .where(usernameEq(condition.username), titleEq(condition.title), bodyEq(condition.body))
            .select(
                QBoardUserDto(
                    qBoard.id.`as`("boardId"),
                    qBoard.title,
                    qBoard.body,
                    QUser.user.name.`as`("username")
                )
            )
            // .offset(pageable.offset)
            // .limit(pageable.pageSize.toLong())
            // .fetchResults()

        val queryResults: QueryResults<BoardUserDto>? =  querydsl?.applyPagination(pageable, jpaQuery)?.fetchResults()

        val boards = queryResults!!.results
        val total: Long = queryResults!!.total

        return PageImpl(boards, pageable, total)
    }
}