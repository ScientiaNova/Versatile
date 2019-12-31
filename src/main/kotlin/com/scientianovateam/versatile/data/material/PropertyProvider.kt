package com.scientianovateam.versatile.data.material

import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider

abstract class PropertyProvider : IDataProvider {
    val properties = mutableMapOf<String, IUnresolved>()

    override fun act(cache: DirectoryCache) {
        TODO("not implemented")
    }

    override fun getName(): String {
        TODO("not implemented")
    }
}