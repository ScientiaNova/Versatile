package com.emosewapixel.pixellib.extensions

import net.minecraft.client.renderer.Vector3f
import net.minecraft.client.renderer.Vector4f
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3i

infix fun Vec3i.x(other: Vec3i): Vec3i = crossProduct(other)
operator fun Vec3i.plus(other: Vec3i) = Vec3i(x + other.x, y + other.y, z + other.z)
operator fun Vec3i.minus(other: Vec3i) = Vec3i(x - other.x, y - other.y, z - other.z)
operator fun Vec3i.times(other: Vec3i) = Vec3i(x * other.x, y * other.y, z * other.z)
operator fun Vec3i.times(m: Int) = Vec3i(x * m, y * m, z * m)
operator fun Vec3i.div(other: Vec3i): Vec3i = Vec3i(x / other.x, y / other.y, z / other.z)
operator fun Vec3i.unaryMinus(): Vec3i = times(-1)
operator fun Vec3i.component1() = x
operator fun Vec3i.component2() = y
operator fun Vec3i.component3() = z
fun Vec3i.toVec3d() = Vec3d(this)


infix fun Vector3f.x(other: Vector3f) = cross(other)
operator fun Vector3f.plus(other: Vector3f) = Vector3f(x + other.x, y + other.y, z + other.z)
operator fun Vector3f.minus(other: Vector3f) = Vector3f(x - other.x, y - other.y, z - other.z)
operator fun Vector3f.times(other: Vector3f) = Vector3f(x * other.x, y * other.y, z * other.z)
operator fun Vector3f.times(m: Float) = Vector3f(x * m, y * m, z * m)
operator fun Vector3f.div(other: Vector3f) = Vector3f(x / other.x, y / other.y, z / other.z)
operator fun Vector3f.unaryMinus() = Vector3f(-x, -y, -z)
operator fun Vector3f.component1() = x
operator fun Vector3f.component2() = y
operator fun Vector3f.component3() = z


operator fun Vector4f.component1() = x
operator fun Vector4f.component2() = y
operator fun Vector4f.component3() = z
operator fun Vector4f.component4() = w


infix fun Vec3d.x(other: Vec3d): Vec3d = crossProduct(other)
operator fun Vec3d.plus(other: Vec3d): Vec3d = add(other)
operator fun Vec3d.minus(other: Vec3d): Vec3d = subtract(other)
operator fun Vec3d.times(other: Vec3d): Vec3d = mul(other)
operator fun Vec3d.times(m: Double): Vec3d = scale(m)
operator fun Vec3d.div(other: Vec3d): Vec3d = Vec3d(x / other.x, y / other.y, z / other.z)
operator fun Vec3d.unaryMinus(): Vec3d = func_216371_e()
operator fun Vec3d.component1() = x
operator fun Vec3d.component2() = y
operator fun Vec3d.component3() = z
fun Vec3d.toVec3i() = Vec3i(x, y, z)