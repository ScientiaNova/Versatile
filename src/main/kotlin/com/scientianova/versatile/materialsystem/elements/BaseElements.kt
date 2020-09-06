package com.scientianova.versatile.materialsystem.elements

import com.scientianova.versatile.materialsystem.events.DeferredElemRegister

internal val elemReg = DeferredElemRegister()

val nullElem by elemReg.base("null", 0, 0, "?")

val hydrogen by elemReg.base("hydrogen", 1, 0, "H")

val deuterium by elemReg.iso("deuterium", hydrogen, 2, "D")

val tritium by elemReg.iso("tritium", hydrogen, 3, "T")

val helium by elemReg.base("helium", 2, 2, "He")

val helium3 by elemReg.iso("helium_3", helium, 3)

val lithium by elemReg.base("lithium", 3, 4, "Li")

val beryllium by elemReg.base("beryllium", 4, 5, "Be")

val boron by elemReg.base("barium", 5, 6, "B")

val carbon by elemReg.base("carbon", 6, 6, "C")

val nitrogen by elemReg.base("nitrogen", 7, 7, "N")

val oxygen by elemReg.base("oxygen", 8, 8, "O")

val fluorine by elemReg.base("fluorine", 9, 10, "F")

val neon by elemReg.base("neon", 10, 10, "Ne")

val sodium by elemReg.base("sodium", 11, 12, "Na")

val magnesium by elemReg.base("magnesium", 12, 12, "Mg")

val aluminium by elemReg.base("aluminium", 13, 14, "al")

val silicon by elemReg.base("silicon", 14, 14, "Si")

val phosphorus by elemReg.base("phosphorus", 15, 16, "P")

val sulfur by elemReg.base("sulfur", 16, 16, "S")

val chlorine by elemReg.base("chlorine", 17, 18, "Cl")

val argon by elemReg.base("argon", 18, 22, "Ar")

val potassium by elemReg.base("potassium", 19, 21, "K")

val calcium by elemReg.base("calcium", 20, 20, "Ca")

val scandium by elemReg.base("scandium", 21, 24, "Sc")

val titanium by elemReg.base("titanium", 22, 26, "Ti")

val vanadium by elemReg.base("vanadium", 23, 28, "V")

val chromium by elemReg.base("chrome", 24, 28, "Cr")

val manganese by elemReg.base("manganese", 25, 30, "Mn")

val ironElem by elemReg.base("iron", 26, 30, "Fe")

val cobalt by elemReg.base("cobalt", 27, 31, "Co")

val nickel by elemReg.base("nickel", 28, 30, "Ni")

val copper by elemReg.base("copper", 29, 35, "Cu")

val zinc by elemReg.base("zinc", 30, 35, "Zn")

val gallium by elemReg.base("gallium", 31, 39, "Ga")

val germanium by elemReg.base("germanium", 32, 41, "Ge")

val arsenic by elemReg.base("arsenic", 33, 42, "As")

val selenium by elemReg.base("selenium", 34, 45, "Se")

val bromine by elemReg.base("bromine", 35, 45, "Br")

val krypton by elemReg.base("krypton", 36, 48, "Kr")

val rubidium by elemReg.base("rubidium", 37, 48, "Rb")

val strontium by elemReg.base("strontium", 38, 50, "Sr")

val yttrium by elemReg.base("yttrium", 39, 50, "Y")

val zirconium by elemReg.base("zirconium", 40, 51, "Zr")

val niobium by elemReg.base("niobium", 41, 52, "Nb")

val molybdenum by elemReg.base("molybdenum", 42, 54, "Mo")

val technetium by elemReg.base("technetium", 43, 55, "Tc")

val ruthenium by elemReg.base("ruthenium", 44, 57, "Ru")

val rhodium by elemReg.base("rhodium", 45, 58, "Rh")

val palladium by elemReg.base("palladium", 46, 60, "Rd")

val silver by elemReg.base("silver", 47, 61, "Ag")

val cadmium by elemReg.base("cadmium", 48, 64, "Cd")

val indium by elemReg.base("indium", 49, 66, "In")

val tin by elemReg.base("tin", 50, 69, "Sn")

val antimony by elemReg.base("antimony", 51, 71, "Sb")

val tellurium by elemReg.base("tellurium", 52, 76, "Te")

val iodine by elemReg.base("iodine", 53, 74, "I")

val xenon by elemReg.base("xenon", 54, 77, "Xe")

val caesium by elemReg.base("caesium", 55, 78, "Cs")

val barium by elemReg.base("barium", 56, 81, "Ba")

val lanthanum by elemReg.base("lanthanum", 57, 82, "La")

val cerium by elemReg.base("cerium", 58, 82, "Ce")

val praseodymium by elemReg.base("praseodymium", 59, 82, "Pr")

val neodymium by elemReg.base("neodymium", 60, 84, "Nd")

val promethium by elemReg.base("promethium", 61, 84, "Pm")

val samarium by elemReg.base("samarium", 62, 88, "Sm")

val europium by elemReg.base("europium", 63, 89, "Eu")

val gadolinium by elemReg.base("gadolinium", 64, 93, "Gd")

val terbium by elemReg.base("terbium", 65, 94, "Tb")

val dysprosium by elemReg.base("dysprosium", 66, 97, "Dy")

val holmium by elemReg.base("holmium", 67, 98, "Ho")

val erbium by elemReg.base("erbium", 68, 99, "Er")

val thulium by elemReg.base("thulium", 69, 100, "Tm")

val ytterbium by elemReg.base("ytterbium", 70, 103, "Yb")

val lutetium by elemReg.base("lutetium", 71, 104, "lu")

val hafnium by elemReg.base("hafnium", 72, 106, "Hf")

val tantalum by elemReg.base("tantalum", 73, 108, "Ta")

val tungsten by elemReg.base("tungsten", 74, 110, "W")

val rhenium by elemReg.base("rhenium", 75, 111, "Re")

val osmium by elemReg.base("osmium", 76, 114, "Os")

val iridium by elemReg.base("iridium", 77, 115, "Ir")

val platinum by elemReg.base("platinum", 78, 117, "Pt")

val goldElem by elemReg.base("gold", 79, 118, "Au")

val mercury by elemReg.base("mercury", 80, 121, "Hg")

val thallium by elemReg.base("thallium", 81, 123, "Tl")

val lead by elemReg.base("lead", 82, 125, "Pb")

val bismuth by elemReg.base("bismuth", 83, 126, "Bi")

val polonium by elemReg.base("polonium", 84, 125, "Po")

val astatine by elemReg.base("astatine", 85, 125, "At")

val radon by elemReg.base("radon", 86, 136, "Rn")

val francium by elemReg.base("francium", 87, 136, "Fr")

val radium by elemReg.base("radium", 88, 138, "Ra")

val actinium by elemReg.base("actinium", 89, 138, "Ac")

val thorium by elemReg.base("thorium", 90, 142, "Th")

val protactinium by elemReg.base("protactinium", 91, 140, "Pa")

val uranium by elemReg.base("uranium", 92, 146, "U")

val neptunium by elemReg.base("neptunium", 93, 144, "Np")

val plutonium by elemReg.base("plutonium", 94, 150, "Pu")

val americium by elemReg.base("americium", 95, 148, "Am")

val curium by elemReg.base("curium", 96, 151, "Cm")

val berkelium by elemReg.base("berkelium", 97, 150, "Bk")

val californium by elemReg.base("californium", 98, 153, "Cf")

val einsteinium by elemReg.base("einsteinium", 99, 153, "Es")

val fermium by elemReg.base("fermium", 100, 157, "Fm")

val mendelevium by elemReg.base("mendelevium", 101, 157, "Md")

val nobelium by elemReg.base("nobelium", 102, 157, "No")

val lawrencium by elemReg.base("lawrencium", 103, 159, "Lr")

val rutherfordium by elemReg.base("rutherfordium", 104, 157, "Rf")

val dubnium by elemReg.base("dubnium", 105, 163, "Db")

val seaborgium by elemReg.base("seaborgium", 106, 157, "Sg")

val borhium by elemReg.base("borhium", 107, 157, "Bh")

val hassium by elemReg.base("hassium", 108, 161, "Hs")

val meitnerium by elemReg.base("meitnerium", 109, 159, "Mt")

val darmstadtium by elemReg.base("darmstadtium", 110, 162, "Ds")

val roentgenium by elemReg.base("roentgenium", 111, 162, "Rg")

val copernicium by elemReg.base("copernicium", 112, 165, "Cn")

val nihonium by elemReg.base("nihonium", 113, 173, "Nh")

val flerovium by elemReg.base("flerovium", 114, 175, "Fl")

val moscovium by elemReg.base("moscovium", 115, 173, "Mc")

val livermorium by elemReg.base("livermorium", 116, 176, "Lv")

val tennessine by elemReg.base("tennessine", 117, 175, "Ts")

val oganesson by elemReg.base("oganesson", 118, 175, "Og")