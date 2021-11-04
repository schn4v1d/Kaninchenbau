package me.schnavid.kaninchenbau.http

data class MediaType(val type: String, val subtype: String, val parameters: List<String>) {
    companion object {
        private val mediaTypeRegex = "(.*)/(.*)(;.*)*".toRegex()

        fun parse(raw: String): MediaType {
            val (type, subtype, parameters) = mediaTypeRegex.find(raw)?.destructured!!

            return MediaType(type, subtype, listOf())
        }

        val xml by lazy { MediaType("application", "xml", listOf()) }
        val json by lazy { MediaType("application", "json", listOf()) }
    }
}