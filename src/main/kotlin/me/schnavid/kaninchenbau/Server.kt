package me.schnavid.kaninchenbau

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.schnavid.kaninchenbau.dav.json.Propfind
import me.schnavid.kaninchenbau.http.MediaType
import me.schnavid.kaninchenbau.http.Request
import me.schnavid.kaninchenbau.http.Response
import me.schnavid.kaninchenbau.stores.filesystem.FilesystemStore
import java.io.PrintWriter

class Server(port: Int, rootDirPath: String) {
    private val socket = java.net.ServerSocket(port)

    private val store = FilesystemStore(rootDirPath)

    fun run() {
        while (true) {
            val connection = socket.accept()

            val reader = connection.getInputStream().bufferedReader()
            val writer = PrintWriter(connection.getOutputStream())

            val request = Request.parse(reader)
            println(request.toString())

            val response = handle(request)

            writer.print(response.toRaw())
            writer.flush()

            connection.close()
        }
    }

    fun handle(request: Request): Response {
        when (request.method) {
            "PROPFIND" -> return handlePropfind(request)
        }

        return Response(200, "OK", request.headers, request.body)
    }

    private fun handlePropfind(request: Request): Response {
        val file = store.getItem(request.path) ?: return Response.notFound()

        when (request.contentType) {
            MediaType.xml -> {
                TODO()
            }
            MediaType.json -> {
                val propfind = request.body?.let { Json.decodeFromString<Propfind>(it) } ?: return Response.badRequest()

                TODO()
            }
            else -> {
                return Response.badRequest()
            }
        }
    }
}