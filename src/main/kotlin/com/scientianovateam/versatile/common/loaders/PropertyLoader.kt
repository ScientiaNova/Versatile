package com.scientianovateam.versatile.common.loaders

import com.scientianovateam.versatile.common.loaders.internal.earlyResources
import com.scientianovateam.versatile.materialsystem.lists.FORM_PROPERTIES
import com.scientianovateam.versatile.materialsystem.lists.MATERIAL_PROPERTIES
import com.scientianovateam.versatile.materialsystem.serializers.PropertySerializer

fun loadProperties() {
    earlyResources.loadAll("registries/mat_properties").map(PropertySerializer::read).forEach { property ->
        MATERIAL_PROPERTIES[property.name] = property
    }

    earlyResources.loadAll("registries/form_properties").map(PropertySerializer::read).forEach { property ->
        FORM_PROPERTIES[property.name] = property
    }
}