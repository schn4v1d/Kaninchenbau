package me.schnavid.kaninchenbau.dav

import com.gitlab.mvysny.konsumexml.Konsumer
import com.gitlab.mvysny.konsumexml.Names
import javax.xml.namespace.QName

data class Propertyupdate(val instructions: List<ProppatchInstruction>) {
    sealed class ProppatchInstruction {
        data class Set(val prop: Prop) : ProppatchInstruction()
        data class Remove(val prop: Prop) : ProppatchInstruction()
    }

    companion object : TopLevelXmlConsumer<Propertyupdate> {
        override val qName = QName(DAV_NAMESPACE, "propertyupdate")

        private val setQName = QName(DAV_NAMESPACE, "set")
        private val removeQName = QName(DAV_NAMESPACE, "remove")

        override fun xml(k: Konsumer): Propertyupdate {
            k.checkCurrent(qName.localPart)

            val instructions = mutableListOf<ProppatchInstruction>()

            k.children(Names.of("set", "remove")) {
                val prop = child("prop") {
                    Prop.xml(this)
                }

                when (name) {
                    setQName -> instructions.add(ProppatchInstruction.Set(prop))
                    removeQName -> {
                        assert(!prop.hasValues)
                        instructions.add(ProppatchInstruction.Remove(prop))
                    }
                    else -> throw Error()
                }
            }

            return Propertyupdate(instructions)
        }

    }
}