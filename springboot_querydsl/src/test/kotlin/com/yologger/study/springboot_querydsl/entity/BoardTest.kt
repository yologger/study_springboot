package com.yologger.study.springboot_querydsl.entity

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.Tuple
import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.CaseBuilder
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.JPAExpressions.select
import com.querydsl.jpa.impl.JPAQueryFactory
import com.yologger.study.springboot_querydsl.dto.MemberDto
import com.yologger.study.springboot_querydsl.dto.QUserDto
import com.yologger.study.springboot_querydsl.dto.UserDto
import com.yologger.study.springboot_querydsl.entity.QBoard.board
import com.yologger.study.springboot_querydsl.entity.QUser.user
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.PersistenceUnit
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.expression.spel.ast.Projection
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class BoardTest(
    @Autowired private val entityManager: EntityManager,
    @Autowired private val entityManagerFactory: EntityManagerFactory
) {
    @BeforeEach
    fun beforeEach() {
        val user1 = User(name = "ronaldo", age = 40, nation = "portugal")
        entityManager.persist(user1)

        val board1 = Board(title = "title1", body = "body1", user = user1)
        val board2 = Board(title = "title2", body = "body2", user = user1)
        val board3 = Board(title = "title3", body = "body3", user = user1)
        val board4 = Board(title = "title4", body = "body4", user = user1)
        entityManager.persist(board1)
        entityManager.persist(board2)
        entityManager.persist(board3)
        entityManager.persist(board4)

        val user2 = User(name = "messi", age = 38, nation = "argentina")
        entityManager.persist(user2)

        val board5 = Board(title = "title5", body = "body5", user = user2)
        val board6 = Board(title = "title6", body = "body6", user = user2)
        val board7 = Board(title = "title7", body = "body7", user = user2)
        val board8 = Board(title = "title8", body = "body8", user = user2)
        entityManager.persist(board5)
        entityManager.persist(board6)
        entityManager.persist(board7)
        entityManager.persist(board8)

        val user3 = User(name = "benzema", age = 40, nation = "france")
        val user4 = User(name = "kane", age = 31, nation = "england")
        val user5 = User(name = "neymar", age = 32, nation = "brazil")
        val user6 = User(name = "son", age = 32, nation = "south korea")
        val user7 = User(name = "zlatan", age = 32, nation = "sweden")

        entityManager.persist(user3)
        entityManager.persist(user4)
        entityManager.persist(user5)
        entityManager.persist(user6)
        entityManager.persist(user7)
    }

    @Test
    fun testJPQL() {
        val result = entityManager.createQuery("SELECT b FROM Board b WHERE b.title = :title", Board::class.java)
            .setParameter("title", "title1")
            .singleResult

        assertThat(result.title).isEqualTo("title1")
    }

    @Test
    fun testQueryDSL() {
        val jpaQueryFactory = JPAQueryFactory(entityManager)
        val boards = jpaQueryFactory
            .selectFrom(board)
            .orderBy(board.title.asc(), board.body.desc().nullsLast())
            .fetch()

        assertThat(boards.size).isNotZero()
    }

    @Test
    fun testAggregation() {
        val jpaQueryFactory = JPAQueryFactory(entityManager)
        val tuple: Tuple? = jpaQueryFactory
            .select(
                user.count(),
                user.age.sum(),
                user.age.avg(),
                user.age.max(),
                user.age.min()
            ).from(user)
            .fetchOne()

        assertThat(tuple?.get(user.count())).isEqualTo(2)
        assertThat(tuple?.get(user.age.sum())).isEqualTo(78)
    }

    @Test
    fun testGrouping() {
        val jpaQueryFactory = JPAQueryFactory(entityManager)
        val result: List<Tuple> = jpaQueryFactory
            .select(
                user.nation,
                user.age.sum()
            )
            .from(user)
            .groupBy(user.nation)
            .having(user.age.sum().gt(50))
            .fetch()

        assertThat(result[0].get(user.nation)).isEqualTo("portugal")
        assertThat(result[0].get(user.age.sum())).isEqualTo(76)
    }

    @Test
    fun join() {

        val jpaQueryFactory = JPAQueryFactory(entityManager)
        val boards: List<Board> = jpaQueryFactory
            .selectFrom(board)
            .rightJoin(board.user, user)
            .where(user.name.eq("ronaldo"))
            .fetch()

        assertThat(boards.size).isEqualTo(4)

        assertThat(boards.size).isEqualTo(4)
    }

    @Test
    fun thetaJoin() {
        val kane = User(name = "Kane", age = 32, nation = "England")
        entityManager.persist(kane)
        val son = User(name = "Son", age = 33, nation = "South Korea")
        entityManager.persist(son)

        val board1 = Board(title = "Son", body = "body5", user = kane)
        val board2 = Board(title = "Son", body = "body6", user = kane)
        entityManager.persist(board1)
        entityManager.persist(board2)

        val jpaQueryFactory = JPAQueryFactory(entityManager)

        val boards: List<Board> = jpaQueryFactory
            .select(board)
            .from(board, user)
            .where(board.title.eq(user.name))
            .fetch()

        assertThat(boards.size).isEqualTo(2)
    }

    @Test
    fun on() {
        // 게시글과 유저을 조회하면서, 이름이 ronaldo인 유저만 조인, 게시글은 모두 조인
        /*
        * JPQL: SELECT b, u FROM Board b LEFT JOIN b.user u ON user.name = 'ronaldo'
        * SQL: SELECT b.*, u.* FROM Board b LEFT JOIN User u ON b.uid = u.id AND u.name = 'ronaldo'
        * */

        val jpaQueryFactory = JPAQueryFactory(entityManager)
        val tuples: List<Tuple> = jpaQueryFactory
            .select(board,  user)
            .from(board)
            .leftJoin(board.user, user)
            .on(user.name.eq("ronaldo"))
            .fetch()

        for (tuple in tuples) {
            println(tuple)
        }

    }

    @Test
    fun test() {
        // 연관관계가 없는 두 엔티티 외부   조인
        // 게시글 제목이 유저 이름과 동일한 대상을 외부 조인
        val kane = User(name = "Kane", age = 32, nation = "England")
        entityManager.persist(kane)
        val son = User(name = "Son", age = 33, nation = "South Korea")
        entityManager.persist(son)

        val board1 = Board(title = "Son", body = "body5", user = kane)
        val board2 = Board(title = "Son", body = "body6", user = kane)
        entityManager.persist(board1)
        entityManager.persist(board2)

        val jpaQueryFactory = JPAQueryFactory(entityManager)

        val tuples: List<Tuple> = jpaQueryFactory
            .select(board, user)
            .from(board)
            .leftJoin(user)
            .on(board.title.eq(user.name))
            .fetch()

        for (tuple in tuples) {
            println(tuple)
        }
    }



    @Test
    fun noFetchJoin() {
        entityManager.flush()
        entityManager.clear()

        val jpaQueryFactory = JPAQueryFactory(entityManager)
        val board = jpaQueryFactory
            .selectFrom(board)
            .where(board.title.eq("title1"))
            .fetchOne()

        // PersistContext에 연관관계에 있는 User가 로딩되었는지 확인
        val isLoaded = entityManagerFactory.persistenceUnitUtil.isLoaded(board?.user)
        assertThat(isLoaded).isFalse()  // false
    }

    @Test
    fun fetchJoin() {
        entityManager.flush()
        entityManager.clear()

        val jpaQueryFactory = JPAQueryFactory(entityManager)
        val board = jpaQueryFactory
            .selectFrom(board)
            .join(board.user, user).fetchJoin()
            .where(board.title.eq("title1"))
            .fetchOne()

        // PersistContext에 연관관계에 있는 User가 로딩되었는지 확인
        val isLoaded = entityManagerFactory.persistenceUnitUtil.isLoaded(board?.user)
        assertThat(isLoaded).isTrue()  // false
    }

    @Test
    fun subquery() {
        val jpaQueryFactory = JPAQueryFactory(entityManager)

        val b1 = QBoard("b1")
        val b2 = QBoard("b2")

        val results: List<Board> = jpaQueryFactory
            .selectFrom(b1)
            .where(b1.title.eq(JPAExpressions
                .select(b2.title)
                .from(b2)
                .where(b2.title.eq("title1"))))
            .fetch()
    }

    @Test
    fun subquery2() {
        val jpaQueryFactory = JPAQueryFactory(entityManager)

        val b1 = QBoard("b1")
        val b2 = QBoard("b2")

        val results: List<Board> = jpaQueryFactory
            .selectFrom(b1)
            .where(b1.title.eq(
                select(b2.title)
                .from(b2)
                .where(b2.title.eq("title1"))))
            .fetch()
    }

    @Test
    fun case1() {
        val jpaQueryFactory = JPAQueryFactory(entityManager)
        val tuples: List<Tuple> = jpaQueryFactory
            .select(
                user.name,
                CaseBuilder()
                    .`when`(user.age.between(0, 20)).then("0~20")
                    .`when`(user.age.between(10, 40)).then("20~40")
                    .otherwise("40~")
            )
            .from(user)
            .fetch()

        for (tuple in tuples)
            println(tuple)

    }

    @Test
    fun constant() {
        val jpaQueryFactory = JPAQueryFactory(entityManager)
        val tuples: List<Tuple> = jpaQueryFactory
            .select(
                user.name,
                user.age,
                user.name.concat("_").concat(user.age.stringValue() )
            )
            .from(user)
            .fetch()

        for (tuple in tuples)
            println(tuple)

    }

    @Test
    fun projection1() {
        val jpaQueryFactory = JPAQueryFactory(entityManager)
        val users: List<User> = jpaQueryFactory
            .select(user)
            .from(user)
            .fetch()

        for (user in users)
            println(user)

    }

    @Test
    fun projectionDto() {
        val jpaQueryFactory = JPAQueryFactory(entityManager)
        val users: List<UserDto> = jpaQueryFactory
            .select(QUserDto(user.name, user.age))
            .from(user)
            .fetch()

        println(users)
    }

    @Test
    fun sadasd() {
        val name: String? = null
        val age: Long? = 30

        val jpaQueryFactory = JPAQueryFactory(entityManager)
        val users = jpaQueryFactory
            .selectFrom(user)
            .where(*allEqual(name, age))
            .fetch()
    }

    private fun nameEqual(nameCond: String?): BooleanExpression? {
        return if (nameCond.isNullOrBlank()) null
        else user.name.eq(nameCond)
    }

    private fun ageEqual(ageCond: Long?): BooleanExpression? {
        return if (ageCond == null) null
        else user.age.eq(ageCond)
    }

    private fun allEqual(nameCond: String?, ageCond: Long?): Array<BooleanExpression?> {
        return arrayOf(nameEqual(nameCond), ageEqual(ageCond))
    }

    @Test
    fun bulkUpdate() {
        val jpaQueryFactory = JPAQueryFactory(entityManager)

        val count = jpaQueryFactory
            .update(user)
            .set(user.age, user.age.add(1))
            .execute()

    }

    @Test
    fun sqlFunction() {
        val jpaQueryFactory = JPAQueryFactory(entityManager)

        jpaQueryFactory
            .select(Expressions.numberTemplate(
                Int::class.java,
                "abs({0})",
                user.age)
            )
            .from(user)
            .fetch()

    }
}