package io.paoloconte.htmx.dsl

import kotlin.test.Test
import kotlin.test.assertEquals

class HtmlDslTest {

    @Test
    fun `script tag should not escape text content`() {
        val html = buildHtml {
            script {
                +"const fn = (a, b) => a < b && a > 0;"
            }
        }
        assertEquals("<script>const fn = (a, b) => a < b && a > 0;</script>", html)
    }

    @Test
    fun `style tag should not escape text content`() {
        val html = buildHtml {
            style {
                +"div > span { color: red; }"
            }
        }
        assertEquals("<style>div > span { color: red; }</style>", html)
    }

    @Test
    fun `normal tags should still escape text content`() {
        val html = buildHtml {
            p {
                +"a < b & c > d"
            }
        }
        assertEquals("<p>a &lt; b &amp; c &gt; d</p>", html)
    }
}
