package com.scientianovateam.versatile.recipes.lists.machines

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.scientianovateam.versatile.recipes.RECIPE_COMPONENT_SERIALIZERS
import com.scientianovateam.versatile.recipes.components.IRecipeComponent

object StandardRecipeListSerializer : IRegisterableJSONSerializer<StandardRecipeList, JsonObject> {
    override val registryName = "standard".toResLocV()

    override fun read(json: JsonObject) = StandardRecipeList(
            json.getAsJsonPrimitive("name").asString.toResLocV(),
            *json.entrySet().mapNotNull { it.value.asJsonObject?.let { json -> RECIPE_COMPONENT_SERIALIZERS[it.key.toResLocV()]?.read(json) } }.toTypedArray(),
            genJEIPage = json.getAsJsonPrimitive("gen_jei_page").asBoolean
    )

    override fun write(obj: StandardRecipeList) = json {
        obj.recipeComponents.values.forEach {
            val serializer = it.serializer
            serializer.registryName.toString() to serializer.writeJSON(it)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> IRegisterableJSONSerializer<out IRecipeComponent<*>, JsonObject>.writeJSON(component: IRecipeComponent<T>) =
            (this as IRegisterableJSONSerializer<IRecipeComponent<T>, JsonObject>).write(component)
}