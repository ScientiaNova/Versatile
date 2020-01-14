package com.scientianovateam.versatile.materialsystem.commands

import com.scientianovateam.versatile.common.extensions.*
import com.scientianovateam.versatile.materialsystem.lists.MaterialBlocks
import com.scientianovateam.versatile.materialsystem.lists.MaterialFluids
import com.scientianovateam.versatile.materialsystem.lists.MaterialItems
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.scientianovateam.versatile.materialsystem.lists.FORMS
import net.minecraft.command.CommandSource
import net.minecraft.command.arguments.ItemArgument
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.text.TranslationTextComponent

class FormCommand(dispatcher: CommandDispatcher<CommandSource>) {
    init {
        dispatcher.register("form") {
            requires {
                it.hasPermissionLevel(1) && it.entity is ServerPlayerEntity
            }
            literal("get") {
                does {
                    val item = source.asPlayer().heldItemMainhand.item
                    MaterialItems.getItemForm(item)?.let {
                        source.sendFeedback(it.name.toComponent(), false)
                    } ?: source.sendErrorMessage(TranslationTextComponent("command.form.item.error"))
                }
                argument("item", ItemArgument()) {
                    does {
                        val item = ItemArgument.getItem(this, "item").item
                        MaterialItems.getItemForm(item)?.let {
                            source.sendFeedback(it.name.toComponent(), false)
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.form.item.error"))
                    }
                }
            }
            argument("name", StringArgumentType.word()) {
                literal("tag") {
                    literal("item") {
                        does {
                            FORMS[StringArgumentType.getString(this, "name")]?.let {
                                source.sendFeedback(it.itemTag.toString().toComponent(), false)
                            } ?: source.sendErrorMessage(TranslationTextComponent("command.form.error"))
                        }
                    }
                    literal("block") {
                        does {
                            FORMS[StringArgumentType.getString(this, "name")]?.let {
                                source.sendFeedback(it.blockTag.toString().toComponent(), false)
                            } ?: source.sendErrorMessage(TranslationTextComponent("command.form.error"))
                        }
                    }
                }
                literal("items") {
                    does {
                        FORMS[StringArgumentType.getString(this, "name")]?.let {
                            MaterialItems[it]?.values?.forEach { item ->
                                source.sendFeedback(item.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.form.error"))
                    }
                }
                literal("blocks") {
                    does {
                        FORMS[StringArgumentType.getString(this, "name")]?.let {
                            MaterialBlocks[it]?.values?.forEach { block ->
                                source.sendFeedback(block.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.form.error"))
                    }
                }
                literal("fluids") {
                    does {
                        FORMS[StringArgumentType.getString(this, "name")]?.let {
                            MaterialFluids[it]?.values?.forEach { fluid ->
                                source.sendFeedback(fluid.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.form.error"))
                    }
                }
            }
        }
    }
}