package com.EmosewaPixel.pixellib.resourceAddition;

import com.google.gson.*;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//This is the fake data/resource pack used for adding data and asset JSONs
public final class FakePack implements IResourcePack {
    private static final Gson GSON = new GsonBuilder().create();
    private final String name;
    private final TreeMap<String, InputStream> root = new TreeMap<>();
    private final TreeMap<ResourceLocation, InputStream> assets = new TreeMap<>();
    private final TreeMap<ResourceLocation, InputStream> data = new TreeMap<>();

    protected FakePack(String name) {
        this.name = name;
    }

    public FakePack putInputStream(ResourcePackType type, ResourceLocation location, InputStream stream) {
        switch (type) {
            case CLIENT_RESOURCES:
                assets.put(location, stream);
                break;
            case SERVER_DATA:
                data.put(location, stream);
                break;
        }
        return this;
    }

    public FakePack putBytes(ResourcePackType type, ResourceLocation location, byte[] bytes) {
        return putInputStream(type, location, new ByteArrayInputStream(bytes));
    }

    public FakePack putString(ResourcePackType type, ResourceLocation location, String string) {
        return putBytes(type, location, string.getBytes());
    }

    public FakePack putJSON(ResourcePackType type, ResourceLocation location, JsonElement json) {
        return putString(type, location, GSON.toJson(json));
    }

    @Override
    public InputStream getRootResourceStream(String fileName) throws IOException {
        if (fileName.contains("/") || fileName.contains("\\")) {
            throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
        }
        if (root.containsKey(fileName)) {
            return root.get(fileName);
        }
        throw new FileNotFoundException(fileName);
    }

    @Override
    public InputStream getResourceStream(ResourcePackType type, ResourceLocation location) throws IOException {
        Map<ResourceLocation, InputStream> map = type == ResourcePackType.CLIENT_RESOURCES ? assets : data;
        if (map.containsKey(location)) {
            return map.get(location);
        }
        throw new FileNotFoundException(location.toString());
    }

    @Override
    public Collection<ResourceLocation> getAllResourceLocations(ResourcePackType type, String pathIn, int maxDepth, Predicate<String> filter) {
        Map<ResourceLocation, InputStream> map = type == ResourcePackType.CLIENT_RESOURCES ? assets : data;
        return map.keySet().stream().filter(location -> StringUtils.countMatches(location.getPath(), '/') < maxDepth).
                filter(location -> location.getPath().startsWith(pathIn)).filter(location -> {
            String path = location.getPath();
            int lastSlash = path.lastIndexOf('/');
            return filter.test(path.substring(lastSlash < 0 ? 0 : lastSlash, path.length()));
        }).collect(Collectors.toList());
    }

    @Override
    public boolean resourceExists(ResourcePackType type, ResourceLocation location) {
        Map<ResourceLocation, InputStream> map = type == ResourcePackType.CLIENT_RESOURCES ? assets : data;
        return map.containsKey(location);
    }

    @Override
    public Set<String> getResourceNamespaces(ResourcePackType type) {
        Map<ResourceLocation, InputStream> map = type == ResourcePackType.CLIENT_RESOURCES ? assets : data;
        return map.keySet().stream().map(location->location.getNamespace()).collect(Collectors.toSet());
    }

    @Nullable
    @Override
    public <T> T getMetadata(IMetadataSectionSerializer<T> deserializer) throws IOException {
        return deserializer.deserialize((JsonObject) new JsonParser().parse("{\"pack_format\":4,\"description\":\"Expert Mode Core's injected resources\"}"));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
