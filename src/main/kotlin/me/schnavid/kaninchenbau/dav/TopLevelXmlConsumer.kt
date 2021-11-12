package me.schnavid.kaninchenbau.dav

import com.gitlab.mvysny.konsumexml.Konsumer
import com.gitlab.mvysny.konsumexml.konsumeXml
import javax.xml.namespace.QName

interface TopLevelXmlConsumer<T>: XmlConsumer<T> {
    val qName: QName

    fun xmlTopLevel(raw: String) = raw.konsumeXml().child(qName.localPart) { xml(this) }
    fun xmlTopLevel(k: Konsumer) = k.child(qName.localPart) { xml(this) }
}