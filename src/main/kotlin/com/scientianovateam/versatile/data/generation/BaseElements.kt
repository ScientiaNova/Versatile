package com.scientianovateam.versatile.data.generation

import com.scientianovateam.versatile.materialsystem.elements.BaseElement
import com.scientianovateam.versatile.materialsystem.elements.Isotope
import net.minecraft.data.DataGenerator

fun DataGenerator.addElements() = elements {
    +BaseElement("null", "?", 0, 0)

    val hydrogen = +BaseElement("hydrogen", "H", 1, 0) as BaseElement

    +Isotope("deuterium", hydrogen, 2, "D")

    +Isotope("tritium", hydrogen, 3, "T")

    val helium = +BaseElement("helium", "He", 2, 2) as BaseElement

    +Isotope("helium_3", helium, 3)

    +BaseElement("lithium", "Li", 3, 4)

    +BaseElement("berrylium", "Be", 4, 5)

    +BaseElement("boron", "B", 5, 6)

    +BaseElement("carbon", "C", 6, 6)

    +BaseElement("nitrogen", "N", 7, 7)

    +BaseElement("oxygen", "O", 8, 8)

    +BaseElement("fluorine", "F", 9, 10)

    +BaseElement("neon", "Ne", 10, 10)

    +BaseElement("sodium", "Na", 11, 12)

    +BaseElement("magnesium", "Mg", 12, 12)

    +BaseElement("aluminum", "Al", 13, 14)

    +BaseElement("silicon", "Si", 14, 14)

    +BaseElement("phosphorus", "P", 15, 16)

    +BaseElement("sulfur", "S", 16, 16)

    +BaseElement("chlorine", "Cl", 17, 18)

    +BaseElement("argon", "Ar", 18, 22)

    +BaseElement("potassium", "K", 19, 21)

    +BaseElement("calcium", "Ca", 20, 20)

    +BaseElement("scandium", "Sc", 21, 24)

    +BaseElement("titanium", "Ti", 22, 26)

    +BaseElement("vanadium", "V", 23, 28)

    +BaseElement("chromium", "Cr", 24, 28)

    +BaseElement("manganese", "Mn", 25, 30)

    +BaseElement("iron", "Fe", 26, 30)

    +BaseElement("cobalt", "Co", 27, 31)

    +BaseElement("nickel", "Ni", 28, 30)

    +BaseElement("copper", "Cu", 29, 35)

    +BaseElement("zinc", "Zn", 30, 35)

    +BaseElement("gallium", "Ga", 31, 39)

    +BaseElement("germanium", "Ge", 32, 41)

    +BaseElement("arsenic", "As", 33, 42)

    +BaseElement("selenium", "Se", 34, 45)

    +BaseElement("bromine", "Br", 35, 45)

    +BaseElement("krypton", "Kr", 36, 48)

    +BaseElement("rubidium", "Rb", 37, 48)

    +BaseElement("strontium", "St", 38, 50)

    +BaseElement("yttrium", "Y", 39, 50)

    +BaseElement("zirconium", "Zr", 40, 51)

    +BaseElement("niobium", "Nb", 41, 52)

    +BaseElement("molybdenum", "Mo", 42, 54)

    +BaseElement("technetium", "Tc", 43, 55)

    +BaseElement("ruthenium", "Ru", 44, 57)

    +BaseElement("rhodium", "Rh", 45, 58)

    +BaseElement("palladium", "Pd", 46, 60)

    +BaseElement("silver", "Ag", 47, 61)

    +BaseElement("cadmium", "Cd", 48, 64)

    +BaseElement("indium", "In", 49, 66)

    +BaseElement("tin", "Sn", 50, 69)

    +BaseElement("antimony", "Sb", 51, 71)

    +BaseElement("tellurium", "Te", 52, 76)

    +BaseElement("iodine", "I", 53, 74)

    +BaseElement("xenon", "Xe", 54, 77)

    +BaseElement("caesium", "Cs", 55, 78)

    +BaseElement("barium", "Ba", 56, 81)

    +BaseElement("lanthanum", "La", 57, 82)

    +BaseElement("cerium", "Ce", 58, 82)

    +BaseElement("praseodymium", "Pr", 59, 82)

    +BaseElement("neodymium", "Nd", 60, 84)

    +BaseElement("promethium", "Pm", 61, 84)

    +BaseElement("samarium", "Sm", 62, 88)

    +BaseElement("europium", "Eu", 63, 89)

    +BaseElement("gadolinium", "Gd", 64, 93)

    +BaseElement("terbium", "Tb", 65, 94)

    +BaseElement("dysprosium", "Dy", 66, 97)

    +BaseElement("holmium", "Ho", 67, 98)

    +BaseElement("erbium", "Er", 68, 99)

    +BaseElement("thulium", "Tm", 69, 100)

    +BaseElement("ytterbium", "Yb", 70, 103)

    +BaseElement("lutetium", "Lu", 71, 104)

    +BaseElement("hafnium", "Hf", 72, 106)

    +BaseElement("tantalum", "Ta", 73, 108)

    +BaseElement("tungsten", "W", 74, 110)

    +BaseElement("rhenium", "Re", 75, 111)

    +BaseElement("osmium", "os", 76, 114)

    +BaseElement("iridium", "Ir", 77, 115)

    +BaseElement("platinum", "Pt", 78, 117)

    +BaseElement("gold", "Au", 79, 118)

    +BaseElement("mercury", "Hg", 80, 121)

    +BaseElement("thallium", "Tl", 81, 123)

    +BaseElement("lead", "Pb", 82, 125)

    +BaseElement("bismuth", "Bi", 83, 126)

    +BaseElement("polonium", "Po", 84, 125)

    +BaseElement("astatine", "At", 85, 125)

    +BaseElement("radon", "Rn", 86, 136)

    +BaseElement("francium", "Fr", 87, 136)

    +BaseElement("radium", "Ra", 88, 138)

    +BaseElement("actinium", "Ac", 89, 138)

    +BaseElement("thorium", "Th", 90, 142)

    +BaseElement("protactinium", "Pa", 91, 140)

    +BaseElement("uranium", "U", 92, 146)

    +BaseElement("neptunium", "Np", 93, 144)

    +BaseElement("plutonium", "Pu", 94, 150)

    +BaseElement("americium", "Am", 95, 148)

    +BaseElement("curium", "Cm", 96, 151)

    +BaseElement("berkelium", "Bk", 97, 150)

    +BaseElement("californium", "Cf", 98, 153)

    +BaseElement("einsteinium", "Es", 99, 153)

    +BaseElement("fermium", "Fm", 100, 157)

    +BaseElement("mendelevium", "Md", 101, 157)

    +BaseElement("nobelium", "No", 102, 157)

    +BaseElement("lawrencium", "Lr", 103, 159)

    +BaseElement("rutherfordium", "Rf", 104, 157)

    +BaseElement("dubnium", "Db", 105, 163)

    +BaseElement("seaborgium", "Sg", 106, 157)

    +BaseElement("bohrium", "Bh", 107, 157)

    +BaseElement("hassium", "Hs", 108, 161)

    +BaseElement("meitnerium", "Mt", 109, 159)

    +BaseElement("darmstadtium", "Ds", 110, 162)

    +BaseElement("roentgenium", "Rg", 111, 162)

    +BaseElement("copernicium", "Cn", 112, 165)

    +BaseElement("nihonium", "Nh", 113, 173)

    +BaseElement("flerovium", "Fl", 114, 175)

    +BaseElement("moscovium", "Mc", 115, 173)

    +BaseElement("livermorium", "Lv", 116, 176)

    +BaseElement("tennessine", "ts", 117, 175)

    +BaseElement("oganesson", "Og", 118, 175)
}
