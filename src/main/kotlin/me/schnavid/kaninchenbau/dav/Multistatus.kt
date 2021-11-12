package me.schnavid.kaninchenbau.dav

import com.gitlab.mvysny.konsumexml.Konsumer
import javax.xml.namespace.QName

data class Multistatus(val responses: List<MultistatusResponse>, val responseDescription: ResponseDescription?) {
    companion object : TopLevelXmlConsumer<Multistatus> {
        override val qName = QName(DAV_NAMESPACE, "multistatus")

        override fun xml(k: Konsumer): Multistatus {
            k.checkCurrent("multistatus")

            val responses = mutableListOf<MultistatusResponse>()

            var responsesComplete = false

            while (!responsesComplete) {
                val response = k.childOrNull("response") {

                }

                if (response == null) {
                    responsesComplete = true
                }
            }

            val description = k.childOrNull("responsedescription") { ResponseDescription.xml(this) }

            return Multistatus(responses, description)
        }

    }
}
