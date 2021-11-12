package me.schnavid.kaninchenbau.dav

import com.gitlab.mvysny.konsumexml.Konsumer

data class Propstat(
    val prop: Prop,
    val status: Status,
    val error: Error?,
    val responseDescription: ResponseDescription?
) {
    companion object : XmlConsumer<Propstat> {
        override fun xml(k: Konsumer): Propstat {
            k.checkCurrent("propstat")

            val prop = k.child("prop") { Prop.xml(this) }

            val status = k.child("status") { Status.xml(this) }

            val error = null // TODO: k.childOrNull("error") { Error.xml(this) }

            val responseDescription = k.childOrNull("responsedescription") { ResponseDescription.xml(this) }

            return Propstat(prop, status, error, responseDescription)
        }
    }
}