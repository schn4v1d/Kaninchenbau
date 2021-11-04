package me.schnavid.kaninchenbau.dav.xml

sealed class Propfind : XmlData {
    abstract override fun toXml(): XmlDocument

    companion object {
        fun fromXml(obj: XmlObject): Propfind {
            assert(obj.namespacedName == XmlNamespacedName("DAV", "propfind"))
            assert(obj.children.isNotEmpty())
            val firstChild = obj.children[0].obj
            when (firstChild.namespacedName) {
                DavName("propname") -> {
                    assert(obj.children.size == 1)
                    return PropfindPropname
                }
                DavName("allprop") -> {
                    return when (obj.children.size) {
                        2 -> PropfindAllprop(
                            obj.children[1].obj.children
                                .map { it.obj }
                                .map {
                                    assert(it.children.isEmpty())
                                    it.namespacedName
                                }
                        )
                        1 -> PropfindAllprop(listOf())
                        else -> throw Error()
                    }
                }
                DavName("prop") -> {
                    assert(obj.children.size == 1)
                    return PropfindProp(firstChild.children
                        .map { it.obj }
                        .map {
                            assert(it.children.isEmpty())
                            it.namespacedName
                        })
                }
                else -> throw Error()
            }
        }
    }

    object PropfindPropname : Propfind() {
        override fun toXml() = XmlDocument {
            davObj("propfind") {
                davObj("propname")
            }
        }
    }

    class PropfindAllprop(val include: List<XmlNamespacedName>) : Propfind() {
        override fun toXml() = XmlDocument {
            davObj("propfind") {
                davObj("allprop")
                if (include.isNotEmpty()) {
                    davObj("include") {
                        for (prop in include) {
                            namespacedObj(prop)
                        }
                    }
                }
            }
        }
    }

    class PropfindProp(val prop: List<XmlNamespacedName>) : Propfind() {
        override fun toXml() = XmlDocument {
            davObj("propfind") {
                davObj("prop") {
                    for (prop in prop) {
                        namespacedObj(prop)
                    }
                }
            }
        }
    }
}