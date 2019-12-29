package com.scientianovateam.versatile.velisp.registry

import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.registry.Registry
import com.scientianovateam.versatile.velisp.VELISPType
import com.scientianovateam.versatile.velisp.functions.IFunction

val VELISP_TYPES = Registry<(String) -> VELISPType>()
val VELISP_FUNCTIONS = Registry<IFunction>()

fun Registry<IFunction>.register(function: IFunction) = set(function.name.toResLocV(), function)
fun Registry<IFunction>.registerAll(vararg functions: IFunction) = functions.forEach(::register)