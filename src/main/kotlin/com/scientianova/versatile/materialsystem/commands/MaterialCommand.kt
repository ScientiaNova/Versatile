package com.scientianova.versatile.materialsystem.commands

import com.scientianovateam.versatile.common.extensions.*
import com.scientianovateam.versatile.materialsystem.lists.MaterialBlocks
import com.scientianovateam.versatile.materialsystem.lists.MaterialFluids
import com.scientianovateam.versatile.materialsystem.lists.MaterialItems
import com.scientianovateam.versatile.materialsystem.lists.Materials
import com.scientianovateam.versatile.materialsystem.main.Material
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
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
                    MaterialItems.getItemMaterial(item)?.let {
                        source.sendFeedback(it.name.toComponent(), false)
                    } ?: source.sendErrorMessage(TranslationTextComponent("command.material.item.error"))
                }
                argument("item", ItemArgument()) {
                    does {
                        val item = ItemArgument.getItem(this, "item").item
                        MaterialItems.getItemMaterial(item)?.let {
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
                        Materials[StringArgumentType.getString(this, "name")]?.sendHierarchy()
                                ?: source.sendErrorMessage(TranslationTextComponent("command.material.error"))
                    }
                }
                literal("items") {
                    does {
                        Materials[StringArgumentType.getString(this, "name")]?.let {
                            MaterialItems[it]?.values?.forEach { item ->
                                source.sendFeedback(item.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.material.error"))
                    }
                }
                literal("blocks") {
                    does {
                        Materials[StringArgumentType.getString(this, "name")]?.let {
                            MaterialBlocks[it]?.values?.forEach { block ->
                                source.sendFeedback(block.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.material.error"))
                    }
                }
                literal("fluids") {
                    does {
                        Materials[StringArgumentType.getString(this, "name")]?.let {
                            MaterialFluids[it]?.values?.forEach { fluid ->
                                source.sendFeedback(fluid.registryName!!.toString().toComponent(), false)
                            }
                        } ?: source.sendErrorMessage(TranslationTextComponent("command.material.error"))
                    }
                }
            }
        }
    }
}