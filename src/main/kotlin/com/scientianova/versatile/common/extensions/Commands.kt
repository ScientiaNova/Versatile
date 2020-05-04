package com.scientianova.versatile.common.extensions

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands

fun CommandDispatcher<CommandSource>.register(name: String, builder: LiteralArgumentBuilder<CommandSource>.() -> Unit) {
    val literal = Commands.literal(name)
    builder.invoke(literal)
    register(literal)
}

fun ArgumentBuilder<CommandSource, *>.literal(name: String, builder: LiteralArgumentBuilder<CommandSource>.() -> Unit) {
    val literal = Commands.literal(name)
    builder.invoke(literal)
    then(literal)
}

fun <T> ArgumentBuilder<CommandSource, *>.argument(name: String, type: ArgumentType<T>, builder: ArgumentBuilder<CommandSource, *>.() -> Unit) {
    val argument = Commands.argument(name, type)
    builder.invoke(argument)
    then(argument)
}

fun ArgumentBuilder<CommandSource, *>.does(command: CommandContext<CommandSource>.() -> Unit) {
    executes {
        command.invoke(it)
        0
    }
}