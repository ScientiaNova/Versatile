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
import com.scientianova.versatile.materialsystem.forms.GlobalForm
import com.scientianova.versatile.materialsystem.materials.FLUID
import com.scientianova.versatile.materialsystem.materials.Material
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

@Suppress("UNCHECKED_CAST")
class DeferredElemRegister(override val priority: EventPriority = EventPriority.NORMAL) : DeferredStringRegister<Element> {
    private val list = mutableListOf<() -> Element>()
    override fun set(name: String, thing: () -> Element): StringRegistryObject<Element> {
        list += thing
        return StringRegistryObject(name, ::ELEMENTS)
    }

    private fun addAll(e: ElementRegistryEvent) {
        list.forEach { e.registry.register(it()) }
    }

    override fun register(bus: IEventBus)  = bus.addListener(priority, Consumer<ElementRegistryEvent> { addAll(it) })

    fun base(name: String, protons: Int, neutrons: Int, symbol: String) =
            set(name) { BaseElement(name, protons, neutrons, symbol) } as StringRegistryObject<BaseElement>

    fun iso(name: String, standard: () -> BaseElement, nucleons: Int, symbol: String? = null) =
            set(name) { Isotope(name, standard, nucleons, symbol?.let { { it } }) } as StringRegistryObject<Isotope>
}

class DeferredMatRegister(override val priority: EventPriority = EventPriority.NORMAL) : DeferredStringRegister<Material> {
    private val list = mutableListOf<() -> Material>()

    override fun set(name: String, thing: () -> Material): StringRegistryObject<Material> {
        list += thing
        return StringRegistryObject(name, ::MATERIALS)
    }

    private fun addAll(e: MaterialRegistryEvent) {
        list.forEach { e.registry.register(it()) }
    }

    override fun register(bus: IEventBus)  = bus.addListener(priority, Consumer<MaterialRegistryEvent> { addAll(it) })

    inline operator fun invoke(vararg names: String, crossinline builder: Material.() -> Unit) = set(names.first()) {
        Material().apply {
            associatedNames = listOf(*names)
            builder()
        }
    }

    inline fun dust(vararg names: String, crossinline builder: Material.() -> Unit) = set(names.first()) {
        Material().apply {
            associatedNames = listOf(*names)
            hasDust = true
            builder()
        }
    }

    inline fun gem(vararg names: String, crossinline builder: Material.() -> Unit) = set(names.first()) {
        Material().apply {
            associatedNames = listOf(*names)
            hasDust = true
            hasGem = true
            builder()
        }
    }

    inline fun ingot(vararg names: String, crossinline builder: Material.() -> Unit) = set(names.first()) {
        Material().apply {
            associatedNames = listOf(*names)
            liquidNames = names.map { "molten_$it" }
            gasNames = names.map { "${it}_gas" }
            compoundType = CompoundType.ALLOY
            hasDust = true
            hasIngot = true
            builder()
        }
    }

    inline fun liquid(vararg names: String, crossinline builder: Material.() -> Unit) = set(names.first()) {
        Material().apply {
            blockCompaction = BlockCompaction.NONE
            associatedNames = listOf(*names)
            textureSet = FLUID
            liquidTemperature = 300
            builder()
        }
    }

    inline fun gas(vararg names: String, crossinline builder: Material.() -> Unit) = set(names.first()) {
        Material().apply {
            blockCompaction = BlockCompaction.NONE
            associatedNames = listOf(*names)
            textureSet = FLUID
            gasTemperature = 300
            builder()
        }
    }

    inline fun group(vararg names: String, crossinline builder: Material.() -> Unit) = set(names.first()) {
        Material().apply {
            associatedNames = listOf(*names)
            displayType = DisplayType.GROUP
            builder()
        }
    }
}

class DeferredFormRegister(override val priority: EventPriority = EventPriority.NORMAL) : DeferredStringRegister<GlobalForm> {
    private val list = mutableListOf<() -> GlobalForm>()
    override fun set(name: String, thing: () -> GlobalForm): StringRegistryObject<GlobalForm> {
        list += thing
        return StringRegistryObject(name, ::FORMS)
    }

    private fun addAll(e: FormRegistryEvent) {
        list.forEach { e.registry.register(it()) }
    }

    override fun register(bus: IEventBus)  = bus.addListener(priority, Consumer<FormRegistryEvent> { addAll(it) })

    inline operator fun invoke(name: String, crossinline builder: GlobalForm.() -> Unit) = GlobalForm().apply {
        this.name = name
        builder()
    }

    inline fun item(name: String, crossinline builder: GlobalForm.() -> Unit) = set(name) {
        GlobalForm().apply {
            this.name = name
            ITEM {
                VersatileItem(ExtendedItemProperties(
                        group = Versatile.MAIN,
                        burnTime = burnTime,
                        localizedNameFun = {
                            if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey)
                            else localize()
                        }
                )).setRegistryName(registryName)
            }
            builder()
        }
    }

    inline fun block(name: String, crossinline builder: GlobalForm.() -> Unit) = set(name) {
        GlobalForm().apply {
            this.name = name
            ITEM {
                VersatileBlockItem(block!!, ExtendedItemProperties(
                        group = Versatile.MAIN,
                        burnTime = burnTime
                )).setRegistryName(registryName)
            }
            BLOCK {
                VersatileBlock(ExtendedBlockProperties(
                        material = net.minecraft.block.material.Material.IRON,
                        hardness = mat.harvestTier.hardness,
                        resistance = mat.harvestTier.resistance,
                        localizedNameFun = {
                            if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey)
                            else localize()
                        }
                )).setRegistryName(registryName)
            }
            ITEM_MODEL {
                json {
                    "parent" to "versatile:block/materialblocks/" + (if (singleTextureSet) "" else "${mat.textureSet}/") + name
                }
            }
            builder()
        }
    }

    inline fun fluid(name: String, crossinline builder: GlobalForm.() -> Unit) = set(name) {
        GlobalForm().apply {
            this.name = name
            singleTextureSet = true
            indexBlacklist = listOf(0)
            bucketVolume = 1000
            ITEM {
                VersatileBucketItem({ stillFluid!! }, ExtendedItemProperties(
                        group = Versatile.MAIN,
                        maxStackSize = 1,
                        burnTime = burnTime
                )).setRegistryName(registryName)
            }
            BLOCK {
                VersatileFluidBlock({ stillFluid!! }, ExtendedBlockProperties(
                        material = net.minecraft.block.material.Material.WATER,
                        blocksMovement = false
                )).setRegistryName(registryName)
            }
            STILL_FLUID {
                ForgeFlowingFluid.Source(fluidProperties!!).apply {
                    registryName = this@STILL_FLUID.registryName
                }
            }
            FLOWING_FLUID {
                ForgeFlowingFluid.Flowing(fluidProperties!!).apply {
                    val stillRegName = this@FLOWING_FLUID.registryName
                    registryName = ResourceLocation(stillRegName.namespace, "flowing_${stillRegName.path}")
                }
            }
            RENDER_TYPE { DistExecutor.runForDist<RenderType?>({ Supplier { RenderType.getTranslucent() } }, { Supplier { null } }) }
            COMBINED_ITEM_TAGS { emptyList() }
            COMBINED_BLOCK_TAGS { emptyList() }
            builder()
        }
    }
}