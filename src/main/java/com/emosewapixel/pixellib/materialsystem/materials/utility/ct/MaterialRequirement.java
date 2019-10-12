package com.emosewapixel.pixellib.materialsystem.materials.utility.ct;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.emosewapixel.pixellib.materialsystem.materials.Material;

import java.util.function.Predicate;

@ZenRegister
@FunctionalInterface
public interface MaterialRequirement extends Predicate<Material> {
}