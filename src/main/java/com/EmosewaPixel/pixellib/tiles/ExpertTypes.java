package com.EmosewaPixel.pixellib.tiles;

import net.minecraft.tileentity.TileEntityType;

public class ExpertTypes {
    public static TileEntityType<TileEntityAlloyer> ALLOYER = TileEntityType.register("alloyer", TileEntityType.Builder.create(TileEntityAlloyer::new));
    public static TileEntityType<TileEntityBlastFurnace> BLAST_FURNACE = TileEntityType.register("blast_furnace", TileEntityType.Builder.create(TileEntityBlastFurnace::new));
    public static TileEntityType<TileEntityCokeOven> COKE_OVEN = TileEntityType.register("coke_oven", TileEntityType.Builder.create(TileEntityCokeOven::new));
    public static TileEntityType<TileEntityCrusher> CRUSHER = TileEntityType.register("crusher", TileEntityType.Builder.create(TileEntityCrusher::new));
    public static TileEntityType<TileEntityInfusionTable> INFUSION_TABLE = TileEntityType.register("infusion_table", TileEntityType.Builder.create(TileEntityInfusionTable::new));
    public static TileEntityType<TileEntitySawmill> SAWMILL = TileEntityType.register("sawmill", TileEntityType.Builder.create(TileEntitySawmill::new));
}