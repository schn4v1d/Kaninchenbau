package me.schnavid.kaninchenbau.dav.xml

import org.junit.Assert.*
import org.junit.Test

class XmlObjectTest {
    @Test
    fun testPrefixedName() {
        val obj = XmlObject("test", "p")

        assertEquals("p:test", obj.prefixedName)
    }

    @Test
    fun testStartTag() {
        val obj = XmlObject("test", "p")

        assertEquals("<p:test>", obj.startTag)
    }

    @Test
    fun testFlatXmlObjectSerialization() {
        val obj = XmlObject("test")

        val serialized = obj.toRaw()

        assertEquals("<test>\n</test>", serialized)
    }

    @Test
    fun testNestedXmlObjectSerialization() {
        val obj = XmlObject("outer", children = listOf(XmlObject("inner1"), XmlObject("inner2")))

        val serialized = obj.toRaw()

        assertEquals(
            """
            <outer>
              <inner1>
              </inner1>
              <inner2>
              </inner2>
            </outer>""".trimIndent(),
            serialized
        )
    }

    @Test
    fun testPrefixedXmlObjectSerialization() {
        val obj = XmlObject("test", prefix = "k")

        val serialized = obj.toRaw()

        assertEquals(
            """
            <k:test>
            </k:test>
            """.trimIndent(),
            serialized
        )
    }

    @Test
    fun testNamespacedXmlObjectSerialization() {
        val obj = XmlObject("test", prefix = "D", attributes = mapOf("xmlns:D" to "DAV:"))

        val serialized = obj.toRaw()

        assertEquals(
            """
            <D:test xmlns:D="DAV:">
            </D:test>
            """.trimIndent(),
            serialized
        )
    }
}