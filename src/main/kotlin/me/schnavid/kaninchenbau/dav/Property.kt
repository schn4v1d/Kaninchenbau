package me.schnavid.kaninchenbau.dav

import com.gitlab.mvysny.konsumexml.Konsumer
import javax.xml.namespace.QName

data class Property<T>(val name: QName, val value: T?) {
    companion object : XmlConsumer<Property<*>> {
        private val registry: Map<QName, PropertyConsumer<*>> = listOf(
            DisplayName
        ).associateBy { DisplayName.qName }

        override fun xml(k: Konsumer): Property<*> {
            val qName = k.name

            return Property(qName, (registry[qName] ?: throw Error()).xml(k))
        }
    }
}