package me.schnavid.kaninchenbau.dav.xml

data class XmlObject(
    val name: String,
    val prefix: String? = null,
    val attributes: Map<String, String> = mapOf(),
    val children: List<XmlElement> = listOf(),
) : XmlElement {
    companion object {
        fun dav(name: String, attributes: Map<String, String> = mapOf(), children: List<XmlElement> = listOf()) =
            XmlObject(name, "D", attributes + ("xmlns:D" to "DAV:"), children)
    }

    var outer: XmlObject? = null

    val prefixedName = prefix?.let { "$prefix:$name" } ?: name

    val startTag = if (attributes.isEmpty()) {
        "<$prefixedName>"
    } else {
        "<$prefixedName ${
            attributes
                .asSequence()
                .joinToString(" ") { "${it.key}=\"${it.value.xmlEscaped()}\"" }
        }>"
    }

    override fun toRaw(indent: String): String {
        return "$indent$startTag\n${children.joinToString("") { "${it.toRaw("$indent  ")}\n" }}$indent</$prefixedName>"
    }

    val onlyChild: XmlElement
        get() {
            assert(children.size == 1)
            return children[0]
        }

    operator fun get(index: Int) = children[index]

    fun getPrefixNamespace(prefix: String): String? =
        attributes["xmlns:$prefix"] ?: outer?.getPrefixNamespace(prefix)

    fun getDefaultNamespace(): String? =
        attributes["xmlns"] ?: outer?.getDefaultNamespace()

    val namespacedName: XmlNamespacedName
        get() = XmlNamespacedName(
            when (prefix) {
                null -> getDefaultNamespace()
                else -> getPrefixNamespace(prefix)
            } ?: "", name
        )
}
