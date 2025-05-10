package com.yologger.study.springboot_querydsl.repository

import com.yologger.study.springboot_querydsl.entity.User
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository: JpaRepository<User, Long> {
    // fun findOrderByAgeDesc(): List<User>

    @Query("SELECT u FROM User u ORDER BY u.age DESC")
    fun selectOrderByAge(): List<User>

    // fun find(sort: Sort): List<User>
}