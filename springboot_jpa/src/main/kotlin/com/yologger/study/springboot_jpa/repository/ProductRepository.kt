package com.yologger.study.springboot_jpa.repository

import com.yologger.study.springboot_jpa.entity.Product
import com.yologger.study.springboot_jpa.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph

interface ProductRepository: JpaRepository<Product, Long>
