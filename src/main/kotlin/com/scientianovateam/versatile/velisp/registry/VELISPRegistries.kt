package com.scientianovateam.versatile.velisp.registry

import com.scientianovateam.versatile.common.registry.Registry
import com.scientianovateam.versatile.velisp.VELISPType
import com.scientianovateam.versatile.velisp.functions.IFunction

val VELISP_FUNCTIONS = Registry<IFunction>()
val VELISP_TYPES = Registry<(String) -> VELISPType>()