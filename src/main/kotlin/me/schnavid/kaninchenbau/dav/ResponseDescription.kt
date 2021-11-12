package me.schnavid.kaninchenbau.dav

import com.gitlab.mvysny.konsumexml.Konsumer

data class ResponseDescription(val description: String) {
    companion object : XmlConsumer<ResponseDescription> {
        override fun xml(k: Konsumer): ResponseDescription {
            k.checkCurrent("responsedescription")

            return ResponseDescription(k.text())
        }
    }
}