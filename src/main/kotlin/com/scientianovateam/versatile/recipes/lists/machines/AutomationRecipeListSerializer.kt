package com.scientianovateam.versatile.recipes.lists.machines

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.scientianovateam.versatile.recipes.RECIPE_COMPONENT_SERIALIZERS
import com.scientianovateam.versatile.recipes.components.IRecipeComponent

object AutomationRecipeListSerializer : IRegisterableJSONSerializer<AutomationRecipeList, JsonObject> {
    override val registryName = "automation".toResLocV()

    override fun read(json: JsonObject) = AutomationRecipeList(
            json.getAsJsonPrimitive("name").asString.toResLocV(),
            *json.entrySet().mapNotNull { it.value.asJsonObject?.let { json -> RECIPE_COMPONENT_SERIALIZERS[it.key.toResLocV()]?.read(json) } }.toTypedArray(),
            genJEIPage = json.getAsJsonPrimitive("gen_jei_page").asBoolean
    )

    override fun write(obj: AutomationRecipeList) = json {
        obj.recipeComponents.values.forEach {
            val serializer = it.serializer
            serializer.registryName.toString() to serializer.writeJSON(it)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> IRegisterableJSONSerializer<out IRecipeComponent<*>, JsonObject>.writeJSON(component: IRecipeComponent<T>) =
            (this as IRegisterableJSONSerializer<IRecipeComponent<T>, JsonObject>).write(component)
}