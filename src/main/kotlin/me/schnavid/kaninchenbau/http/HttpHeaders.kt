package me.schnavid.kaninchenbau.http

import java.io.BufferedReader

class HttpHeaders(mapOfHeaders: Map<String, Collection<String>> = emptyMap()) {
    private val mapOfHeaders: Map<String, Collection<String>> =
        mapOfHeaders.mapKeys { (k, _) -> k.lowercase() }

    constructor(vararg pairs: Pair<String, String>) : this(
        pairs
            .asSequence()
            .groupBy { (name, _) -> name }
            .mapValues { (_, namesWithValues) -> namesWithValues.map { (_, values) -> values } }
            .toMap()
    )

    val size = this.mapOfHeaders.size

    val contentLength: Int = this["content-length"].firstOrNull()?.toInt() ?: 0

    fun asSequence() = mapOfHeaders.asSequence()
    operator fun get(key: String): Collection<String> = mapOfHeaders[key.lowercase()] ?: emptyList()

    companion object {
        private val headerRegex = "^(.+): (.+)$".toRegex()

        fun parse(reader: BufferedReader) = reader.lineSequence()
            .takeWhile { it.isNotBlank() }
            .map {
                val (header, values) = headerRegex.find(it)?.destructured
                                       ?: throw RequestParseError("Invalid header line: $it")

                header to values.split(", ")
            }
            .groupBy { (name, _) -> name }
            .mapValues { (_, valuesWithNames) -> valuesWithNames.map { (_, values) -> values }.flatten() }
            .let(::HttpHeaders)
    }
}