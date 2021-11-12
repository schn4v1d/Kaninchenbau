package me.schnavid.kaninchenbau.dav

import com.gitlab.mvysny.konsumexml.Konsumer
import com.gitlab.mvysny.konsumexml.Whitespace
import javax.xml.namespace.QName

data class DisplayName(val displayName: String) {
    companion object : PropertyConsumer<DisplayName> {
        override val qName = QName(DAV_NAMESPACE, "displayname")

        override fun xml(k: Konsumer): DisplayName? {
            k.checkCurrent(qName.localPart)

            val displayName = k.text(Whitespace.collapse, true)

            return if (displayName.isEmpty()) {
                null
            } else {
                DisplayName(displayName)
            }
        }

    }
}