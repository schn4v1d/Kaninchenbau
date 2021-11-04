package me.schnavid.kaninchenbau.stores.filesystem

import me.schnavid.kaninchenbau.stores.StoreCollection
import java.io.File

class FilesystemStoreCollection(private val file: File, private val isWritable: Boolean) : StoreCollection {
    init {
        assert(file.isDirectory)
    }

    override val name: String get() = file.name
    override val uniqueKey: String get() = file.absolutePath
}