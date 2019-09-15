package com.emosewapixel.pixellib.commands

import com.emosewapixel.pixellib.extensions.argument
import com.emosewapixel.pixellib.extensions.does
import com.emosewapixel.pixellib.extensions.literal
import com.emosewapixel.pixellib.extensions.register
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.command.CommandSource
import net.minecraft.command.arguments.ItemArgument
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TranslationTextComponent

class MaterialItemCommand(dispatcher: CommandDispatcher<CommandSource>) {
    init {
        dispatcher.register("materialitem") {
            requires {
                it.hasPermissionLevel(1) && it.entity is ServerPlayerEntity
            }
            literal("material") {
                literal("name") {
                    does {
                        val item = source.asPlayer().heldItemMainhand.item
                        if (item in MaterialItems)
                            source.sendFeedback(MaterialItems.getItemMaterial(item)!!.localizedName, false)
                        else
                            source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                    }
                }
                literal("hierarchy") {
                    does {
                        val item = source.asPlayer().heldItemMainhand.item

                        fun Material.sendHierarchy(level: Int = 0) {
                            source.sendFeedback(StringTextComponent((if (level > 0) " ".repeat(level - 1) + "-" else "") + localizedName.formattedText), false)
                            if (composition.isNotEmpty())
                                composition.forEach { it.material.sendHierarchy(level + 1) }
                        }

                        if (item in MaterialItems)
                            MaterialItems.getItemMaterial(item)!!.sendHierarchy()
                        else
                            source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                    }
                }
            }
            literal("type") {
                literal("name") {
                    does {
                        val item = source.asPlayer().heldItemMainhand.item
                        if (item in MaterialItems)
                            source.sendFeedback(StringTextComponent((MaterialItems.getItemObjType(item)!!).name.normalFormat()), false)
                        else
                            source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                    }
                }
                literal("tag") {
                    does {
                        val item = source.asPlayer().heldItemMainhand.item
                        if (item in MaterialItems)
                            source.sendFeedback(StringTextComponent((MaterialItems.getItemObjType(item)!!).itemTag.id.toString()), false)
                        else
                            source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                    }
                }
            }
            argument("item", ItemArgument()) {
                literal("material") {
                    requires {
                        it.entity is ServerPlayerEntity
                    }
                    literal("name") {
                        does {
                            val item = ItemArgument.getItem(this, "item").item
                            if (item in MaterialItems)
                                source.sendFeedback(MaterialItems.getItemMaterial(item)!!.localizedName, false)
                            else
                                source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                        }
                    }
                    literal("hierarchy") {
                        does {
                            val item = ItemArgument.getItem(this, "item").item

                            fun Material.sendHierarchy(level: Int = 0) {
                                source.sendFeedback(StringTextComponent((if (level > 0) " ".repeat(level - 1) + "-" else "") + localizedName.formattedText), false)
                                if (composition.isNotEmpty())
                                    composition.forEach { it.material.sendHierarchy(level + 1) }
                            }

                            if (item in MaterialItems)
                                MaterialItems.getItemMaterial(item)!!.sendHierarchy()
                            else
                                source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                        }
                    }
                }
                literal("type") {
                    requires {
                        it.entity is ServerPlayerEntity
                    }
                    literal("name") {
                        does {
                            val item = ItemArgument.getItem(this, "item").item
                            if (item in MaterialItems)
                                source.sendFeedback(StringTextComponent((MaterialItems.getItemObjType(item)!!).name.normalFormat()), false)
                            else
                                source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                        }
                    }
                    literal("tag") {
                        does {
                            val item = ItemArgument.getItem(this, "item").item
                            if (item in MaterialItems)
                                source.sendFeedback(StringTextComponent((MaterialItems.getItemObjType(item)!!).itemTag.id.toString()), false)
                            else
                                source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                        }
                    }
                }
            }
        }
    }

    private fun String.normalFormat() = split('_').map(String::capitalize).joinToString(separator = " ", transform = { it })
}