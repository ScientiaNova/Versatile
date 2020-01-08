package com.scientianovateam.versatile.common.loaders

import com.scientianovateam.versatile.common.loaders.internal.earlyResources
import com.scientianovateam.versatile.common.registry.FORM_PROPERTIES
import com.scientianovateam.versatile.common.registry.MATERIAL_PROPERTIES
import com.scientianovateam.versatile.common.serialization.registries.PropertySerializer

fun loadProperties() {
    earlyResources.loadAll("registries/mat_properties").map(PropertySerializer::read).forEach { property ->
        MATERIAL_PROPERTIES[property.name] = property
    }

    earlyResources.loadAll("registries/form_properties").map(PropertySerializer::read).forEach { property ->
        FORM_PROPERTIES[property.name] = property
    }
}