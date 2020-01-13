package com.scientianovateam.versatile.velisp

import com.google.gson.JsonElement
import com.scientianovateam.versatile.materialsystem.main.Form
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.main.MaterialStack
import com.scientianovateam.versatile.velisp.evaluated.*
import com.scientianovateam.versatile.velisp.unevaluated.FunctionCall
import com.scientianovateam.versatile.velisp.unevaluated.UnevaluatedStructValue
import com.scientianovateam.versatile.velisp.unresolved.Getter
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

operator fun String.invoke(vararg inputs: Any?) = FunctionCall(this, inputs.map(Any?::expr).toList())
val String.get get() = get(this)
val String.matGet get() = get("mat/$this")
val String.formGet get() = get("form/$this")
fun Number.expr() = NumberValue(this.toDouble())
fun String.expr() = StringValue(this)
fun Boolean.expr() = BoolValue(this)
fun Material.expr() = MaterialValue(this)
fun MaterialStack.expr() = MaterialStackValue(material.expr(), count.expr())
fun Form.expr() = FormValue(this)
fun List<Any?>.expr() = ListValue(this.map { it.expr() as IEvaluated })
fun get(property: String) = Getter(property)
val it = get("it")

class StructBuilder {
    private val map = mutableMapOf<String, Any?>()
    fun add(key: String, value: Any?) = map.put(key, value)
    infix fun String.to(value: Any?) = map.put(this, value)
    operator fun String.invoke(struct: StructBuilder.() -> Unit) = map.put(this, StructBuilder().apply(struct).build())
    fun build() = UnevaluatedStructValue(map.mapValues { it.value.expr() })
}

fun struct(builder: StructBuilder.() -> Unit) = StructBuilder().apply(builder).build()

fun Any?.expr(): IUnresolved = when (this) {
    null -> NullValue
    is Number -> expr()
    is String -> expr()
    is Boolean -> expr()
    is Material -> expr()
    is MaterialStack -> expr()
    is Form -> expr()
    is List<Any?> -> expr()
    is JsonElement -> toExpression()
    is IUnresolved -> this
    else -> error("${this::class.simpleName} is not an expression value")
}