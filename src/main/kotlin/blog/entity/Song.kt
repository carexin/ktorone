package blog.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object Songs : IntIdTable() {

    val name = varchar("name", 50) // Column<String>
}

class Song(id:EntityID<Int>):IntEntity(id){
    companion object : IntEntityClass<Song>(Songs)
    //
    var name by Songs.name
}

fun main(args: Array<String>) {
    Database.connect(url = "jdbc:mysql://localhost:3306/ktorone?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC"
            , user = "root"
            , password = "123456"
            , driver = "com.mysql.cj.jdbc.Driver"
    )

    transaction {

        create(Songs)

        val id = Song.new {
           name = "皇帝的新衣 skr"
        }

        print("song's id is $id")
        // 批量插入
        // Song.batchInsert(listOf())

    }
}