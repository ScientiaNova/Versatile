package com.scientianovateam.versatile.common.loaders

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.getStringOrNull
import com.scientianovateam.versatile.common.loaders.internal.cascadeJsons
import com.scientianovateam.versatile.common.loaders.internal.earlyResources
import com.scientianovateam.versatile.common.math.Graph
import com.scientianovateam.versatile.common.registry.FORM_PROPERTIES
import com.scientianovateam.versatile.common.serialization.registries.FormSerializer
import com.scientianovateam.versatile.materialsystem.lists.Forms
import com.scientianovateam.versatile.velisp.convertToExpression
import com.scientianovateam.versatile.velisp.evaluated.StringValue
import com.scientianovateam.versatile.velisp.functions.constructor.FormFunction
import com.scientianovateam.versatile.velisp.unevaluated.FunctionCall
import com.scientianovateam.versatile.velisp.unresolved.evaluate

fun loadForms() {
    val sets = mutableMapOf<String, MutableList<JsonObject>>()
    earlyResources.loadJsons("registries/forms").forEach { json ->
        json.getStringOrNull("name")?.let { sets.computeIfAbsent(it) { mutableListOf() }.add(json) }
    }
    val jsons = sets.mapValues { (_, value) -> cascadeJsons(value) }
    val graph = Graph<JsonObject>()
    jsons.values.forEach { form ->
        FORM_PROPERTIES.forEach { (_, property) ->
            val value = if (form.has(property.name))
                convertToExpression(form.get(property.name))
            else property.default
            value.find(FunctionCall::class.java) { it.function == FormFunction }
                    .mapNotNull { (it.inputs.firstOrNull()?.evaluate() as? StringValue)?.value?.let { key -> jsons[key] } }
                    .forEach { graph.add(it, form) }
        }
    }
    val forms = graph.topologicalSort() ?: error("Form cycle")
    forms.map(FormSerializer::read).forEach { Forms.add(it) }
}