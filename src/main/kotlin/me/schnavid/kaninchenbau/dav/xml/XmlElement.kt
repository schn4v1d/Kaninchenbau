package me.schnavid.kaninchenbau.dav.xml

interface XmlElement {
    fun toRaw(indent: String): String
    fun toRaw() = toRaw("")

    val obj: XmlObject get() = when (this) {
        is XmlObject -> this
        else -> throw Error()
    }

    val primitive: XmlPrimitive get() = when (this) {
        is XmlPrimitive -> this
        else -> throw Error()
    }
}
