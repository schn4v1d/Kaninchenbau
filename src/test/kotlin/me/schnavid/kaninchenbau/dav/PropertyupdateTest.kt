package me.schnavid.kaninchenbau.dav

import kotlin.test.Test

class PropertyupdateTest {
    @Test
    fun testXmlDeserializePropertyupdateDisplayname() {
        val raw = """
            <?xml version="1.0" encoding="utf-8" ?>
            <D:propertyupdate xmlns:D="DAV:">
                <D:set>
                    <D:prop>
                        <D:displayname>A Beautiful Name!</D:displayname>
                    </D:prop>
                </D:set>
            </D:propertyupdate>
        """.trimIndent()

        val propertyupdate = Propertyupdate.xmlTopLevel(raw)

        println(propertyupdate)
    }
}