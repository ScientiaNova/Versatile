package com.emosewapixel.pixellib.materialsystem.main.ct;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.emosewapixel.pixellib.materialsystem.main.Material;

import java.util.function.Predicate;

@ZenRegister
@FunctionalInterface
public interface MaterialRequirement extends Predicate<Material> {
}