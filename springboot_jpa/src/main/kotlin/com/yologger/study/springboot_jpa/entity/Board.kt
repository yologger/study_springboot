package com.yologger.study.springboot_jpa.entity

import jakarta.persistence.*

@Entity
@Table(name= "board")
data class Board (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0,

    @Column(name = "title")
    var title: String = "",

    @Column(name = "body")
    var body: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    var user: User?
) {
    override fun toString(): String {
        return "(title: ${title}, body: ${body})"
    }
}