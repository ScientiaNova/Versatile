package com.emosewapixel.pixellib.extensions

import net.minecraft.client.renderer.Vector3f
import net.minecraft.client.renderer.Vector4f
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3i
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

infix fun Vec3i.x(other: Vec3i): Vec3i = crossProduct(other)
operator fun Vec3i.plus(other: Vec3i) = Vec3i(x + other.x, y + other.y, z + other.z)
operator fun Vec3i.minus(other: Vec3i) = Vec3i(x - other.x, y - other.y, z - other.z)
operator fun Vec3i.times(other: Vec3i) = Vec3i(x * other.x, y * other.y, z * other.z)
operator fun Vec3i.times(m: Int) = Vec3i(x * m, y * m, z * m)
operator fun Vec3i.div(other: Vec3i): Vec3i = Vec3i(x / other.x, y / other.y, z / other.z)
operator fun Vec3i.unaryMinus(): Vec3i = times(-1)
operator fun Vec3i.component0() = x
operator fun Vec3i.component1() = y
operator fun Vec3i.component2() = z
fun Vec3i.toVec3d() = Vec3d(this)


@OnlyIn(Dist.CLIENT)
infix fun Vector3f.x(other: Vector3f) = cross(other)

@OnlyIn(Dist.CLIENT)
operator fun Vector3f.plus(other: Vector3f) = Vector3f(x + other.x, y + other.y, z + other.z)

@OnlyIn(Dist.CLIENT)
operator fun Vector3f.minus(other: Vector3f) = Vector3f(x - other.x, y - other.y, z - other.z)

@OnlyIn(Dist.CLIENT)
operator fun Vector3f.times(other: Vector3f) = Vector3f(x * other.x, y * other.y, z * other.z)

@OnlyIn(Dist.CLIENT)
operator fun Vector3f.times(m: Float) = Vector3f(x * m, y * m, z * m)

@OnlyIn(Dist.CLIENT)
operator fun Vector3f.div(other: Vector3f) = Vector3f(x / other.x, y / other.y, z / other.z)

@OnlyIn(Dist.CLIENT)
operator fun Vector3f.unaryMinus() = Vector3f(-x, -y, -z)

@OnlyIn(Dist.CLIENT)
operator fun Vector3f.component0() = x

@OnlyIn(Dist.CLIENT)
operator fun Vector3f.component1() = y

@OnlyIn(Dist.CLIENT)
operator fun Vector3f.component2() = z


@OnlyIn(Dist.CLIENT)
operator fun Vector4f.component0() = x

@OnlyIn(Dist.CLIENT)
operator fun Vector4f.component1() = y

@OnlyIn(Dist.CLIENT)
operator fun Vector4f.component2() = z

@OnlyIn(Dist.CLIENT)
operator fun Vector4f.component3() = w


infix fun Vec3d.x(other: Vec3d): Vec3d = crossProduct(other)
operator fun Vec3d.plus(other: Vec3d): Vec3d = add(other)
operator fun Vec3d.minus(other: Vec3d): Vec3d = subtract(other)
operator fun Vec3d.times(other: Vec3d): Vec3d = mul(other)
operator fun Vec3d.times(m: Double): Vec3d = scale(m)
operator fun Vec3d.div(other: Vec3d): Vec3d = Vec3d(x / other.x, y / other.y, z / other.z)
operator fun Vec3d.unaryMinus(): Vec3d = func_216371_e()
operator fun Vec3d.component0() = x
operator fun Vec3d.component1() = y
operator fun Vec3d.component2() = z
fun Vec3d.toVec3i() = Vec3i(x, y, z)