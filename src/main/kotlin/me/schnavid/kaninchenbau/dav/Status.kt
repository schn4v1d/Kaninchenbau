package me.schnavid.kaninchenbau.dav

import com.gitlab.mvysny.konsumexml.Konsumer

data class Status(val statusCode: Int, val statusText: String) {
    companion object : XmlConsumer<Status> {
        private val regex = "^HTTP/1.1 (.+) (.+)$".toRegex()

        override fun xml(k: Konsumer): Status {
            k.checkCurrent("status")

            val text = k.text()

            val (code, statusText) = regex.find(text)?.destructured ?: throw Error()

            val statusCode = code.toInt()

            return Status(statusCode, statusText)
        }
    }
}