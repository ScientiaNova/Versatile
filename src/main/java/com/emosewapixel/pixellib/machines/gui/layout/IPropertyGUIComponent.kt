package com.emosewapixel.pixellib.machines.gui.layout

import com.emosewapixel.pixellib.machines.properties.IMachineProperty

interface IPropertyGUIComponent : IGUIComponent {
    val property: IMachineProperty
}