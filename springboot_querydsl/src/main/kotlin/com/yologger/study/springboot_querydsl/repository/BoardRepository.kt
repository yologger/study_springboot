package com.yologger.study.springboot_querydsl.repository

import com.yologger.study.springboot_querydsl.entity.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface BoardRepository: JpaRepository<Board, Long>, BoardRepositoryCustom, QuerydslPredicateExecutor<Board> {
    fun findByTitle (title: String): List<Board>
}