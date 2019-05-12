package com.EmosewaPixel.pixellib.multiblocks;

import net.minecraft.block.state.IBlockState;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MultiblockPatternBuilder {
    private List<List<String>> pattern = new ArrayList<>();
    private Map<Character, Predicate<IBlockState>> block_map = new HashMap<>();
    private List<List<List<Predicate<IBlockState>>>> final_pattern = new ArrayList<>();

    public static MultiblockPatternBuilder create() {
        return new MultiblockPatternBuilder();
    }

    public MultiblockPatternBuilder addLayer(String... layer) {
        pattern.add(Arrays.asList(layer));
        return this;
    }

    public MultiblockPatternBuilder repeat(int amount) {
        for (int i = 0; i < amount; i++)
            pattern.add(pattern.get(pattern.size() - 1));
        return this;
    }

    public MultiblockPatternBuilder where(Character c, Predicate<IBlockState> p) {
        block_map.put(c, p);
        return this;
    }

    public MultiblockPatternBuilder build() {
        final_pattern = pattern.stream().map(strings -> strings.stream().map(s -> s.chars().mapToObj(c -> (char) c).map(block_map::get).collect(Collectors.toList())).collect(Collectors.toList())).collect(Collectors.toList());
        return this;
    }

    public List<List<List<Predicate<IBlockState>>>> getPattern() {
        return final_pattern;
    }
}