package com.emosewapixel.pixellib.commands

import com.emosewapixel.ktlib.extensions.does
import com.emosewapixel.ktlib.extensions.literal
import com.emosewapixel.ktlib.extensions.register
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.command.CommandSource
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.fluids.capability.CapabilityFluidHandler

class FluidContainerCommand(dispatcher: CommandDispatcher<CommandSource>) {
    init {
        dispatcher.register("fluidcontainer") {
            requires {
                it.hasPermissionLevel(1) && it.entity is ServerPlayerEntity
            }
            literal("fluid") {
                does {
                    val item = source.asPlayer().heldItemMainhand
                    if (item.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent)
                        item.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent { cap ->
                            (0 until cap.tanks).map { cap.getFluidInTank(it) to cap.getTankCapacity(it) }.forEach { (fluid, max) ->
                                source.sendFeedback(StringTextComponent("Fluid: " + fluid.fluid.attributes.getDisplayName(fluid).string), false)
                                source.sendFeedback(StringTextComponent("Amount: ${fluid.amount}/$max"), false)
                            }
                        }
                    else
                        source.sendErrorMessage(TranslationTextComponent("command.fluidcontainer.error"))
                }
                literal("properties") {
                    does {
                        val item = source.asPlayer().heldItemMainhand
                        if (item.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent)
                            item.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent { cap ->
                                (0 until cap.tanks).map { cap.getFluidInTank(it) }.forEach {
                                    val attributes = it.fluid.attributes
                                    source.sendFeedback(attributes.getDisplayName(it), false)
                                    source.sendFeedback(StringTextComponent("Color: ${attributes.color}"), false)
                                    source.sendFeedback(StringTextComponent("Density: ${attributes.density}"), false)
                                    source.sendFeedback(StringTextComponent("Is Gaseous: ${attributes.isGaseous}"), false)
                                    source.sendFeedback(StringTextComponent("Luminosity: ${attributes.luminosity}"), false)
                                    source.sendFeedback(StringTextComponent("Temperature: ${attributes.temperature}K"), false)
                                    source.sendFeedback(StringTextComponent("Viscosity: ${attributes.viscosity}"), false)
                                }
                            }
                        else
                            source.sendErrorMessage(TranslationTextComponent("command.fluidcontainer.error"))
                    }
                }
            }
        }
    }
}