package com.scientianova.versatile.materialsystem.addition

import com.scientianova.versatile.materialsystem.elements.Element
import com.scientianova.versatile.materialsystem.elements.Isotope

//This class contains all the elements of the periodic table and a few common isotopes
object BaseElements {
    @JvmField
    val NULL = Element("?", 0, 0)

    @JvmField
    val HYDROGEN = Element("H", 1, 0)

    @JvmField
    val DEUTERIUM = Isotope("D", HYDROGEN, 2)

    @JvmField
    val TRITIUM = Isotope("T", HYDROGEN, 3)

    @JvmField
    val HELIUM = Element("He", 2, 2)

    @JvmField
    val HELIUM_3 = Isotope(HELIUM, 3)

    @JvmField
    val LITHIUM = Element("Li", 3, 4)

    @JvmField
    val BERYLLIUM = Element("Be", 4, 5)

    @JvmField
    val BORON = Element("B", 5, 6)

    @JvmField
    val CARBON = Element("C", 6, 6)

    @JvmField
    val NITROGEN = Element("N", 7, 7)

    @JvmField
    val OXYGEN = Element("O", 8, 8)

    @JvmField
    val FLUORINE = Element("F", 9, 10)

    @JvmField
    val NEON = Element("Ne", 10, 10)

    @JvmField
    val SODIUM = Element("Na", 11, 12)

    @JvmField
    val MAGNESIUM = Element("Mg", 12, 12)

    @JvmField
    val ALUMINUM = Element("Al", 13, 14)

    @JvmField
    val SILICON = Element("Si", 14, 14)

    @JvmField
    val PHOSPHORUS = Element("P", 15, 16)

    @JvmField
    val SULFUR = Element("S", 16, 16)

    @JvmField
    val CHLORINE = Element("Cl", 17, 18)

    @JvmField
    val ARGON = Element("Ar", 18, 22)

    @JvmField
    val POTASSIUM = Element("K", 19, 21)

    @JvmField
    val CALCIUM = Element("Ca", 20, 20)

    @JvmField
    val SCANDIUM = Element("Sc", 21, 24)

    @JvmField
    val TITANIUM = Element("Ti", 22, 26)

    @JvmField
    val VANADIUM = Element("V", 23, 28)

    @JvmField
    val CHROMIUM = Element("Cr", 24, 28)

    @JvmField
    val MANGANESE = Element("Mn", 25, 30)

    @JvmField
    val IRON = Element("Fe", 26, 30)

    @JvmField
    val COBALT = Element("Co", 27, 31)

    @JvmField
    val NICKEL = Element("Ni", 28, 30)

    @JvmField
    val COPPER = Element("Cu", 29, 35)

    @JvmField
    val ZINC = Element("Zn", 30, 35)

    @JvmField
    val GALLIUM = Element("Ga", 31, 39)

    @JvmField
    val GERMANIUM = Element("Ge", 32, 41)

    @JvmField
    val ARSENIC = Element("As", 33, 42)

    @JvmField
    val SELENIUM = Element("Se", 34, 45)

    @JvmField
    val BROMINE = Element("Br", 35, 45)

    @JvmField
    val KRYPTON = Element("Kr", 36, 48)

    @JvmField
    val RUBIDIUM = Element("Rb", 37, 48)

    @JvmField
    val STRONTIUM = Element("St", 38, 50)

    @JvmField
    val YTTRIUM = Element("Y", 39, 50)

    @JvmField
    val ZIRCONIUM = Element("Zr", 40, 51)

    @JvmField
    val NIOBIUM = Element("Nb", 41, 52)

    @JvmField
    val MOLYBDENUM = Element("Mo", 42, 54)

    @JvmField
    val TECHNETIUM = Element("Tc", 43, 55)

    @JvmField
    val RUTHENIUM = Element("Ru", 44, 57)

    @JvmField
    val RHODIUM = Element("Rh", 45, 58)

    @JvmField
    val PALLADIUM = Element("Rd", 46, 60)

    @JvmField
    val SILVER = Element("Ag", 47, 61)

    @JvmField
    val CADMIUM = Element("Cd", 48, 64)

    @JvmField
    val INDIUM = Element("In", 49, 66)

    @JvmField
    val TIN = Element("Sn", 50, 69)

    @JvmField
    val ANTIMONY = Element("Sb", 51, 71)

    @JvmField
    val TELLURIUM = Element("Te", 52, 76)

    @JvmField
    val IODINE = Element("I", 53, 74)

    @JvmField
    val XENON = Element("Xe", 54, 77)

    @JvmField
    val CAESIUM = Element("Cs", 55, 78)

    @JvmField
    val BARIUM = Element("Ba", 56, 81)

    @JvmField
    val LANTHANUM = Element("La", 57, 82)

    @JvmField
    val CERIUM = Element("Ce", 58, 82)

    @JvmField
    val PRASEODYMIUM = Element("Pr", 59, 82)

    @JvmField
    val NEODYMIUM = Element("Nd", 60, 84)

    @JvmField
    val PROMETHIUM = Element("Pm", 61, 84)

    @JvmField
    val SAMARIUM = Element("Sm", 62, 88)

    @JvmField
    val EUROPIUM = Element("Eu", 63, 89)

    @JvmField
    val GADOLINIUM = Element("Gd", 64, 93)

    @JvmField
    val TERBIUM = Element("Tb", 65, 94)

    @JvmField
    val DYSPROSIUM = Element("Dy", 66, 97)

    @JvmField
    val HOLMIUM = Element("Ho", 67, 98)

    @JvmField
    val ERBIUM = Element("Er", 68, 99)

    @JvmField
    val THULIUM = Element("Tm", 69, 100)

    @JvmField
    val YTTERBIUM = Element("Yb", 70, 103)

    @JvmField
    val LUTETIUM = Element("Lu", 71, 104)

    @JvmField
    val HAFNIUM = Element("Hf", 72, 106)

    @JvmField
    val TANTALUM = Element("Ta", 73, 108)

    @JvmField
    val TUNGSTEN = Element("W", 74, 110)

    @JvmField
    val RHENIUM = Element("Re", 75, 111)

    @JvmField
    val OSMIUM = Element("os", 76, 114)

    @JvmField
    val IRIDIUM = Element("Ir", 77, 115)

    @JvmField
    val PLATINUM = Element("Pt", 78, 117)

    @JvmField
    val GOLD = Element("Au", 79, 118)

    @JvmField
    val MERCURY = Element("Hg", 80, 121)

    @JvmField
    val THALLIUM = Element("Tl", 81, 123)

    @JvmField
    val LEAD = Element("Pb", 82, 125)

    @JvmField
    val BISMUTH = Element("Bi", 83, 126)

    @JvmField
    val POLONIUM = Element("Po", 84, 125)

    @JvmField
    val ASTATINE = Element("At", 85, 125)

    @JvmField
    val RADON = Element("Rn", 86, 136)

    @JvmField
    val FRANCIUM = Element("Fr", 87, 136)

    @JvmField
    val RADIUM = Element("Ra", 88, 138)

    @JvmField
    val ACTINIUM = Element("Ac", 89, 138)

    @JvmField
    val THORIUM = Element("Th", 90, 142)

    @JvmField
    val PROTACTINIUM = Element("Pa", 91, 140)

    @JvmField
    val URANIUM = Element("U", 92, 146)

    @JvmField
    val NEPTUNIUM = Element("Np", 93, 144)

    @JvmField
    val PLUTONIUM = Element("Pu", 94, 150)

    @JvmField
    val AMERICIUM = Element("Am", 95, 148)

    @JvmField
    val CURIUM = Element("Cm", 96, 151)

    @JvmField
    val BERKELIUM = Element("Bk", 97, 150)

    @JvmField
    val CALIFORNIUM = Element("Cf", 98, 153)

    @JvmField
    val EINSTEINIUM = Element("Es", 99, 153)

    @JvmField
    val FERMIUM = Element("Fm", 100, 157)

    @JvmField
    val MENDELEVIUM = Element("Md", 101, 157)

    @JvmField
    val NOBELIUM = Element("No", 102, 157)

    @JvmField
    val LAWRENCIUM = Element("Lr", 103, 159)

    @JvmField
    val RUTHERFORDIUM = Element("Rf", 104, 157)

    @JvmField
    val DUBNIUM = Element("Db", 105, 163)

    @JvmField
    val SEABORGIUM = Element("Sg", 106, 157)

    @JvmField
    val BORHIUM = Element("Bh", 107, 157)

    @JvmField
    val HASSIUM = Element("Hs", 108, 161)

    @JvmField
    val MEITNERIUM = Element("Mt", 109, 159)

    @JvmField
    val DARMSTADTIUM = Element("Ds", 110, 162)

    @JvmField
    val ROENTGENIUM = Element("Rg", 111, 162)

    @JvmField
    val COPERNICIUM = Element("Cn", 112, 165)

    @JvmField
    val NIHONIUM = Element("Nh", 113, 173)

    @JvmField
    val FLEROVIUM = Element("Fl", 114, 175)

    @JvmField
    val MOSCOVIUM = Element("Mc", 115, 173)

    @JvmField
    val LIVERMORIUM = Element("Lv", 116, 176)

    @JvmField
    val TENNESSINE = Element("ts", 117, 175)

    @JvmField
    val OGANESSON = Element("Og", 118, 175)
}
