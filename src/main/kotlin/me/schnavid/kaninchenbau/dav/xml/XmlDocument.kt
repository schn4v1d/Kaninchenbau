package me.schnavid.kaninchenbau.dav.xml

data class XmlDocument(val topLevel: XmlObject) {
    companion object {
        fun fromRaw(raw: String): XmlDocument {
            TODO()
        }
    }

    fun toRaw(): String {
        return "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n${topLevel.toRaw()}"
    }
}