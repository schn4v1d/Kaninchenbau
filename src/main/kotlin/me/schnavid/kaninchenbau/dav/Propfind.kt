package me.schnavid.kaninchenbau.dav

import com.gitlab.mvysny.konsumexml.Konsumer
import com.gitlab.mvysny.konsumexml.Names
import javax.xml.namespace.QName

sealed class Propfind {
    companion object : TopLevelXmlConsumer<Propfind> {
        override val qName = QName(DAV_NAMESPACE, "propfind")

        override fun xml(k: Konsumer): Propfind {
            k.checkCurrent(qName.localPart)
            val result: Propfind = k.child(Names.of("propname", "allprop", "prop")) {
                when (localName) {
                    "propname" -> {
                        PropfindPropname
                    }
                    "allprop" -> {
                        TODO()
                    }
                    "prop" -> {
                        val prop = Prop.xml(this)

                        assert(!prop.hasValues)

                        PropfindProp(prop)
                    }
                    else -> throw Error()
                }
            }
            return result
        }
    }

    object PropfindPropname : Propfind() {
//        override fun toXml() = XmlDocument {
//            davObj("propfind") {
//                davObj("propname")
//            }
//        }
    }

//    class PropfindAllprop() : Propfind()

    data class PropfindProp(val prop: Prop) : Propfind()
}