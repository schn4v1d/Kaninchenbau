package me.schnavid.kaninchenbau.dav.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class PropfindSelection {
    @Serializable
    @SerialName("all")
    object PropfindSelectionAll : PropfindSelection()

    @Serializable
    @SerialName("some")
    data class PropfindSelectionSome(val propNames: List<String>) : PropfindSelection()
}