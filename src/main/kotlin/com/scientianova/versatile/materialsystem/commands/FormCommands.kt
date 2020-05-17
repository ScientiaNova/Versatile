package com.scientianova.versatile.materialsystem.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.scientianova.versatile.common.extensions.*
import com.scientianova.versatile.common.registry.FORMS
import net.minecraft.command.CommandSource
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.text.TranslationTextComponent

class FormCommands(dispatcher: CommandDispatcher<CommandSource>) {
    init {
        dispatcher.register("form") {
            requires {
                it.hasPermissionLevel(1) && it.entity is ServerPlayerEntity
            }
            argument("name", StringArgumentType.word()) {
                literal("tag") {
                    literal("item") {
                        does {
                            FORMS[StringArgumentType.getString(this, "name")]?.let {
                                source.sendFeedback(it.itemTagName.toComponent(), false)
                            } ?: source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                        }
                    }
                    literal("block") {
                        does {
                            FORMS[StringArgumentType.getString(this, "name")]?.let {
                                source.sendFeedback(it.blockTagName.toComponent(), false)
                            } ?: source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                        }
                    }
                }
                literal("items") {
                    does {
                        FORMS[StringArgumentType.getString(this, "name")]?.let { global ->
                            global.specialized.forEach { regular ->
                                val item = regular.item ?: return@forEach
                                source.sendFeedback(item.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                    }
                }
                literal("blocks") {
                    does {
                        FORMS[StringArgumentType.getString(this, "name")]?.let { global ->
                            global.specialized.forEach { regular ->
                                val block = regular.block ?: return@forEach
                                source.sendFeedback(block.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                    }
                }
                literal("fluids") {
                    does {
                        FORMS[StringArgumentType.getString(this, "name")]?.let { global ->
                            global.specialized.forEach { regular ->
                                val fluid = regular.stillFluid ?: return@forEach
                                source.sendFeedback(fluid.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.objtype.error"))
                    }
                }
            }
        }
    }
}