package com.scientianova.versatile.materialsystem.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.scientianova.versatile.common.extensions.*
import com.scientianova.versatile.common.registry.forms
import com.scientianova.versatile.common.registry.materials
import com.scientianova.versatile.materialsystem.properties.*
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
                            forms[StringArgumentType.getString(this, "name")]?.let {
                                source.sendFeedback(it[itemTag].toComponent(), false)
                            } ?: source.sendErrorMessage(TranslationTextComponent("command.form.error"))
                        }
                    }
                    literal("block") {
                        does {
                            forms[StringArgumentType.getString(this, "name")]?.let {
                                source.sendFeedback(it[blockTag].toComponent(), false)
                            } ?: source.sendErrorMessage(TranslationTextComponent("command.form.error"))
                        }
                    }
                }
                literal("items") {
                    does {
                        forms[StringArgumentType.getString(this, "name")]?.let { form ->
                            materials.forEach { mat ->
                                val item = item[mat, form] ?: return@forEach
                                source.sendFeedback(item.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.form.error"))
                    }
                }
                literal("blocks") {
                    does {
                        forms[StringArgumentType.getString(this, "name")]?.let { form ->
                            materials.forEach { mat ->
                                val block = block[mat, form] ?: return@forEach
                                source.sendFeedback(block.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.form.error"))
                    }
                }
                literal("fluids") {
                    does {
                        forms[StringArgumentType.getString(this, "name")]?.let { form ->
                            materials.forEach { mat ->
                                val fluid = stillFluid[mat, form] ?: return@forEach
                                source.sendFeedback(fluid.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.form.error"))
                    }
                }
            }
        }
    }
}