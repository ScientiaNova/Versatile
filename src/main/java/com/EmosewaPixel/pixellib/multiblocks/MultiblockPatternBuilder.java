package com.EmosewaPixel.pixellib.multiblocks;

import com.EmosewaPixel.pixellib.miscutils.StreamUtils;
import com.google.common.collect.Streams;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//The Multiblock Pattern Builder is simply a Builder for creating multiblock patterns
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
        StreamUtils.repeat(amount, i -> pattern.add(pattern.get(pattern.size() - 1)));
        return this;
    }

    public MultiblockPatternBuilder where(Character c, Predicate<BlockState> p) {
        blockMap.put(c, p);
        return this;
    }

    public MultiblockPatternBuilder build() {
        finalPattern = pattern.stream().map(strings -> strings.stream().map(s -> s.chars().mapToObj(c -> (char) c).map(blockMap::get).collect(Collectors.toList())).collect(Collectors.toList())).collect(Collectors.toList());

        AtomicInteger index = new AtomicInteger(0);
        patternMap = finalPattern.stream()
                .flatMap(l1 -> Streams.mapWithIndex(l1.stream()
                                .flatMap(l2 -> Streams.mapWithIndex(l2.stream(), (pred, i) -> Pair.of(i, pred))),
                        (p, i) -> Triple.of(i, p.getKey(), p.getValue())))
                .collect(Collectors.toMap(t -> new BlockPos(index.getAndIncrement(), t.getLeft(), t.getMiddle()), Triple::getRight));
        return this;
    }

    public List<List<List<Predicate<BlockState>>>> getPattern() {
        return finalPattern;
    }

    public Map<BlockPos, Predicate<BlockState>> getPatternMap() {
        return patternMap;
    }
}