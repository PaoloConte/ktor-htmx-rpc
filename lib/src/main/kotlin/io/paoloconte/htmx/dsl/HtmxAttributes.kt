package io.paoloconte.htmx.dsl
var Tag.hxGet: String
    get() = attributes["hx-get"] ?: ""
    set(value) { attributes["hx-get"] = value }

var Tag.hxPost: String
    get() = attributes["hx-post"] ?: ""
    set(value) { attributes["hx-post"] = value }

var Tag.hxPut: String
    get() = attributes["hx-put"] ?: ""
    set(value) { attributes["hx-put"] = value }

var Tag.hxPatch: String
    get() = attributes["hx-patch"] ?: ""
    set(value) { attributes["hx-patch"] = value }

var Tag.hxDelete: String
    get() = attributes["hx-delete"] ?: ""
    set(value) { attributes["hx-delete"] = value }

var Tag.hxTarget: String
    get() = attributes["hx-target"] ?: ""
    set(value) { attributes["hx-target"] = value }

var Tag.hxSwap: String
    get() = attributes["hx-swap"] ?: ""
    set(value) { attributes["hx-swap"] = value }

var Tag.hxSwapOob: String
    get() = attributes["hx-swap-oob"] ?: ""
    set(value) { attributes["hx-swap-oob"] = value }

var Tag.hxSelect: String
    get() = attributes["hx-select"] ?: ""
    set(value) { attributes["hx-select"] = value }

var Tag.hxSelectOob: String
    get() = attributes["hx-select-oob"] ?: ""
    set(value) { attributes["hx-select-oob"] = value }

var Tag.hxTrigger: String
    get() = attributes["hx-trigger"] ?: ""
    set(value) { attributes["hx-trigger"] = value }

var Tag.hxVals: String
    get() = attributes["hx-vals"] ?: ""
    set(value) { attributes["hx-vals"] = value }

var Tag.hxInclude: String
    get() = attributes["hx-include"] ?: ""
    set(value) { attributes["hx-include"] = value }

var Tag.hxParams: String
    get() = attributes["hx-params"] ?: ""
    set(value) { attributes["hx-params"] = value }

var Tag.hxEncoding: String
    get() = attributes["hx-encoding"] ?: ""
    set(value) { attributes["hx-encoding"] = value }

var Tag.hxPushUrl: String
    get() = attributes["hx-push-url"] ?: ""
    set(value) { attributes["hx-push-url"] = value }

var Tag.hxReplaceUrl: String
    get() = attributes["hx-replace-url"] ?: ""
    set(value) { attributes["hx-replace-url"] = value }

var Tag.hxHistoryElt: String
    get() = attributes["hx-history-elt"] ?: ""
    set(value) { attributes["hx-history-elt"] = value }

var Tag.hxIndicator: String
    get() = attributes["hx-indicator"] ?: ""
    set(value) { attributes["hx-indicator"] = value }

var Tag.hxDisabledElt: String
    get() = attributes["hx-disabled-elt"] ?: ""
    set(value) { attributes["hx-disabled-elt"] = value }

var Tag.hxConfirm: String
    get() = attributes["hx-confirm"] ?: ""
    set(value) { attributes["hx-confirm"] = value }

var Tag.hxPrompt: String
    get() = attributes["hx-prompt"] ?: ""
    set(value) { attributes["hx-prompt"] = value }

var Tag.hxBoost: String
    get() = attributes["hx-boost"] ?: ""
    set(value) { attributes["hx-boost"] = value }

var Tag.hxExt: String
    get() = attributes["hx-ext"] ?: ""
    set(value) { attributes["hx-ext"] = value }

var Tag.hxDisinherit: String
    get() = attributes["hx-disinherit"] ?: ""
    set(value) { attributes["hx-disinherit"] = value }

var Tag.hxPreserve: String
    get() = attributes["hx-preserve"] ?: ""
    set(value) { attributes["hx-preserve"] = value }

var Tag.hxSync: String
    get() = attributes["hx-sync"] ?: ""
    set(value) { attributes["hx-sync"] = value }

var Tag.hxValidate: String
    get() = attributes["hx-validate"] ?: ""
    set(value) { attributes["hx-validate"] = value }

var Tag.hxHeaders: String
    get() = attributes["hx-headers"] ?: ""
    set(value) { attributes["hx-headers"] = value }

var Tag.hxRequest: String
    get() = attributes["hx-request"] ?: ""
    set(value) { attributes["hx-request"] = value }

var Tag.hxInherit: String
    get() = attributes["hx-inherit"] ?: ""
    set(value) { attributes["hx-inherit"] = value }

var Tag.sseConnect: String
    get() = attributes["sse-connect"] ?: ""
    set(value) { attributes["sse-connect"] = value }

var Tag.sseSwap: String
    get() = attributes["sse-swap"] ?: ""
    set(value) { attributes["sse-swap"] = value }

var Tag.wsConnect: String
    get() = attributes["ws-connect"] ?: ""
    set(value) { attributes["ws-connect"] = value }

var Tag.wsSend: String
    get() = attributes["ws-send"] ?: ""
    set(value) { attributes["ws-send"] = value }

fun Tag.hxOn(event: String, handler: String) {
    attributes["hx-on:$event"] = handler
}
