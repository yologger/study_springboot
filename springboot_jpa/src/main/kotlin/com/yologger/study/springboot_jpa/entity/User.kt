package com.yologger.study.springboot_jpa.entity

import jakarta.persistence.*
import org.hibernate.annotations.BatchSize

@Entity
@Table(name= "user")
data class User (
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "name")
    var name: String = "",

    @Column(name = "nation")
    var nation: String = "",

    @Column(name = "age")
    var age: Long = 0,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @BatchSize(size = 2)
    var boards: MutableList<Board> = mutableListOf(),
) {
    override fun toString(): String {
        return "(name=${name}, nation=${nation}, boards=${boards})"
    }
}