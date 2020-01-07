package com.scientianovateam.versatile.velisp.registry

import com.scientianovateam.versatile.common.registry.Registry
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.types.VELISPType

val VELISP_TYPES = Registry<VELISPType>()
val VELISP_FUNCTIONS = VELISPRegistry<IFunction>()

fun VELISPRegistry<IFunction>.register(function: IFunction) = set(function.name.toRegName(), function)
fun VELISPRegistry<IFunction>.registerAll(vararg functions: IFunction) = functions.forEach(::register)