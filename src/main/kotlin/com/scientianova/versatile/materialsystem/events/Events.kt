package com.scientianova.versatile.materialsystem.events

import com.scientianova.versatile.common.registry.StringBasedRegistry
import com.scientianova.versatile.common.registry.StringRegistryEvent
import com.scientianova.versatile.materialsystem.elements.Element
import com.scientianova.versatile.materialsystem.forms.GlobalForm
import com.scientianova.versatile.materialsystem.materials.Material
import net.minecraftforge.eventbus.api.Event

class ElementRegistryEvent(override val registry: ElementRegistry) : Event(), StringRegistryEvent<Element>
class MaterialRegistryEvent(override val registry: MaterialRegistry) : Event(), StringRegistryEvent<Material>
class FormRegistryEvent(override val registry: FormRegistry) : Event(), StringRegistryEvent<GlobalForm>
class FormOverrideEvent(val forms: StringBasedRegistry<GlobalForm>, val mats: StringBasedRegistry<Material>) : Event()