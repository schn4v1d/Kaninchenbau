package me.schnavid.kaninchenbau.dav

import kotlin.test.Test

class PropfindTest {
    @Test
    fun testXmlDeserializePropfindPropDisplayname() {
        val raw = """
            <?xml version="1.0" encoding="utf-8" ?>
            <D:propfind xmlns:D="DAV:">
                <D:prop>
                    <D:displayname/>
                </D:prop>
            </D:propfind>
        """.trimIndent()

        Propfind.xmlTopLevel(raw)
    }
}