package com.emosewapixel.pixellib.commands

import com.emosewapixel.pixellib.extensions.*
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
                        val type = ObjTypes[StringArgumentType.getString(this, "name")]
                        if (type != null) {
                            source.sendFeedback(type.itemTag.id.toString().toComponent(), false)
                        } else source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                    }
                }
                literal("items") {
                    does {
                        val type = ObjTypes[StringArgumentType.getString(this, "name")]
                        if (type != null) {
                            MaterialItems[type]?.values?.forEach {
                                source.sendFeedback(it.registryName!!.toString().toComponent(), false)
                            } ?: source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                        } else source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                    }
                }
                literal("blocks") {
                    does {
                        val type = ObjTypes[StringArgumentType.getString(this, "name")]
                        if (type != null) {
                            MaterialBlocks[type]?.values?.forEach {
                                source.sendFeedback(it.registryName!!.toString().toComponent(), false)
                            } ?: source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                        } else source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                    }
                }
                literal("fluids") {
                    does {
                        val type = ObjTypes[StringArgumentType.getString(this, "name")]
                        if (type != null) {
                            MaterialFluids[type]?.values?.forEach {
                                source.sendFeedback(it.registryName!!.toString().toComponent(), false)
                            } ?: source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                        } else source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                    }
                }
            }
        }
    }
}