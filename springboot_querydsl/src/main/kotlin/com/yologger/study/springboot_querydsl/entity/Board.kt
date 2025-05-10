package com.yologger.study.springboot_querydsl.entity

import jakarta.persistence.*

@Entity
@Table(name= "board")
data class Board (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "title")
    var title: String,

    @Column(name = "body")
    var body: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    var user: User
)