package com.scientianovateam.versatile.velisp

import com.scientianovateam.versatile.materialsystem.main.Form
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.velisp.evaluated.*
import com.scientianovateam.versatile.velisp.unevaluated.FunctionCall
import com.scientianovateam.versatile.velisp.unresolved.Getter
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

operator fun String.invoke(vararg inputs: IUnresolved) = FunctionCall(this, inputs.toList())
fun Number.expr() = NumberValue(this.toDouble())
fun String.expr() = StringValue(this)
fun Boolean.expr() = BoolValue(this)
fun Material.expr() = MaterialValue(this)
fun Form.expr() = FormValue(this)
fun get(property: String) = Getter(property)