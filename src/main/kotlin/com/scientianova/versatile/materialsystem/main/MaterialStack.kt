package com.scientianova.versatile.materialsystem.main

data class MaterialStack(var material: Material, var count: Int = 1) {
    val isEmpty get() = count == 0
}