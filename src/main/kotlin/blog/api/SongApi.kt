package blog.api

import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import java.time.LocalDate

/**
 * Song 歌曲有关类
 *
 */
data class SongVO(val id: Int, val name: String)


data class Model(val name: String, val items: List<Item>, val date: LocalDate = LocalDate.of(2018, 4, 13))

data class Item(val key: String, val value: String)

val model = Model("root", listOf(Item("A", "Apache"), Item("B", "Bing")))

fun Routing.songApi() {
    get("/songList") {
        call.respond(model)
    }


    /**
     * ktor如何将
     */
    post("/addSong") {
        //val post = call.receive<SongVO>()

        val data = call.receiveText()
        val dataValue =
        println("data: $data")

       // println("post: $post")

        //call.respond("ok $post")
    }

}