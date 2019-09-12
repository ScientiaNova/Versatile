package com.emosewapixel.pixellib.commands

import com.emosewapixel.pixellib.extensions.argument
import com.emosewapixel.pixellib.extensions.does
import com.emosewapixel.pixellib.extensions.literal
import com.emosewapixel.pixellib.extensions.register
import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.block.Block
import net.minecraft.command.CommandSource
import net.minecraft.command.arguments.ItemArgument
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TranslationTextComponent

class MaterialItemCommand(dispatcher: CommandDispatcher<CommandSource>) {
    init {
        dispatcher.register("materialitem") {
            requires {
                it.hasPermissionLevel(1)
            }
            literal("material") {
                requires {
                    it.entity is ServerPlayerEntity
                }
                literal("name") {
                    does {
                        val item = source.asPlayer().heldItemMainhand.item
                        when {
                            item in MaterialItems -> source.sendFeedback(MaterialItems.getItemMaterial(item)!!.translationKey, false)
                            item is BlockItem && Block.getBlockFromItem(item) in MaterialBlocks -> source.sendFeedback(MaterialBlocks.getBlockMaterial(Block.getBlockFromItem(item))!!.translationKey, false)
                            else -> source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                        }
                    }
                }
                literal("hierarchy") {
                    does {
                        val item = source.asPlayer().heldItemMainhand.item

                        fun Material.sendHierarchy(level: Int = 0) {
                            source.sendFeedback(StringTextComponent((if (level > 0) " ".repeat(level - 1) + "-" else "") + translationKey.formattedText), false)
                            if (composition.isNotEmpty())
                                composition.forEach { it.material.sendHierarchy(level + 1) }
                        }

                        when {
                            item in MaterialItems -> MaterialItems.getItemMaterial(item)!!.sendHierarchy()
                            item is BlockItem && Block.getBlockFromItem(item) in MaterialBlocks -> MaterialBlocks.getBlockMaterial(Block.getBlockFromItem(item))!!.sendHierarchy()
                            else -> source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                        }
                    }
                }
            }
            literal("type") {
                requires {
                    it.entity is ServerPlayerEntity
                }
                literal("name") {
                    does {
                        val item = source.asPlayer().heldItemMainhand.item
                        when {
                            item in MaterialItems -> source.sendFeedback(StringTextComponent((MaterialItems.getItemObjType(item)!!).name.normalFormat()), false)
                            item is BlockItem && Block.getBlockFromItem(item) in MaterialBlocks -> source.sendFeedback(StringTextComponent(MaterialBlocks.getBlockObjType(Block.getBlockFromItem(item))!!.name.normalFormat()), false)
                            else -> source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                        }
                    }
                }
                literal("tag") {
                    does {
                        val item = source.asPlayer().heldItemMainhand.item
                        when {
                            item in MaterialItems -> source.sendFeedback(StringTextComponent((MaterialItems.getItemObjType(item)!!).itemTag.id.toString()), false)
                            item is BlockItem && Block.getBlockFromItem(item) in MaterialBlocks -> source.sendFeedback(StringTextComponent(MaterialBlocks.getBlockObjType(Block.getBlockFromItem(item))!!.itemTag.id.toString()), false)
                            else -> source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                        }
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
                            when {
                                item in MaterialItems -> source.sendFeedback(MaterialItems.getItemMaterial(item)!!.translationKey, false)
                                item is BlockItem && Block.getBlockFromItem(item) in MaterialBlocks -> source.sendFeedback(MaterialBlocks.getBlockMaterial(Block.getBlockFromItem(item))!!.translationKey, false)
                                else -> source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                            }
                        }
                    }
                    literal("hierarchy") {
                        does {
                            val item = ItemArgument.getItem(this, "item").item

                            fun Material.sendHierarchy(level: Int = 0) {
                                source.sendFeedback(StringTextComponent((if (level > 0) " ".repeat(level - 1) + "-" else "") + translationKey.formattedText), false)
                                if (composition.isNotEmpty())
                                    composition.forEach { it.material.sendHierarchy(level + 1) }
                            }

                            when {
                                item in MaterialItems -> MaterialItems.getItemMaterial(item)!!.sendHierarchy()
                                item is BlockItem && Block.getBlockFromItem(item) in MaterialBlocks -> MaterialBlocks.getBlockMaterial(Block.getBlockFromItem(item))!!.sendHierarchy()
                                else -> source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                            }
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
                            when {
                                item in MaterialItems -> source.sendFeedback(StringTextComponent((MaterialItems.getItemObjType(item)!!).name.normalFormat()), false)
                                item is BlockItem && Block.getBlockFromItem(item) in MaterialBlocks -> source.sendFeedback(StringTextComponent(MaterialBlocks.getBlockObjType(Block.getBlockFromItem(item))!!.name.normalFormat()), false)
                                else -> source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                            }
                        }
                    }
                    literal("tag") {
                        does {
                            val item = ItemArgument.getItem(this, "item").item
                            when {
                                item in MaterialItems -> source.sendFeedback(StringTextComponent((MaterialItems.getItemObjType(item)!!).itemTag.id.toString()), false)
                                item is BlockItem && Block.getBlockFromItem(item) in MaterialBlocks -> source.sendFeedback(StringTextComponent(MaterialBlocks.getBlockObjType(Block.getBlockFromItem(item))!!.itemTag.id.toString()), false)
                                else -> source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun String.normalFormat() = split('_').map(String::capitalize).joinToString(separator = " ", transform = { it })
}