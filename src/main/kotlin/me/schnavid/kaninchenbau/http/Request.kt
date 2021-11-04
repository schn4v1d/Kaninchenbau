package me.schnavid.kaninchenbau.http

import java.io.BufferedReader
import java.nio.CharBuffer

class Request(
    val method: String,
    val path: String,
    val headers: HttpHeaders,
    val body: String?,
) {
    companion object {
        fun parse(reader: BufferedReader): Request {
            val requestLine = RequestLine.parse(reader)

            val headers = HttpHeaders.parse(reader)

            val body = when (headers.contentLength) {
                0 -> null
                else -> {
                    val buffer = CharBuffer.allocate(headers.contentLength)
                    reader.read(buffer)
                    buffer.flip()
                    buffer.toString()
                }
            }

            return Request(requestLine.method, requestLine.path, headers, body)
        }
    }

    val contentType: MediaType? = headers["Content-Type"].firstOrNull()?.let { MediaType.parse(it) }
}