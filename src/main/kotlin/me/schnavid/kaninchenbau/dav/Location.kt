package me.schnavid.kaninchenbau.dav

import com.gitlab.mvysny.konsumexml.Konsumer

data class Location(val href: HRef) {
    companion object : XmlConsumer<Location> {
        override fun xml(k: Konsumer): Location {
            k.checkCurrent("location")

            return Location(
                k.child("href") { HRef.xml(this) }
            )
        }
    }
}