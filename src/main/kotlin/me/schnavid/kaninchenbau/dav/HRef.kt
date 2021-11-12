package me.schnavid.kaninchenbau.dav

import com.gitlab.mvysny.konsumexml.Konsumer

data class HRef(val uri: String) {
    companion object : XmlConsumer<HRef> {
        override fun xml(k: Konsumer): HRef {
            k.checkCurrent("href")

            return HRef(k.text())
        }
    }
}