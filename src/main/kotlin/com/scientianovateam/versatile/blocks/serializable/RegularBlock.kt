package com.scientianovateam.versatile.blocks.serializable

import com.google.gson.JsonObject
import com.scientianovateam.versatile.blocks.properties.ExtendedBlockProperties
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.EntitySpawnPlacementRegistry
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.Explosion
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorldReader

open class RegularBlock(val extendedProperties: ExtendedBlockProperties) : Block(extendedProperties), ISerializableBlock {
    init {
        registryName = extendedProperties.name.toResLoc()
    }

    override fun isAir(state: BlockState?, world: IBlockReader?, pos: BlockPos?) = extendedProperties.isAir
    override fun getTranslationKey(): String = extendedProperties.translationKey
    override val renderType = extendedProperties.renderType
    override fun getShape(state: BlockState, blockReader: IBlockReader, pos: BlockPos, context: ISelectionContext) = extendedProperties.shape
    override fun getCollisionShape(state: BlockState, blockReader: IBlockReader, pos: BlockPos, context: ISelectionContext) = extendedProperties.collisionShape
    override fun getRenderShape(state: BlockState, blockReader: IBlockReader, pos: BlockPos) = extendedProperties.renderShape
    override fun canDropFromExplosion(state: BlockState?, world: IBlockReader?, pos: BlockPos?, explosion: Explosion?) = extendedProperties.canDropFromExplosion
    override fun isLadder(state: BlockState?, world: IWorldReader?, pos: BlockPos?, entity: LivingEntity?) = extendedProperties.climbable
    override fun isBurning(state: BlockState?, world: IBlockReader?, pos: BlockPos?) = extendedProperties.burning
    override fun canCreatureSpawn(state: BlockState?, world: IBlockReader?, pos: BlockPos?, type: EntitySpawnPlacementRegistry.PlacementType?, entityType: EntityType<*>?) = extendedProperties.canMobsSpawn
    override fun isFoliage(state: BlockState?, world: IWorldReader?, pos: BlockPos?) = extendedProperties.foliage
    override fun isFertile(state: BlockState?, world: IBlockReader?, pos: BlockPos?) = extendedProperties.fertile
    override fun getExpDrop(state: BlockState?, world: IWorldReader?, pos: BlockPos?, fortune: Int, silktouch: Int) = extendedProperties.xpDrop
    override fun getEnchantPowerBonus(state: BlockState?, world: IWorldReader?, pos: BlockPos?) = extendedProperties.enchantingPowerBonus
    override fun isStickyBlock(state: BlockState?) = extendedProperties.sticky
    override fun getFlammability(state: BlockState?, world: IBlockReader?, pos: BlockPos?, face: Direction?) = extendedProperties.flammability
    override fun getFireSpreadSpeed(state: BlockState?, world: IBlockReader?, pos: BlockPos?, face: Direction?) = extendedProperties.fireSpreadSpeed
    override fun isFireSource(state: BlockState?, world: IBlockReader?, pos: BlockPos?, side: Direction?) = extendedProperties.fireSource
    override fun getPushReaction(state: BlockState) = extendedProperties.pushReaction
    private var localizationFunction: () -> ITextComponent = { TranslationTextComponent(translationKey) }
    override fun setLocalization(function: () -> ITextComponent) {
        localizationFunction = function
    }

    override fun getNameTextComponent() = localizationFunction()

    override val serializer = Serializer

    object Serializer : IRegisterableJSONSerializer<RegularBlock, JsonObject> {
        override val registryName = "regular".toResLocV()
        override fun read(json: JsonObject) = RegularBlock(ExtendedBlockProperties.Serializer.read(json))
        override fun write(obj: RegularBlock) = json {
            "type" to "regular"
            ExtendedBlockProperties.Serializer.write(obj.extendedProperties).extract()
        }
    }
}