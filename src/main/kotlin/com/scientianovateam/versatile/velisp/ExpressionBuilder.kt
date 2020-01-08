package com.scientianovateam.versatile.velisp

import com.scientianovateam.versatile.materialsystem.main.Form
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.velisp.evaluated.*
import com.scientianovateam.versatile.velisp.unevaluated.FunctionCall
import com.scientianovateam.versatile.velisp.unresolved.Getter
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

operator fun String.invoke(vararg inputs: Any) = FunctionCall(this, inputs.map(Any::expr).toList())
val String.get get() = get(this)
val String.matGet get() = get("mat/$this")
val String.formGet get() = get("form/$this")
fun Number.expr() = NumberValue(this.toDouble())
fun String.expr() = StringValue(this)
fun Boolean.expr() = BoolValue(this)
fun Material.expr() = MaterialValue(this)
fun Form.expr() = FormValue(this)
fun get(property: String) = Getter(property)
val it = get("it")

fun Any.expr() = when (this) {
    is Number -> expr()
    is String -> expr()
    is Boolean -> expr()
    is Material -> expr()
    is Form -> expr()
    is IUnresolved -> this
    else -> error("${this::class.simpleName} is not an expression value")
}