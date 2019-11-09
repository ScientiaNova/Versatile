package com.emosewapixel.pixellib.commands

import com.emosewapixel.ktlib.extensions.*
import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.MaterialFluids
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.lists.ObjTypes
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import net.minecraft.command.CommandSource
import net.minecraft.command.arguments.ItemArgument
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.text.TranslationTextComponent

class ObjTypeCommand(dispatcher: CommandDispatcher<CommandSource>) {
    init {
        dispatcher.register("objtype") {
            requires {
                it.hasPermissionLevel(1) && it.entity is ServerPlayerEntity
            }
            literal("get") {
                does {
                    val item = source.asPlayer().heldItemMainhand.item
                    if (item in MaterialItems)
                        source.sendFeedback(MaterialItems.getItemObjType(item)!!.name.toComponent(), false)
                    else
                        source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                }
                argument("item", ItemArgument()) {
                    does {
                        val item = ItemArgument.getItem(this, "item").item
                        if (item in MaterialItems)
                            source.sendFeedback(MaterialItems.getItemObjType(item)!!.name.toComponent(), false)
                        else
                            source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                    }
                }
            }
            argument("name", StringArgumentType.word()) {
                literal("tag") {
                    does {
                        ObjTypes[StringArgumentType.getString(this, "name")]?.let {
                            source.sendFeedback(it.itemTag.id.toString().toComponent(), false)
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                    }
                }
                literal("items") {
                    does {
                        ObjTypes[StringArgumentType.getString(this, "name")]?.let {
                            MaterialItems[it]?.values?.forEach { item ->
                                source.sendFeedback(item.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                    }
                }
                literal("blocks") {
                    does {
                        ObjTypes[StringArgumentType.getString(this, "name")]?.let {
                            MaterialBlocks[it]?.values?.forEach { block ->
                                source.sendFeedback(block.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                    }
                }
                literal("fluids") {
                    does {
                        ObjTypes[StringArgumentType.getString(this, "name")]?.let {
                            MaterialFluids[it]?.values?.forEach { fluid ->
                                source.sendFeedback(fluid.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                    }
                }
            }
        }
    }
}