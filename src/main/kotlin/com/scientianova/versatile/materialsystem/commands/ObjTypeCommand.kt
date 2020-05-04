package com.scientianova.versatile.materialsystem.commands

import com.scientianovateam.versatile.common.extensions.*
import com.scientianovateam.versatile.materialsystem.lists.MaterialBlocks
import com.scientianovateam.versatile.materialsystem.lists.MaterialFluids
import com.scientianovateam.versatile.materialsystem.lists.MaterialItems
import com.scientianovateam.versatile.materialsystem.lists.ObjTypes
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
                    MaterialItems.getItemObjType(item)?.let {
                        source.sendFeedback(it.name.toComponent(), false)
                    } ?: source.sendErrorMessage(TranslationTextComponent("command.objtype.item.error"))
                }
                argument("item", ItemArgument()) {
                    does {
                        val item = ItemArgument.getItem(this, "item").item
                        MaterialItems.getItemObjType(item)?.let {
                            source.sendFeedback(it.name.toComponent(), false)
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.objtype.item.error"))
                    }
                }
            }
            argument("name", StringArgumentType.word()) {
                literal("tag") {
                    literal("item") {
                        does {
                            ObjTypes[StringArgumentType.getString(this, "name")]?.let {
                                source.sendFeedback(it.itemTagName.toComponent(), false)
                            } ?: source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                        }
                    }
                    literal("block") {
                        does {
                            ObjTypes[StringArgumentType.getString(this, "name")]?.let {
                                source.sendFeedback(it.blockTagName.toComponent(), false)
                            } ?: source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                        }
                    }
                    literal("fluid") {
                        does {
                            ObjTypes[StringArgumentType.getString(this, "name")]?.let {
                                source.sendFeedback(it.fluidTagName.toComponent(), false)
                            } ?: source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                        }
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