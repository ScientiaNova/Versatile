package com.emosewapixel.pixellib.proxy

import com.emosewapixel.pixellib.extensions.json
import com.emosewapixel.pixellib.items.MaterialItem
import com.emosewapixel.pixellib.materialsystem.MaterialRegistry
import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.materials.DustMaterial
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.types.BlockType
import com.emosewapixel.pixellib.resources.FakeResourcePackFinder
import com.emosewapixel.pixellib.resources.JSONAdder
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
                if (sItem is IMaterialObject)
                    return@IItemColor if (MaterialRegistry.USES_UNREFINED_COLOR in (sItem as IMaterialObject).objType.typeTags && (sItem as IMaterialObject).mat is DustMaterial)
                        ((sItem as IMaterialObject).mat as DustMaterial).unrefinedColor
                    else
                        (sItem as IMaterialObject).mat.color
                return@IItemColor -1
            }, item)
        }

        MaterialBlocks.getAll().filterIsInstance<IMaterialObject>().forEach {
            val block = it as Block
            Minecraft.getInstance().blockColors.register(IBlockColor { state: BlockState, _, _, index: Int ->
                val sBlock = state.block
                if (sBlock is IMaterialObject && index == 0)
                    return@IBlockColor if (MaterialRegistry.USES_UNREFINED_COLOR in (sBlock as IMaterialObject).objType.typeTags && (sBlock as IMaterialObject).mat is DustMaterial)
                        ((sBlock as IMaterialObject).mat as DustMaterial).unrefinedColor
                    else
                        (sBlock as IMaterialObject).mat.color
                return@IBlockColor -1
            }, block)

            Minecraft.getInstance().itemColors.register(IItemColor { stack: ItemStack, index: Int ->
                val sBlock = Block.getBlockFromItem(stack.item)
                if (sBlock is IMaterialObject && index == 0)
                    return@IItemColor if (MaterialRegistry.USES_UNREFINED_COLOR in (sBlock as IMaterialObject).objType.typeTags && (sBlock as IMaterialObject).mat is DustMaterial)
                        ((sBlock as IMaterialObject).mat as DustMaterial).unrefinedColor
                    else
                        (sBlock as IMaterialObject).mat.color
                return@IItemColor -1
            }, block.asItem())
        }
    }
}

fun addModelJSONs() {
    MaterialItems.getAll().filterIsInstance<IMaterialObject>().forEach {
        val registryName = (it as Item).registryName!!
        val type = (it as IMaterialObject).objType
        val model = json {
            "parent" to registryName.namespace + ":item/materialitems/" + if (MaterialRegistry.SINGLE_TEXTURE_TYPE in type.typeTags) type.name else "${(it as IMaterialObject).mat.textureType}/${type.name}"
        }
        JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "models/item/" + registryName.path + ".json"), model)
    }

    MaterialBlocks.getAll().filterIsInstance<IMaterialObject>().forEach {
        val registryName = (it as Block).registryName!!
        val type = (it as IMaterialObject).objType
        val model = json {
            "parent" to registryName.namespace+":block/materialblocks/"+if (MaterialRegistry.SINGLE_TEXTURE_TYPE in type.typeTags) type.name else "${(it as IMaterialObject).mat.textureType}/${type.name}"
        }
        JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "models/item/" + registryName.path + ".json"), model)
        JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "blockstates/" + registryName.path + ".json"), (type as BlockType).getBlockStateJson(it as IMaterialObject))
    }
}