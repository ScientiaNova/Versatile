package com.emosewapixel.pixellib.proxy

import com.emosewapixel.pixellib.items.MaterialItem
import com.emosewapixel.pixellib.materialsystem.MaterialRegistry
import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.materials.DustMaterial
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialItem
import com.emosewapixel.pixellib.materialsystem.types.BlockType
import com.emosewapixel.pixellib.resources.FakeResourcePackFinder
import com.emosewapixel.pixellib.resources.JSONAdder
import com.google.gson.JsonObject
import net.alexwells.kottle.KotlinEventBusSubscriber
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.color.IBlockColor
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent

@KotlinEventBusSubscriber(Dist.CLIENT)
object ClientProxy : IModProxy {
    override fun init() {
        Minecraft.getInstance().resourcePackList.addPackFinder(FakeResourcePackFinder())
    }

    override fun enque(e: InterModEnqueueEvent) {
        color()
    }

    override fun process(e: InterModProcessEvent) {}

    private fun color() {
        MaterialItems.getAll().filterIsInstance<MaterialItem>().forEach { item ->
            Minecraft.getInstance().itemColors.register(IItemColor { stack: ItemStack, _ ->
                val sItem = stack.item
                if (sItem is IMaterialItem)
                    return@IItemColor if (MaterialRegistry.USES_UNREFINED_COLOR in (sItem as IMaterialItem).objType.typeTags && (sItem as IMaterialItem).mat is DustMaterial)
                        ((sItem as IMaterialItem).mat as DustMaterial).unrefinedColor
                    else
                        (sItem as IMaterialItem).mat.color
                return@IItemColor -1
            }, item)
        }

        MaterialBlocks.getAll().filterIsInstance<IMaterialItem>().forEach {
            val block = it as Block
            Minecraft.getInstance().blockColors.register(IBlockColor { state: BlockState, _, _, index: Int ->
                val sBlock = state.block
                if (sBlock is IMaterialItem && index == 0)
                    return@IBlockColor if (MaterialRegistry.USES_UNREFINED_COLOR in (sBlock as IMaterialItem).objType.typeTags && (sBlock as IMaterialItem).mat is DustMaterial)
                        ((sBlock as IMaterialItem).mat as DustMaterial).unrefinedColor
                    else
                        (sBlock as IMaterialItem).mat.color
                return@IBlockColor -1
            }, block)

            Minecraft.getInstance().itemColors.register(IItemColor { stack: ItemStack, index: Int ->
                val sBlock = Block.getBlockFromItem(stack.item)
                if (sBlock is IMaterialItem && index == 0)
                    return@IItemColor if (MaterialRegistry.USES_UNREFINED_COLOR in (sBlock as IMaterialItem).objType.typeTags && (sBlock as IMaterialItem).mat is DustMaterial)
                        ((sBlock as IMaterialItem).mat as DustMaterial).unrefinedColor
                    else
                        (sBlock as IMaterialItem).mat.color
                return@IItemColor -1
            }, block.asItem())
        }
    }
}

fun addModelJSONs() {
    MaterialItems.getAll().filterIsInstance<IMaterialItem>().forEach { i ->
        val registryName = (i as Item).registryName
        val type = (i as IMaterialItem).objType
        val model = JsonObject()
        model.addProperty("parent", registryName!!.namespace + ":item/materialitems/" + if (MaterialRegistry.SINGLE_TEXTURE_TYPE in type.typeTags) type.name else (i as IMaterialItem).mat.textureType + "/" + type.name)
        JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "models/item/" + registryName.path + ".json"), model)
    }

    MaterialBlocks.getAll().filterIsInstance<IMaterialItem>().forEach { b ->
        val registryName = (b as Block).registryName
        val type = (b as IMaterialItem).objType
        val model = JsonObject()
        model.addProperty("parent", registryName!!.namespace + ":block/materialblocks/" + if (MaterialRegistry.SINGLE_TEXTURE_TYPE in type.typeTags) type.name else (b as IMaterialItem).mat.textureType + "/" + type.name)
        JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "models/item/" + registryName.path + ".json"), model)
        JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "blockstates/" + registryName.path + ".json"), (type as BlockType).getBlockStateJson(b as IMaterialItem))
    }
}