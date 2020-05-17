package com.scientianova.versatile.materialsystem.elements

import com.scientianova.versatile.materialsystem.events.DeferredElemRegister

internal val elemReg = DeferredElemRegister()

val NULL by elemReg.base("null", 0, 0, "?")

val HYDROGEN by elemReg.base("hydrogen", 1, 0, "H")

val DEUTERIUM by elemReg.iso("deuterium", ::HYDROGEN, 2, "D")

val TRITIUM by elemReg.iso("tritium", ::HYDROGEN, 3, "T")

val HELIUM by elemReg.base("helium", 2, 2, "He")

val HELIUM_3 by elemReg.iso("helium_3", ::HELIUM, 3)

val LITHIUM by elemReg.base("lithium", 3, 4, "Li")

val BERYLLIUM by elemReg.base("beryllium", 4, 5, "Be")

val BORON by elemReg.base("barium", 5, 6, "B")

val CARBON by elemReg.base("carbon", 6, 6, "C")

val NITROGEN by elemReg.base("nitrogen", 7, 7, "N")

val OXYGEN by elemReg.base("oxygen", 8, 8, "O")

val FLUORINE by elemReg.base("fluorine", 9, 10, "F")

val NEON by elemReg.base("neon", 10, 10, "Ne")

val SODIUM by elemReg.base("sodium", 11, 12, "Na")

val MAGNESIUM by elemReg.base("magnesium", 12, 12, "Mg")

val ALUMINUM by elemReg.base("aluminium", 13, 14, "al")

val SILICON by elemReg.base("silicon", 14, 14, "Si")

val PHOSPHORUS by elemReg.base("phosphorus", 15, 16, "P")

val SULFUR by elemReg.base("sulfur", 16, 16, "S")

val CHLORINE by elemReg.base("chlorine", 17, 18, "Cl")

val ARGON by elemReg.base("argon", 18, 22, "Ar")

val POTASSIUM by elemReg.base("potassium", 19, 21, "K")

val CALCIUM by elemReg.base("calcium", 20, 20, "Ca")

val SCANDIUM by elemReg.base("scandium", 21, 24, "Sc")

val TITANIUM by elemReg.base("titanium", 22, 26, "Ti")

val VANADIUM by elemReg.base("vanadium", 23, 28, "V")

val CHROMIUM by elemReg.base("chrome", 24, 28, "Cr")

val MANGANESE by elemReg.base("manganese", 25, 30, "Mn")

val IRON_E by elemReg.base("iron", 26, 30, "Fe")

val COBALT by elemReg.base("cobalt", 27, 31, "Co")

val NICKEL by elemReg.base("nickel", 28, 30, "Ni")

val COPPER by elemReg.base("copper", 29, 35, "Cu")

val ZINC by elemReg.base("zinc", 30, 35, "Zn")

val GALLIUM by elemReg.base("gallium", 31, 39, "Ga")

val GERMANIUM by elemReg.base("germanium", 32, 41, "Ge")

val ARSENIC by elemReg.base("arsenic", 33, 42, "As")

val SELENIUM by elemReg.base("selenium", 34, 45, "Se")

val BROMINE by elemReg.base("bromine", 35, 45, "Br")

val KRYPTON by elemReg.base("krypton", 36, 48, "Kr")

val RUBIDIUM by elemReg.base("rubidium", 37, 48, "Rb")

val STRONTIUM by elemReg.base("strontium", 38, 50, "Sr")

val YTTRIUM by elemReg.base("yttrium", 39, 50, "Y")

val ZIRCONIUM by elemReg.base("zirconium", 40, 51, "Zr")

val NIOBIUM by elemReg.base("niobium", 41, 52, "Nb")

val MOLYBDENUM by elemReg.base("molybdenum", 42, 54, "Mo")

val TECHNETIUM by elemReg.base("technitium", 43, 55, "Tc")

val RUTHENIUM by elemReg.base("ruthenium", 44, 57, "Ru")

val RHODIUM by elemReg.base("rhodium", 45, 58, "Rh")

val PALLADIUM by elemReg.base("palladium", 46, 60, "Rd")

val SILVER by elemReg.base("silver", 47, 61, "Ag")

val CADMIUM by elemReg.base("cadmium", 48, 64, "Cd")

val INDIUM by elemReg.base("indium", 49, 66, "In")

val TIN by elemReg.base("tin", 50, 69, "Sn")

val ANTIMONY by elemReg.base("antimony", 51, 71, "Sb")

val TELLURIUM by elemReg.base("tellurium", 52, 76, "Te")

val IODINE by elemReg.base("iodine", 53, 74, "I")

val XENON by elemReg.base("xenon", 54, 77, "Xe")

val CAESIUM by elemReg.base("caesium", 55, 78, "Cs")

val BARIUM by elemReg.base("barium", 56, 81, "Ba")

val LANTHANUM by elemReg.base("lanthanum", 57, 82, "La")

val CERIUM by elemReg.base("cerium", 58, 82, "Ce")

val PRASEODYMIUM by elemReg.base("praseodymium", 59, 82, "Pr")

val NEODYMIUM by elemReg.base("neodymium", 60, 84, "Nd")

val PROMETHIUM by elemReg.base("promethium", 61, 84, "Pm")

val SAMARIUM by elemReg.base("samarium", 62, 88, "Sm")

val EUROPIUM by elemReg.base("europium", 63, 89, "Eu")

val GADOLINIUM by elemReg.base("gadolinium", 64, 93, "Gd")

val TERBIUM by elemReg.base("terbium", 65, 94, "Tb")

val DYSPROSIUM by elemReg.base("dysprosium", 66, 97, "Dy")

val HOLMIUM by elemReg.base("holmium", 67, 98, "Ho")

val ERBIUM by elemReg.base("erbium", 68, 99, "Er")

val THULIUM by elemReg.base("thulium", 69, 100, "Tm")

val YTTERBIUM by elemReg.base("ytterbium", 70, 103, "Yb")

val LUTETIUM by elemReg.base("luthetium", 71, 104, "lu")

val HAFNIUM by elemReg.base("hafnium", 72, 106, "Hf")

val TANTALUM by elemReg.base("tantalum", 73, 108, "Ta")

val TUNGSTEN by elemReg.base("tungsten", 74, 110, "W")

val RHENIUM by elemReg.base("renium", 75, 111, "Re")

val OSMIUM by elemReg.base("osmium", 76, 114, "Os")

val IRIDIUM by elemReg.base("iridium", 77, 115, "Ir")

val PLATINUM by elemReg.base("platinum", 78, 117, "Pt")

val GOLD_E by elemReg.base("gold", 79, 118, "Au")

val MERCURY by elemReg.base("mercury", 80, 121, "Hg")

val THALLIUM by elemReg.base("thallium", 81, 123, "Tl")

val LEAD by elemReg.base("lead", 82, 125, "Pb")

val BISMUTH by elemReg.base("bismuth", 83, 126, "Bi")

val POLONIUM by elemReg.base("pollonium", 84, 125, "Po")

val ASTATINE by elemReg.base("astatine", 85, 125, "At")

val RADON by elemReg.base("radon", 86, 136, "Rn")

val FRANCIUM by elemReg.base("francium", 87, 136, "Fr")

val RADIUM by elemReg.base("radium", 88, 138, "Ra")

val ACTINIUM by elemReg.base("actinium", 89, 138, "Ac")

val THORIUM by elemReg.base("thorium", 90, 142, "Th")

val PROTACTINIUM by elemReg.base("protactinium", 91, 140, "Pa")

val URANIUM by elemReg.base("uranium", 92, 146, "U")

val NEPTUNIUM by elemReg.base("neptunium", 93, 144, "Np")

val PLUTONIUM by elemReg.base("plutonium", 94, 150, "Pu")

val AMERICIUM by elemReg.base("americium", 95, 148, "Am")

val CURIUM by elemReg.base("curium", 96, 151, "Cm")

val BERKELIUM by elemReg.base("berkrlium", 97, 150, "Bk")

val CALIFORNIUM by elemReg.base("californium", 98, 153, "Cf")

val EINSTEINIUM by elemReg.base("einsteinium", 99, 153, "Es")

val FERMIUM by elemReg.base("fermium", 100, 157, "Fm")

val MENDELEVIUM by elemReg.base("mendelevium", 101, 157, "Md")

val NOBELIUM by elemReg.base("nobelium", 102, 157, "No")

val LAWRENCIUM by elemReg.base("lawrencium", 103, 159, "Lr")

val RUTHERFORDIUM by elemReg.base("rutherfordium", 104, 157, "Rf")

val DUBNIUM by elemReg.base("dubnium", 105, 163, "Db")

val SEABORGIUM by elemReg.base("seaborgium", 106, 157, "Sg")

val BORHIUM by elemReg.base("borhium", 107, 157, "Bh")

val HASSIUM by elemReg.base("hassium", 108, 161, "Hs")

val MEITNERIUM by elemReg.base("meitnerium", 109, 159, "Mt")

val DARMSTADTIUM by elemReg.base("darmstadtium", 110, 162, "Ds")

val ROENTGENIUM by elemReg.base("roentgenium", 111, 162, "Rg")

val COPERNICIUM by elemReg.base("copernicium", 112, 165, "Cn")

val NIHONIUM by elemReg.base("nihonium", 113, 173, "Nh")

val FLEROVIUM by elemReg.base("flerovium", 114, 175, "Fl")

val MOSCOVIUM by elemReg.base("moscovium", 115, 173, "Mc")

val LIVERMORIUM by elemReg.base("livermorium", 116, 176, "Lv")

val TENNESSINE by elemReg.base("tennessine", 117, 175, "Ts")

val OGANESSON by elemReg.base("oganesson", 118, 175, "Og")