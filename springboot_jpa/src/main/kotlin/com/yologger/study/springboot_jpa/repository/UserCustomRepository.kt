package com.yologger.study.springboot_jpa.repository

import com.yologger.study.springboot_jpa.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement

@Repository
class UserCustomRepository(
    @Autowired private val jdbcTemplate: JdbcTemplate
) {
    fun bulkInsert(users: List<User>) {

        users.chunked(INSERT_CHUNKED_SIZE).forEach { chuckedUsers ->
            jdbcTemplate.batchUpdate(
                INSERT_QUERY,
                object: BatchPreparedStatementSetter {
                    override fun setValues(ps: PreparedStatement, index: Int) {
                        ps.setString(1, chuckedUsers[index].name)
                        ps.setString(2, chuckedUsers[index].nation)
                        ps.setLong(3, chuckedUsers[index].age)
                    }

                    override fun getBatchSize(): Int {
                        return chuckedUsers.size
                    }
                }
            )
        }
    }

    companion object {
        val INSERT_CHUNKED_SIZE = 50

        val INSERT_QUERY = """
            INSERT INTO user (name, nation, age)
            VALUES (?, ?, ?)
        """.trimIndent()
    }
}