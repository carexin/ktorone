package blog.entity

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object Song : Table() {
    val id = integer("id").autoIncrement().primaryKey() // Column<Int>
    val name = varchar("name", 50) // Column<String>
}

fun main(args: Array<String>) {
    Database.connect(url = "jdbc:mysql://localhost:3306/ktorone?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC"
            , user = "root"
            , password = "123456"
            , driver = "com.mysql.cj.jdbc.Driver"
    )

    transaction {

        create(Song)

        val id = Song.insert {
            it[name] = "无人之境"
        } get Song.id

        print("song's id is $id")
        // 批量插入
        // Song.batchInsert(listOf())

    }
}