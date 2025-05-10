package com.yologger.study.springboot_querydsl.controller

import com.yologger.study.springboot_querydsl.dto.BoardSearchCondition
import com.yologger.study.springboot_querydsl.dto.BoardUserDto
import com.yologger.study.springboot_querydsl.repository.BoardJpaRepository
import com.yologger.study.springboot_querydsl.repository.BoardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BoardController(
    @Autowired private val boardRepository: BoardRepository
) {
    @GetMapping("/api/v1/boards")
    fun searchBoardV1(condition: BoardSearchCondition): List<BoardUserDto>? {
        return boardRepository.search(condition)
    }

    @GetMapping("/api/v2/boards")
    fun searchBoardV2(condition: BoardSearchCondition, pageable: Pageable): Page<BoardUserDto> {
        return boardRepository.search(condition, pageable)
    }
}