package me.schnavid.kaninchenbau.stores

interface Store {
    fun getItem(path: String): StoreItem?
}