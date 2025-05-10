package com.yologger.study.springboot_querydsl.repository

import com.yologger.study.springboot_querydsl.dto.BoardSearchCondition
import com.yologger.study.springboot_querydsl.dto.BoardUserDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardRepositoryCustom {
    fun search(condition: BoardSearchCondition): List<BoardUserDto>?
     fun search(condition: BoardSearchCondition, pageable: Pageable): Page<BoardUserDto>
}