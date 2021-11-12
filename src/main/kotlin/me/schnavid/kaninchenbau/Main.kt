package me.schnavid.kaninchenbau

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.modules.SerializersModule

@ExperimentalSerializationApi
fun main() {
    Server(8080, "root").run()
}