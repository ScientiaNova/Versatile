package com.scientianova.versatile.materialsystem.commands

import com.scientianova.versatile.common.extensions.*
import com.scientianova.versatile.materialsystem.materials.Material
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.scientianova.versatile.common.registry.FORMS
import com.scientianova.versatile.common.registry.MATERIALS
import com.scientianova.versatile.materialsystem.materials.material
import com.scientianova.versatile.materialsystem.properties.BLOCK
import com.scientianova.versatile.materialsystem.properties.ITEM
import com.scientianova.versatile.materialsystem.properties.STILL_FLUID
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
                        MATERIALS[StringArgumentType.getString(this, "name")]?.sendHierarchy()
                                ?: source.sendErrorMessage(TranslationTextComponent("command.material.error"))
                    }
                }
                literal("items") {
                    does {
                        MATERIALS[StringArgumentType.getString(this, "name")]?.let { mat ->
                            FORMS.forEach { global ->
                                val item = global[mat]?.get(ITEM) ?: return@forEach
                                source.sendFeedback(item.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.material.error"))
                    }
                }
                literal("blocks") {
                    does {
                        MATERIALS[StringArgumentType.getString(this, "name")]?.let { mat ->
                            FORMS.forEach { global ->
                                val block = global[mat]?.get(BLOCK) ?: return@forEach
                                source.sendFeedback(block.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.material.error"))
                    }
                }
                literal("fluids") {
                    does {
                        MATERIALS[StringArgumentType.getString(this, "name")]?.let { mat ->
                            FORMS.forEach { global ->
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