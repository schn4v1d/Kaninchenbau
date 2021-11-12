package me.schnavid.kaninchenbau.dav

import com.gitlab.mvysny.konsumexml.Konsumer
import com.gitlab.mvysny.konsumexml.Names

data class Prop(val properties: List<Property<*>>) {
    companion object : XmlConsumer<Prop> {
        override fun xml(k: Konsumer): Prop {
            k.checkCurrent("prop")
            return Prop(k.children(Names.any()) {
                Property.xml(this)
            })
        }
    }

    val hasValues get() = properties.any { it.value != null }
}