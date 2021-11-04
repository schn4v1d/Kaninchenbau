package me.schnavid.kaninchenbau.dav.json

import kotlinx.serialization.Serializable

@Serializable
data class Propfind(val selection: PropfindSelection) {
    companion object {
        val all get() = Propfind(PropfindSelection.PropfindSelectionAll)
        fun some(vararg properties: String) = Propfind(PropfindSelection.PropfindSelectionSome(properties.toList()))
    }
}