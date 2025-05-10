package com.yologger.study.springboot_jpa.repository

import com.yologger.study.springboot_jpa.dto.UserDto
import com.yologger.study.springboot_jpa.entity.Board
import com.yologger.study.springboot_jpa.entity.User
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.test.annotation.Commit
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet

@SpringBootTest
@Transactional
class UserRepositoryTest(
    @Autowired private val entityManager: EntityManager,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val jdbcTemplate: JdbcTemplate
) {

    @BeforeEach
    fun beforeEach() {
        val user1 = User(name = "ronaldo", nation="portugal", age = 40)
        entityManager.persist(user1)
        entityManager.persist(Board(title = "title1", body = "body1", user = user1))
        entityManager.persist(Board(title = "title2", body = "body2", user = user1))
        entityManager.persist(Board(title = "title3", body = "body3", user = user1))

        val user2 = User(name = "messi", nation="messi", age = 38)
        entityManager.persist(user2)
        entityManager.persist(Board(title = "title1", body = "body1", user = user2))
        entityManager.persist(Board(title = "title2", body = "body2", user = user2))
        entityManager.persist(Board(title = "title3", body = "body3", user = user2))

        val user3 = User(name = "benzema", nation="france", age = 40)
        entityManager.persist(user3)
        entityManager.persist(Board(title = "title1", body = "body1", user = user3))
        entityManager.persist(Board(title = "title2", body = "body2", user = user3))
        entityManager.persist(Board(title = "title3", body = "body3", user = user3))

        val user4 = User(name = "neymar", nation="brazil", age = 38)
        entityManager.persist(user4)
        entityManager.persist(Board(title = "title1", body = "body1", user = user4))
        entityManager.persist(Board(title = "title2", body = "body2", user = user4))
        entityManager.persist(Board(title = "title3", body = "body3", user = user4))

        entityManager.clear()
    }

    @Test
    fun test() {
        // val query = "SELECT u FROM User u INNER JOIN FETCH u.boards"
        val query = "SELECT u FROM User u"
        val users: List<User> = entityManager.createQuery(query, User::class.java).resultList
        for (user in users) {
            for (board in user.boards) {
                println("${board.title}, ${board.body}, ${user.name}")
            }
        }
    }

    @Test
    fun test1() {


        val users: List<User> = userRepository.findAll()
        for (user in users) {
            for (board in user.boards) {
                println(board)
            }
        }
    }
    @Test
    fun test2() {
        val users: List<User> = userRepository.findAll()
        for (user in users) {
            for (board in user.boards) {
                println("${board.title}, ${board.body}, ${user.name}")
            }
        }
    }

    @Test
    fun test3() {
        val sql = "SELECT * FROM user"
        jdbcTemplate.queryForList(sql, object: RowMapper<UserDto> {
            override fun mapRow(rs: ResultSet, rowNum: Int): UserDto? {
                return UserDto(name = rs.getString("name"), nation = rs.getString("nation"), age = rs.getInt("age"))
            }
        })
    }
}