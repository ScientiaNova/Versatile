package com.emosewapixel.pixellib.machinetiers

//Machine Tiers are used for making a tiered machine system. This is a base class that you will probably want to extend if you want to make such a system
interface MachineTier {
    val name: String
    val color: Int
}