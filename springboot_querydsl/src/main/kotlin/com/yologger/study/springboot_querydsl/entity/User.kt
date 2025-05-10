package com.yologger.study.springboot_querydsl.entity

import jakarta.persistence.*

@Entity
@Table(name= "user")
data class User (
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "name")
    var name: String,

    @Column(name = "nation")
    val nation: String,

    @Column(name = "age")
    var age: Long,

    @OneToMany(mappedBy = "user")
    var boards: MutableList<Board> = mutableListOf(),
)