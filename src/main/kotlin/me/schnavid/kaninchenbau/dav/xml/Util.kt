package me.schnavid.kaninchenbau.dav.xml

fun String.xmlEscaped(): String {
    return replace("&(?!apos;|quot;|amp;|lt;|gt;)".toRegex(), "&amp;")
        .replace("'", "&apos;")
        .replace("\"", "&quot;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
}

fun XmlDocument(build: XmlDocumentBuilder.() -> Unit) : XmlDocument {
    return XmlDocumentBuilder().apply(build).build()
}

fun DavName(name: String) = XmlNamespacedName("DAV", name)