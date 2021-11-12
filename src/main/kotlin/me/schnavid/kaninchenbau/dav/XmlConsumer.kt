package me.schnavid.kaninchenbau.dav

import com.gitlab.mvysny.konsumexml.Konsumer

interface XmlConsumer<T> {
    fun xml(k: Konsumer): T
}