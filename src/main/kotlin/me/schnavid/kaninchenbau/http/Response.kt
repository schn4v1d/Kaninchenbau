package me.schnavid.kaninchenbau.http

data class Response(
    val statusCode: Int,
    val statusText: String,
    val headers: HttpHeaders = HttpHeaders(),
    val body: String? = null
) {
    companion object {
        fun ok(headers: HttpHeaders = HttpHeaders(), body: String? = null) =
            Response(200, "OK", headers, body)

        fun badRequest(headers: HttpHeaders = HttpHeaders(), body: String? = null) =
            Response(400, "Bad Request", headers, body)

        fun notFound(headers: HttpHeaders = HttpHeaders(), body: String? = null) =
            Response(404, "Not Found", headers, body)
    }

    fun toRaw(): String {
        val buffer = StringBuffer()
        buffer.append("${RequestLine.PROTOCOL} $statusCode $statusText")
        headers.asSequence().joinTo(buffer) { (name, values) ->
            "$name: ${values.joinToString(", ")}\n"
        }
        body?.let { buffer.append("\n").append(it) }
        return buffer.toString()
    }
}