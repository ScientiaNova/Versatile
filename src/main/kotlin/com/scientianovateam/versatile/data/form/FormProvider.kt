package com.scientianovateam.versatile.data.form

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.data.generation.GSON
import com.scientianovateam.versatile.materialsystem.builders.FormIntermediate
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import java.io.IOException

class FormProvider(private val dataGenerator: DataGenerator) : IDataProvider {
    private val forms = mutableListOf<FormIntermediate>()

    operator fun FormIntermediate.unaryPlus() = this.apply {
        forms += this
    }

    override fun act(cache: DirectoryCache) {
        forms.forEach {
            try {
                IDataProvider.save(GSON, cache, it.toJSON(), dataGenerator.outputFolder.resolve("data/versatile/registries/forms/${it.name}.json"))
            } catch (e: IOException) {
                Versatile.LOGGER.error("Couldn't save form")
            }
        }
    }

    override fun getName() = "Form"
}