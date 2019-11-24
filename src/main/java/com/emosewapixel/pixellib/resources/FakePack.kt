package com.emosewapixel.pixellib.resources

import com.emosewapixel.pixellib.extensions.JsonBuilder
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
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

//This is the fake data/resource pack used for adding data and asset JSONs
internal class FakePack constructor(private val name: String) : IResourcePack {
    private val root = TreeMap<String, InputStream>()
    private val assets = TreeMap<ResourceLocation, InputStream>()
    private val data = TreeMap<ResourceLocation, InputStream>()

    init {
        putJSON(ResourcePackType.CLIENT_RESOURCES, ResourceLocation("pack.mcmeta"), JsonParser().parse("{\"pack_format\":4,\"description\":\"PixelLib's injected resources\"}"))
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
            putString(type, location, GSON.toJson(com.emosewapixel.pixellib.extensions.json(json)))

    @Throws(IOException::class)
    override fun getRootResourceStream(fileName: String): InputStream {
        require(!("/" in fileName || "\\" in fileName)) { "Root resources can only be filenames, not paths (no / allowed!)" }
        return root[fileName] ?: throw FileNotFoundException(fileName)
    }

    @Throws(IOException::class)
    override fun getResourceStream(type: ResourcePackType, location: ResourceLocation) =
            (if (type == ResourcePackType.CLIENT_RESOURCES) assets else data)[location]
                    ?: throw FileNotFoundException(location.toString())

    override fun getAllResourceLocations(type: ResourcePackType, pathIn: String, maxDepth: Int, filter: Predicate<String>) =
            (if (type == ResourcePackType.CLIENT_RESOURCES) assets else data).keys.filter {
                val path = it.path
                val lastSlash = path.lastIndexOf('/')
                StringUtils.countMatches(path, '/') < maxDepth && path.startsWith(pathIn) && filter.test(path.substring(if (lastSlash < 0) 0 else lastSlash))
            }

    override fun resourceExists(type: ResourcePackType, location: ResourceLocation) =
            (if (type == ResourcePackType.CLIENT_RESOURCES) assets else data).containsKey(location)

    override fun getResourceNamespaces(type: ResourcePackType) = (if (type == ResourcePackType.CLIENT_RESOURCES) assets else data).keys.map { it.namespace }.toSet()

    override fun <T> getMetadata(deserializer: IMetadataSectionSerializer<T>): T? = ResourcePack.getResourceMetadata(deserializer, assets[ResourceLocation("pack.mcmeta")]!!)

    override fun getName() = name

    override fun close() {}

    override fun isHidden() = true

    companion object {
        private val GSON = GsonBuilder().create()
    }
}
