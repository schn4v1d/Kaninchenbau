package me.schnavid.kaninchenbau.stores.filesystem

import me.schnavid.kaninchenbau.stores.StoreItem
import java.io.File

class FilesystemStoreItem(private val file: File, private val isWritable: Boolean) : StoreItem {
    init {
        assert(file.isFile)
    }

    override val name: String get() = file.name
    override val uniqueKey: String get() = file.absolutePath
}