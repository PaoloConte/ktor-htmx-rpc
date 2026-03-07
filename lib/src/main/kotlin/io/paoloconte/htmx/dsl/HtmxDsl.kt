package io.paoloconte.htmx.dsl

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

enum class SwapStrategy(val value: String) {
    InnerHTML("innerHTML"),
    OuterHTML("outerHTML"),
    AfterBegin("afterbegin"),
    BeforeEnd("beforeend"),
    BeforeBegin("beforebegin"),
    AfterEnd("afterend"),
    Delete("delete"),
    None("none");
}

/** Sets hx-swap-oob and id on any tag */
private fun Tag.swapOob(selector: String, strategy: SwapStrategy = SwapStrategy.OuterHTML) {
    id = selector.removePrefix("#")
    hxSwapOob = strategy.value
}

class HtmxResponseBuilder : Tag {
    override val tagName: String = "__builder__"
    override val attributes = mutableMapOf<String, String>()
    override val children = mutableListOf<Node>()
    internal val headers = mutableMapOf<String, String>()

    /**
     * Emits an OOB swap fragment. The block must return the tag to swap.
     * The tag must already have an id set
     */
    fun replace(strategy: SwapStrategy = SwapStrategy.OuterHTML, block: Tag.() -> Tag) {
        block().apply {
            hxSwapOob = strategy.value
            assert(id.isNotBlank())
        }
    }

    /**
     * Shorthand for updating any element by its CSS selector (e.g., "#count") using a specific strategy.
     * Uses a 'div' as the transport tag, which works for most cases (except inside tables).
     */
    fun update(selector: String, strategy: SwapStrategy = SwapStrategy.InnerHTML, block: Tag.() -> Unit) {
        div {
            id = selector.removePrefix("#")
            hxSwapOob = strategy.value
            block()
        }
    }

    /** Removes an element from the DOM
     * The tag must already have an id set */
    fun removeElement(selector: String) {
        div {
            swapOob(selector, SwapStrategy.Delete)
            assert(id.isNotBlank())
        }
    }

    /** Sets an HX-Trigger header (triggers JS events on the client) */
    fun triggerEvent(eventName: String, detail: String? = null) {
        val value = if (detail != null) """{"$eventName": $detail}""" else eventName
        headers["HX-Trigger"] = value
    }

    /** Forces a client-side redirect */
    fun redirect(url: String) {
        headers["HX-Redirect"] = url
    }

    /** Forces a page refresh */
    fun refresh() {
        headers["HX-Refresh"] = "true"
    }

    /** Changes the URL in the address bar without navigating */
    fun pushUrl(url: String) {
        headers["HX-Push-Url"] = url
    }

    /** Specifies how the response will be swapped */
    fun reswap(swap: String) {
        headers["HX-Reswap"] = swap
    }

    /** Specifies the target element to be replaced
     * @param selector is a CSS selector */
    fun retarget(selector: String) {
        headers["HX-Retarget"] = selector
    }

    /** Selects which part of the response to swap in, overriding hx-select on the triggering element
     * @param selector is a CSS selector */
    fun reselect(selector: String) {
        headers["HX-Reselect"] = selector
    }

    /** Performs a client-side redirect without a full page reload */
    fun location(url: String) {
        headers["HX-Location"] = url
    }

    /** Replaces the current URL in the location bar */
    fun replaceUrl(url: String) {
        headers["HX-Replace-Url"] = url
    }

    /** Triggers client-side events after the settle step */
    fun triggerAfterSettle(eventName: String, detail: String? = null) {
        val value = if (detail != null) """{"$eventName": $detail}""" else eventName
        headers["HX-Trigger-After-Settle"] = value
    }

    /** Triggers client-side events after the swap step */
    fun triggerAfterSwap(eventName: String, detail: String? = null) {
        val value = if (detail != null) """{"$eventName": $detail}""" else eventName
        headers["HX-Trigger-After-Swap"] = value
    }

}

/**
 * Extension on ApplicationCall to respond with an HtmxResponse.
 * Sets headers and renders all fragments.
 */
suspend fun ApplicationCall.respondHtmx(block: HtmxResponseBuilder.() -> Unit) {
    val response = HtmxResponseBuilder().apply(block)

    // Set custom headers
    response.headers.forEach { (key, value) ->
        this.response.header(key, value)
    }

    val html = buildHtml {
        children.addAll(response.children)
    }

    respondText(html, ContentType.Text.Html)
}

/** Extension on ApplicationCall to respond with a full HTML page. */
suspend fun ApplicationCall.respondHtml(block: Tag.() -> Unit) {
    val html = "<!DOCTYPE html>\n" + buildHtml(block)
    respondText(html, ContentType.Text.Html)
}

/** Checks if the request is an HTMX request. */
fun ApplicationCall.isHtmx(): Boolean =
    request.headers["HX-Request"] == "true"

/** Returns the client's current URL (useful to know which page the request came from). */
fun ApplicationCall.htmxCurrentUrl(): String? =
    request.headers["HX-Current-URL"]

/** Returns the trigger element's name. */
fun ApplicationCall.htmxTriggerName(): String? =
    request.headers["HX-Trigger-Name"]
