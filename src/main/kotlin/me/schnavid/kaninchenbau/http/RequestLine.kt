package me.schnavid.kaninchenbau.http

import java.io.BufferedReader

data class RequestLine(val method: String, val path: String, val protocol: String) {
    companion object {
        private val requestLineRegex = "^(.+) (.+) (.+)$".toRegex()

        const val PROTOCOL = "HTTP/1.1"

        fun parse(reader: BufferedReader): RequestLine {
            val requestLine = reader.readLine() ?: throw RequestParseError("Request must not be empty")
            val (method, path, protocol) = requestLineRegex.find(requestLine)?.destructured
                                           ?: throw RequestParseError("Invalid request line. It should match ${requestLineRegex.pattern}")
            if (protocol != PROTOCOL) {
                throw RequestParseError("Invalid protocol. Only $PROTOCOL is supported.")
            }

            return RequestLine(method, path, protocol)
        }
    }
}