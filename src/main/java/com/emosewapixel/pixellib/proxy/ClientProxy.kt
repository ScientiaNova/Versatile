package com.emosewapixel.pixellib.proxy

import com.emosewapixel.pixellib.extensions.json
import com.emosewapixel.pixellib.materialsystem.addition.MaterialRegistry
import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.MaterialFluids
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.types.BlockType
import com.emosewapixel.pixellib.materialsystem.types.FluidType
import com.emosewapixel.pixellib.resources.FakeResourcePackFinder
import com.emosewapixel.pixellib.resources.JSONAdder
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.color.IBlockColor
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.item.BlockItem
import net.minecraft.item.BucketItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent

object ClientProxy : IModProxy {
    override fun init() {
        Minecraft.getInstance().resourcePackList.addPackFinder(FakeResourcePackFinder)
    }

    override fun enque(e: InterModEnqueueEvent) {
        color()
    }

    override fun process(e: InterModProcessEvent) {}

    private fun color() {
        MaterialItems.all.filterIsInstance<IMaterialObject>().forEach {
            val item = it as Item
            Minecraft.getInstance().itemColors.register(IItemColor { stack: ItemStack, index ->
                val sItem = stack.item as IMaterialObject
                if (index !in sItem.objType.indexBlackList)
                    sItem.objType.color(sItem.mat)
                else
                    -1
            }, item)
        }

        MaterialBlocks.all.filterIsInstance<IMaterialObject>().forEach {
            val block = it as Block
            Minecraft.getInstance().blockColors.register(IBlockColor { state: BlockState, _, _, index: Int ->
                val sBlock = state.block as IMaterialObject
                if (index !in sBlock.objType.indexBlackList)
                    sBlock.objType.color(sBlock.mat)
                else
                    -1
            }, block)
        }
    }
}

fun addModelJSONs() {
    MaterialItems.all.filterIsInstance<IMaterialObject>().forEach {
        val registryName = (it as Item).registryName!!
        val type = it.objType
        when (it) {
            is BlockItem -> {
                val model = json {
                    "parent" to registryName.namespace + ":block/materialblocks/" + if (MaterialRegistry.SINGLE_TEXTURE_TYPE in type.typeTags) type.name else "${it.mat.textureType}/${type.name}"
                }
                JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "models/item/" + registryName.path + ".json"), model)
            }
            is BucketItem -> {
                val model = json {
                    "parent" to registryName.namespace + ":item/materialitems/" + (if (MaterialRegistry.SINGLE_TEXTURE_TYPE in type.typeTags) type.name else "${it.mat.textureType}/${type.name}") + (if ((it.objType as FluidType).gaseousFun(it.mat)) "_gas" else "") + "_bucket"
                }
                JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "models/item/" + registryName.path + ".json"), model)
            }
            else -> {
                val model = json {
                    "parent" to registryName.namespace + ":item/materialitems/" + if (MaterialRegistry.SINGLE_TEXTURE_TYPE in type.typeTags) type.name else "${it.mat.textureType}/${type.name}"
                }
                JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "models/item/" + registryName.path + ".json"), model)
            }
        }
    }

    MaterialBlocks.all.filter { it is IMaterialObject && it !is FlowingFluidBlock }.forEach {
        val registryName = it.registryName!!
        val type = (it as IMaterialObject).objType
        JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "blockstates/" + registryName.path + ".json"), (type as BlockType).buildBlockStateJson(it))
    }

    MaterialFluids.all.filterIsInstance<IMaterialObject>().forEach {
        val type = it.objType
        val registryName = it.objType.buildRegistryName(it.mat)
        JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "blockstates/" + registryName.path + ".json"), (type as FluidType).buildBlockStateJson(it))
    }
}