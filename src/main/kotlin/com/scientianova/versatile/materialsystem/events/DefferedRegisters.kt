package com.scientianova.versatile.materialsystem.events

import com.scientianova.versatile.Versatile
import com.scientianova.versatile.blocks.ExtendedBlockProperties
import com.scientianova.versatile.blocks.VersatileBlock
import com.scientianova.versatile.blocks.VersatileBlockItem
import com.scientianova.versatile.common.extensions.json
import com.scientianova.versatile.common.registry.*
import com.scientianova.versatile.fluids.VersatileBucketItem
import com.scientianova.versatile.fluids.VersatileFluidBlock
import com.scientianova.versatile.items.ExtendedItemProperties
import com.scientianova.versatile.items.VersatileItem
import com.scientianova.versatile.materialsystem.elements.BaseElement
import com.scientianova.versatile.materialsystem.elements.Element
import com.scientianova.versatile.materialsystem.elements.Isotope
import com.scientianova.versatile.materialsystem.forms.Form
import com.scientianova.versatile.materialsystem.materials.Material
import com.scientianova.versatile.materialsystem.materials.fluid
import com.scientianova.versatile.materialsystem.properties.*
import net.minecraft.client.renderer.RenderType
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.LanguageMap
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fluids.ForgeFlowingFluid
import net.minecraftforge.fml.DistExecutor
import java.util.function.Consumer
import java.util.function.Supplier

class DeferredElemRegister(override val priority: EventPriority = EventPriority.NORMAL) : DeferredStringRegister<Element> {
    private val list = mutableListOf<() -> Element>()
    override fun set(name: String, thing: () -> Element): StringRegistryObject<Element> {
        list += thing
        return StringRegistryObject(name, ::elements)
    }

    private fun addAll(e: ElementRegistryEvent) {
        list.forEach { e.registry.register(it()) }
    }

    override fun register(bus: IEventBus) = bus.addListener(priority, Consumer<ElementRegistryEvent> { addAll(it) })

    fun base(name: String, protons: Int, neutrons: Int, symbol: String) =
            set(name) { BaseElement(name, protons, neutrons, symbol) }

    fun iso(name: String, standard: Element, nucleons: Int, symbol: String? = null) =
            set(name) { Isotope(name, standard, nucleons, symbol ?: "${standard.symbol}-$nucleons") }
}

class DeferredMatRegister(override val priority: EventPriority = EventPriority.NORMAL) : DeferredStringRegister<Material> {
    private val list = mutableListOf<() -> Material>()

    override fun set(name: String, thing: () -> Material): StringRegistryObject<Material> {
        list += thing
        return StringRegistryObject(name, ::materials)
    }

    private fun addAll(e: MaterialRegistryEvent) {
        list.forEach { e.registry.register(it()) }
    }

    override fun register(bus: IEventBus) = bus.addListener(priority, Consumer<MaterialRegistryEvent> { addAll(it) })

    inline operator fun invoke(name: String, crossinline builder: Material.() -> Unit) = set(name) {
        Material(name).apply {
            builder()
        }
    }

    inline fun dust(name: String, crossinline builder: Material.() -> Unit) = set(name) {
        Material(name).apply {
            set(hasDust) { true }
            builder()
        }
    }

    inline fun gem(name: String, crossinline builder: Material.() -> Unit) = set(name) {
        Material(name).apply {
            set(hasDust) { true }
            set(hasGem) { true }
            builder()
        }
    }

    inline fun ingot(name: String, crossinline builder: Material.() -> Unit) = set(name) {
        Material(name)
                .set(liquidNames) { listOf("molten_$name") }
                .set(gasNames) { listOf("${name}_gas") }
                .set(isAlloy) { true }
                .set(hasDust) { true }
                .set(hasIngot) { true }
                .apply(builder)
    }

    inline fun liquid(name: String, crossinline builder: Material.() -> Unit) = set(name) {
        Material(name)
                .set(blockCompaction) { BlockCompaction.NONE }
                .set(textureSet) { fluid }
                .set(liquidTemp) { 300 }
                .apply(builder)
    }

    inline fun gas(name: String, crossinline builder: Material.() -> Unit) = set(name) {
        Material(name)
                .set(blockCompaction) { BlockCompaction.NONE }
                .set(textureSet) { fluid }
                .set(gasTemp) { 300 }
                .apply(builder)
    }

    inline fun group(name: String, crossinline builder: Material.() -> Unit) = set(name) {
        Material(name).apply {
            set(chemicalGroup) { true }
            builder()
        }
    }
}

class DeferredFormRegister(override val priority: EventPriority = EventPriority.NORMAL) : DeferredStringRegister<Form> {
    private val list = mutableListOf<() -> Form>()
    override fun set(name: String, thing: () -> Form): StringRegistryObject<Form> {
        list += thing
        return StringRegistryObject(name, ::forms)
    }

    private fun addAll(e: FormRegistryEvent) {
        list.forEach { e.registry.register(it()) }
    }

    override fun register(bus: IEventBus) = bus.addListener(priority, Consumer<FormRegistryEvent> { addAll(it) })

    inline operator fun invoke(name: String, crossinline builder: Form.() -> Unit) = Form(name).apply(builder)

    inline fun item(name: String, crossinline builder: Form.() -> Unit) = set(name) {
        Form(name).apply {
            set(item) {
                VersatileItem(ExtendedItemProperties(
                        group = Versatile.MAIN,
                        burnTime = get(burnTime),
                        localizedNameFun = {
                            if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey)
                            else localize()
                        }
                )).setRegistryName(get(registryName))
            }
            builder()
        }
    }

    inline fun block(name: String, crossinline builder: Form.() -> Unit) = set(name) {
        Form(name).apply {
            set(item) {
                VersatileBlockItem(get(block)!!, ExtendedItemProperties(
                        group = Versatile.MAIN,
                        burnTime = get(burnTime)
                )).setRegistryName(get(registryName))
            }
            set(block) {
                VersatileBlock(ExtendedBlockProperties(
                        material = net.minecraft.block.material.Material.IRON,
                        hardness = mat[harvestTier].hardness,
                        resistance = mat[harvestTier].resistance,
                        localizedNameFun = {
                            if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey)
                            else localize()
                        }
                )).setRegistryName(get(registryName))
            }
            set(itemModel) {
                json {
                    "parent" to "versatile:block/materialblocks/" + (if (get(singleTextureSet)) "" else "${mat[textureSet]}/") + name
                }
            }
            builder()
        }
    }

    inline fun fluid(name: String, crossinline builder: Form.() -> Unit) = set(name) {
        Form(name).apply {
            set(singleTextureSet) { true }
            set(indexBlacklist) { listOf(0) }
            set(bucketVolume) { 1000 }
            set(itemTag) { "forge:buckets" }
            set(item) {
                VersatileBucketItem({ get(stillFluid)!! }, ExtendedItemProperties(
                        group = Versatile.MAIN,
                        maxStackSize = 1,
                        burnTime = get(burnTime)
                )).setRegistryName(get(registryName))
            }
            set(block) {
                VersatileFluidBlock({ get(stillFluid)!! }, ExtendedBlockProperties(
                        material = net.minecraft.block.material.Material.WATER,
                        blocksMovement = false
                )).setRegistryName(get(registryName))
            }
            set(stillFluid) {
                ForgeFlowingFluid.Source(get(fluidProperties)!!).also {
                    it.registryName = get(registryName)
                }
            }
            set(flowingFluid) {
                ForgeFlowingFluid.Flowing(get(fluidProperties)!!).also {
                    val stillRegName = get(registryName)
                    it.registryName = ResourceLocation(stillRegName.namespace, "flowing_${stillRegName.path}")
                }
            }
            set(renderType) { DistExecutor.runForDist<RenderType?>({ Supplier { RenderType.getTranslucent() } }, { Supplier { null } }) }
            set(combinedBlocKTags) { emptyList() }
            builder()
        }
    }
}