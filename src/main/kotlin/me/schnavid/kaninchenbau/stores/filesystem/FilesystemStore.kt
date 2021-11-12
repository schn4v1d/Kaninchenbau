package me.schnavid.kaninchenbau.stores.filesystem

import me.schnavid.kaninchenbau.stores.Store
import me.schnavid.kaninchenbau.stores.StoreItem
import java.io.File

class FilesystemStore(root: String, private val isWritable: Boolean = true) : Store {
    private val rootDirectory = File(root)

    init {
        println("Store running from: ${rootDirectory.absolutePath}")
    }

    override fun getItem(path: String): StoreItem? {
        val file = File(rootDirectory, path)

        return when {
            file.isDirectory -> FilesystemStoreCollection(file, isWritable)
            file.isFile -> FilesystemStoreItem(file, isWritable)
            else -> null
        }
    }
}