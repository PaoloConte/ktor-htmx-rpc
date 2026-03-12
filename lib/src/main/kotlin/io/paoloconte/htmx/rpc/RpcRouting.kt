package io.paoloconte.htmx.rpc

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.util.collections.*
import io.paoloconte.htmx.dsl.*
import java.lang.reflect.Method
import kotlin.coroutines.SuspendFunction1
import kotlin.reflect.KFunction
import kotlin.reflect.full.callSuspend
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.jvm.javaMethod
import kotlin.reflect.typeOf

typealias RpcResponse = HtmxResponseBuilder.() -> Unit
typealias RpcFunction = SuspendFunction1<ApplicationCall, RpcResponse>

class RpcRouting(
    private val routing: Routing,
) {

    companion object {

        private const val BASE_PATH = "/rpc"

        fun Routing.rpc(init: RpcRouting.() -> Unit) {
            RpcRouting(this).init()
        }

        var Tag.rpc: suspend (ApplicationCall) -> RpcResponse
            set(value) {
                hxPost = functionEndpoint(value)
                if (hxSwap.isEmpty()) hxSwap = "none"
            }
            get() = error("can't get this")

        fun functionEndpoint(function: RpcFunction): String {
            val method = (function as KFunction<*>).javaMethod!!
            val name = method.declaringClass.simpleName + "." + (function as KFunction<*>).name
            return "$BASE_PATH/$name"
        }
    }

    private val rpcFunctions = ConcurrentMap<String, Method>()

    /**
     * Register a single RPC function as a ktor endpoint
     */
    fun action(function: RpcFunction) {
        registerAction(function)
    }

    /**
     * Register all functions in the object that match the [RpcFunction] signature
     * as ktor endpoints
     */
    @Suppress("UNCHECKED_CAST")
    fun registerAll(obj: Any) {
        obj::class.memberFunctions.forEach { func ->
            if (func.returnType != typeOf<RpcResponse>()) return@forEach
            if (func.parameters.size != 2) return@forEach
            if (func.parameters[1].type != typeOf<ApplicationCall>()) return@forEach
            val wrapper = FuncWrapper(obj, func as KFunction<RpcResponse>)
            registerAction(wrapper::callSuspend, func.javaMethod!!.declaringClass.simpleName+"."+func.name)
        }
    }

    private fun registerAction(function: RpcFunction, overrideName: String? = null) {
        val method = (function as KFunction<*>).javaMethod!!
        val name = overrideName ?: (method.declaringClass.simpleName + "." + (function as KFunction<*>).name)

        val existing = rpcFunctions.putIfAbsent(name, method)
        if (existing != null && existing != method) {
            error("RPC name collision: '$name' is already registered")
        }
        if (existing == null) {
            routing.post("$BASE_PATH/$name") {
                val response = function(call)
                call.respondHtmx {
                    response()
                }
            }
        }
    }

    /**
     * This class is used to transform a KFunction that is retrieved through reflection
     * to a SuspendFunction that is used to invoke it, same as with [action]
     */
    private class FuncWrapper(val obj: Any, val func: KFunction<RpcResponse>) {
        suspend fun callSuspend(params: ApplicationCall): RpcResponse {
            return func.callSuspend(obj, params)
        }
    }
}