package me.schnavid.kaninchenbau.dav.xml

class XmlObjectBuilder(var name: String, private val outer: XmlObjectBuilder? = null) {
    var prefix: String? = null
    val attributes: MutableMap<String, String> = mutableMapOf()
    val children: MutableList<XmlElement> = mutableListOf()

    fun attribute(key: String, value: String) {
        attributes[key] = value
    }

    fun namespacedObj(namespacedName: XmlNamespacedName, init: (XmlObjectBuilder.() -> Unit)? = null) =
        children.add(
            XmlObjectBuilder(namespacedName.name).apply {
                val p = namespacePrefix(namespacedName.namespace)

                if (p == null) {
                    val nsP = prefixNamespace(namespacedName.namespace.uppercase().substring(0, 1))
                    if (nsP == null) {
                        this.prefix = nsP

                        if (init != null) {
                            init()
                        }
                    } else {
                        throw Error()
                    }
                } else {
                    this.prefix = p

                    if (init != null) {
                        init()
                    }
                }
            }.build()
        )

    fun namespacedObj(namespace: String, prefix: String, name: String, init: (XmlObjectBuilder.() -> Unit)? = null) =
        children.add(
            XmlObjectBuilder(name).apply {
                val ns = prefixNamespace(prefix)
                if (ns == null) {
                    attribute("xmlns:$prefix", "$namespace:")
                } else if (ns == "$namespace:") {
                    this.prefix = prefix

                    if (init != null) {
                        init()
                    }
                } else {
                    throw Error()
                }
            }.build()
        )

    fun davObj(name: String, build: (XmlObjectBuilder.() -> Unit)? = null) =
        namespacedObj("DAV", "D", name, build)

    fun prefixNamespace(prefix: String): String? = attributes["xmlns:$prefix"] ?: outer?.prefixNamespace(prefix)

    fun namespacePrefix(namespace: String): String? =
        attributes
            .entries
            .find { it.key.substring(0, 6) == "xmlns:" && it.value == "$namespace:" }
            ?.key
            ?.substring(6)
        ?: outer?.namespacePrefix(namespace)

    fun build(): XmlObject {
        val obj = XmlObject(name, prefix, attributes, children)
        obj.children.forEach {
            if (it is XmlObject) {
                it.outer = obj
            }
        }
        return obj
    }
}
