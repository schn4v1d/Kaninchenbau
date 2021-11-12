package me.schnavid.kaninchenbau.dav

import com.gitlab.mvysny.konsumexml.Konsumer
import javax.xml.namespace.QName

interface PropertyConsumer<T> {
    val qName: QName

    fun xml(k: Konsumer): T?
}