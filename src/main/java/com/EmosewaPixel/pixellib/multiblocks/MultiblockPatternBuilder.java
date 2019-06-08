package com.EmosewaPixel.pixellib.multiblocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MultiblockPatternBuilder {
    private List<List<String>> pattern = new ArrayList<>();
    private Map<Character, Predicate<BlockState>> block_map = new HashMap<>();
    private List<List<List<Predicate<BlockState>>>> final_pattern = new ArrayList<>();
    private Map<BlockPos, Predicate<BlockState>> pattern_map = new HashMap<>();

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

    public MultiblockPatternBuilder where(Character c, Predicate<BlockState> p) {
        block_map.put(c, p);
        return this;
    }

    public MultiblockPatternBuilder build() {
        final_pattern = pattern.stream().map(strings -> strings.stream().map(s -> s.chars().mapToObj(c -> (char) c).map(block_map::get).collect(Collectors.toList())).collect(Collectors.toList())).collect(Collectors.toList());
        for (int i = 0; i < final_pattern.size(); i++)
            for (int j = 0; j < final_pattern.get(i).size(); j++)
                for (int k = 0; k < final_pattern.get(i).get(j).size(); k++)
                    pattern_map.put(new BlockPos(i, j, k), final_pattern.get(i).get(j).get(k));

        return this;
    }

    public List<List<List<Predicate<BlockState>>>> getPattern() {
        return final_pattern;
    }

    public Map<BlockPos, Predicate<BlockState>> getPatternMap() {
        return pattern_map;
    }
}