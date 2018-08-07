package blog.api

import io.ktor.application.call
import io.ktor.content.PartData
import io.ktor.content.forEachPart
import io.ktor.network.util.ioCoroutineDispatcher
import io.ktor.request.receiveMultipart
import io.ktor.routing.Routing
import io.ktor.routing.post
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.withContext
import kotlinx.coroutines.experimental.yield
import java.io.File
import java.io.InputStream
import java.io.OutputStream

fun Routing.fileApi() {
    post("/upload") {
        call.receiveMultipart().forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    println(part.value)
                }
                is PartData.FileItem -> {
                    print("附件名称: ${part.originalFileName!!}")
                    // 获取扩展名
                    val ext = File(part.originalFileName).extension

                   // val file = File()
                    //part.streamProvider().use { its -> file }
                }
            }
        }
    }
}

suspend fun InputStream.copyToSuspend(
        out: OutputStream,
        bufferSize: Int = DEFAULT_BUFFER_SIZE,
        yieldSize: Int = 4 * 1024 * 1024,
        dispatcher: CoroutineDispatcher = ioCoroutineDispatcher
): Long {
    return withContext(dispatcher) {
        val buffer = ByteArray(bufferSize)
        var bytesCopied = 0L
        var bytesAfterYield = 0L
        while (true) {
            val bytes = read(buffer).takeIf { it >= 0 } ?: break
            out.write(buffer, 0, bytes)
            if (bytesAfterYield >= yieldSize) {
                yield()
                bytesAfterYield %= yieldSize
            }
            bytesCopied += bytes
            bytesAfterYield += bytes
        }
        return@withContext bytesCopied
    }
}
