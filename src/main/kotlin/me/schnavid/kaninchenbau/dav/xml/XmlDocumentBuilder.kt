package me.schnavid.kaninchenbau.dav.xml

class XmlDocumentBuilder {
    private var topLevel: XmlObject? = null

    fun obj(name: String, init: XmlObjectBuilder.() -> Unit) {
        topLevel = XmlObjectBuilder(name).apply(init).build()
    }

    fun namespacedObj(namespace: String, prefix: String, name: String, init: XmlObjectBuilder.() -> Unit) {
        topLevel = XmlObjectBuilder(name).apply {
            this.prefix = prefix
            attribute("xmlns:$prefix", "$namespace:")
        }.apply(init).build()
    }

    fun davObj(name: String, build: XmlObjectBuilder.() -> Unit) =
        namespacedObj("DAV", "D", name, build)

    fun build() = XmlDocument(topLevel!!)
}