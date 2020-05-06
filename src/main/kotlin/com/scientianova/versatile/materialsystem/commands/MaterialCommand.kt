package com.scientianova.versatile.materialsystem.commands

import com.scientianova.versatile.common.extensions.*
import com.scientianova.versatile.materialsystem.main.Material
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.scientianova.versatile.materialsystem.addition.BLOCK
import com.scientianova.versatile.materialsystem.addition.ITEM
import com.scientianova.versatile.materialsystem.addition.STILL_FLUID
import com.scientianova.versatile.materialsystem.lists.allForms
import com.scientianova.versatile.materialsystem.lists.material
import com.scientianova.versatile.materialsystem.lists.materialFor
import net.minecraft.command.CommandSource
import net.minecraft.command.arguments.ItemArgument
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TranslationTextComponent

class MaterialCommand(dispatcher: CommandDispatcher<CommandSource>) {
    init {
        dispatcher.register("material") {
            requires {
                it.hasPermissionLevel(1) && it.entity is ServerPlayerEntity
            }
            literal("get") {
                does {
                    val item = source.asPlayer().heldItemMainhand.item
                    item.material?.let {
                        source.sendFeedback(it.name.toComponent(), false)
                    } ?: source.sendErrorMessage(TranslationTextComponent("command.material.item.error"))
                }
                argument("item", ItemArgument()) {
                    does {
                        val item = ItemArgument.getItem(this, "item").item
                        item.material?.let {
                            source.sendFeedback(it.name.toComponent(), false)
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.material.item.error"))
                    }
                }
            }
            argument("name", StringArgumentType.word()) {
                literal("hierarchy") {
                    does {
                        fun Material.sendHierarchy(level: Int = 0) {
                            source.sendFeedback(StringTextComponent((if (level > 0) " ".repeat(level - 1) + "-" else "")
                                    + localizedName.formattedText), false
                            )
                            composition.forEach { it.material.sendHierarchy(level + 1) }
                        }
                        materialFor(StringArgumentType.getString(this, "name"))?.sendHierarchy()
                                ?: source.sendErrorMessage(TranslationTextComponent("command.material.error"))
                    }
                }
                literal("items") {
                    does {
                        materialFor(StringArgumentType.getString(this, "name"))?.let { mat ->
                            allForms.forEach { global ->
                                val item = global[mat]?.get(ITEM) ?: return@forEach
                                source.sendFeedback(item.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.material.error"))
                    }
                }
                literal("blocks") {
                    does {
                        materialFor(StringArgumentType.getString(this, "name"))?.let { mat ->
                            allForms.forEach { global ->
                                val block = global[mat]?.get(BLOCK) ?: return@forEach
                                source.sendFeedback(block.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.material.error"))
                    }
                }
                literal("fluids") {
                    does {
                        materialFor(StringArgumentType.getString(this, "name"))?.let { mat ->
                            allForms.forEach { global ->
                                val fluid = global[mat]?.get(STILL_FLUID) ?: return@forEach
                                source.sendFeedback(fluid.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.material.error"))
                    }
                }
            }
        }
    }
}