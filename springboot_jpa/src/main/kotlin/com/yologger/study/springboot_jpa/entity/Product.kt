package com.yologger.study.springboot_jpa.entity

import jakarta.persistence.*

@Entity
@Table(name= "product")
data class Product (
    @Id
    @Column(name = "id")
    var id: Int,

    @Column(name = "title")
    var name: String = "",

    @Column(name = "description")
    var description: String = ""
)