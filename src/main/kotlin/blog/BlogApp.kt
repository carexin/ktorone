package blog

import blog.api.fileApi
import blog.api.songApi
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.routing.Routing
import java.text.DateFormat
import org.jetbrains.exposed.sql.Database

fun Application.main() {

    Database.connect(url = "jdbc:mysql://localhost:3306/ktorone?characterEncoding=utf-8&useSSL=false"
            , user = "root"
            , password = "123456"
            , driver = "com.mysql.cj.jdbc.Driver"
    )

    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }

    install(Routing){
        songApi()
        fileApi()
    }
}

