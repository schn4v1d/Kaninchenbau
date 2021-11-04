package me.schnavid.kaninchenbau.dav.xml

data class XmlPrimitive(val content: String): XmlElement {
    override fun toRaw(indent: String): String {
        return indent + content.xmlEscaped()
    }
}