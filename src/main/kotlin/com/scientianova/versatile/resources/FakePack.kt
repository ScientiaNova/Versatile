package com.scientianova.versatile.resources

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.scientianova.versatile.common.extensions.JsonBuilder
import net.minecraft.resources.IResourcePack
import net.minecraft.resources.ResourcePack
import net.minecraft.resources.ResourcePackType
import net.minecraft.resources.data.IMetadataSectionSerializer
import net.minecraft.util.ResourceLocation
import org.apache.commons.lang3.StringUtils
import java.io.ByteArrayInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.util.*
import java.util.function.Predicate

internal class FakePack constructor(private val name: String) : IResourcePack {
    private val root = TreeMap<String, InputStream>()
    private val assets = TreeMap<ResourceLocation, InputStream>()
    private val data = TreeMap<ResourceLocation, InputStream>()

    init {
        putJSON(ResourcePackType.CLIENT_RESOURCES, ResourceLocation("pack.mcmeta"), JsonParser().parse("{\"pack_format\":4,\"description\":\"Versatile's injected resources\"}"))
    }

    fun putInputStream(type: ResourcePackType, location: ResourceLocation, stream: InputStream): FakePack {
        when (type) {
            ResourcePackType.CLIENT_RESOURCES -> assets[location] = stream
            ResourcePackType.SERVER_DATA -> data[location] = stream
        }
        return this
    }

    fun putBytes(type: ResourcePackType, location: ResourceLocation, bytes: ByteArray) = putInputStream(type, location, ByteArrayInputStream(bytes))

    fun putString(type: ResourcePackType, location: ResourceLocation, string: String) = putBytes(type, location, string.toByteArray())

    fun putJSON(type: ResourcePackType, location: ResourceLocation, json: JsonElement) = putString(type, location, GSON.toJson(json))

    fun putJSON(type: ResourcePackType, location: ResourceLocation, json: JsonBuilder.() -> Unit) =
            putString(type, location, GSON.toJson(com.scientianova.versatile.common.extensions.json(json)))

    @Throws(IOException::class)
    override fun getRootResourceStream(fileName: String): InputStream {
        require(!("/" in fileName || "\\" in fileName)) { "Root resources can only be filenames, not paths (no / allowed!)" }
        return root[fileName] ?: throw FileNotFoundException(fileName)
    }

    @Throws(IOException::class)
    override fun getResourceStream(type: ResourcePackType, location: ResourceLocation) =
            (if (type == ResourcePackType.CLIENT_RESOURCES) assets else data)[location]
                    ?: throw FileNotFoundException(location.toString())

    override fun getAllResourceLocations(type: ResourcePackType, namespaceIn: String, pathIn: String, maxDepth: Int, filter: Predicate<String>) =
            (if (type == ResourcePackType.CLIENT_RESOURCES) assets else data).keys.filter {
                val path = it.path
                val lastSlash = path.lastIndexOf('/')
                StringUtils.countMatches(path, '/') < maxDepth
                        && it.namespace == namespaceIn
                        && path.startsWith(pathIn)
                        && StringUtils.countMatches(path, '/') < maxDepth
                        && filter.test(path.substring(if (lastSlash < 0) 0 else lastSlash))
            }

    override fun resourceExists(type: ResourcePackType, location: ResourceLocation) =
            location in if (type == ResourcePackType.CLIENT_RESOURCES) assets else data

    override fun getResourceNamespaces(type: ResourcePackType) =
            (if (type == ResourcePackType.CLIENT_RESOURCES) assets else data).keys.map { it.namespace }.toSet()

    override fun <T> getMetadata(deserializer: IMetadataSectionSerializer<T>): T? =
            ResourcePack.getResourceMetadata(deserializer, assets[ResourceLocation("pack.mcmeta")]!!)

    override fun getName() = name

    override fun close() {}

    override fun isHidden() = true
}

private val GSON = GsonBuilder().create()