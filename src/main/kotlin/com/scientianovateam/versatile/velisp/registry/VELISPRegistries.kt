package com.scientianovateam.versatile.velisp.registry

import com.scientianovateam.versatile.common.registry.Registry
import com.scientianovateam.versatile.velisp.VELISPType
import com.scientianovateam.versatile.velisp.functions.IFunction

object VELISPRegistries {
    val FUNCTION_REGISTRY = Registry<IFunction>()
    val TYPES = Registry<(String) -> VELISPType>()
}