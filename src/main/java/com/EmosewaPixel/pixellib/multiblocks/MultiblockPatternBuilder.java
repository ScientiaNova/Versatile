package com.EmosewaPixel.pixellib.multiblocks;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MultiblockPatternBuilder {
    private List<List<String>> pattern = new ArrayList<>();
    private Map<Character, Predicate<BlockState>> blockMap = new HashMap<>();
    private List<List<List<Predicate<BlockState>>>> finalPattern = new ArrayList<>();
    private Map<BlockPos, Predicate<BlockState>> patternMap = new HashMap<>();

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
        blockMap.put(c, p);
        return this;
    }

    public MultiblockPatternBuilder build() {
        finalPattern = pattern.stream().map(strings -> strings.stream().map(s -> s.chars().mapToObj(c -> (char) c).map(blockMap::get).collect(Collectors.toList())).collect(Collectors.toList())).collect(Collectors.toList());

        AtomicInteger index = new AtomicInteger(0);
        patternMap = finalPattern.stream().map(l1 -> l1.stream().map(l2 -> l2.stream().collect(Collectors.toMap(l2::indexOf, Function.identity())).entrySet()).flatMap(Set::stream).collect(Tables.toTable(l1::indexOf, Map.Entry::getKey, Map.Entry::getValue, HashBasedTable::create)).cellSet()).flatMap(Set::stream).collect(Collectors.toMap(c -> new BlockPos(index.getAndIncrement(), c.getRowKey(), c.getColumnKey()), Table.Cell::getValue));

        return this;
    }

    public List<List<List<Predicate<BlockState>>>> getPattern() {
        return finalPattern;
    }

    public Map<BlockPos, Predicate<BlockState>> getPatternMap() {
        return patternMap;
    }
}