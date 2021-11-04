package me.schnavid.kaninchenbau.dav.xml

import org.junit.Test

import org.junit.Assert.*

class PropfindTest {
    @Test
    fun toXml() {
        assertEquals(
            XmlDocument {
                davObj("propfind") {
                    davObj("propname")
                }
            },
            Propfind.PropfindPropname.toXml()
        )

        assertEquals(
            XmlDocument {
                davObj("propfind") {
                    davObj("allprop")
                }
            },
            Propfind.PropfindAllprop(listOf()).toXml()
        )

        assertEquals(
            XmlDocument {
                davObj("propfind") {
                    davObj("prop") {
                        davObj("creationdate")
                        davObj("displayname")
                    }
                }
            },
            Propfind.PropfindProp(listOf(DavName("creationdate"), DavName("displayname"))).toXml()
        )
    }
}