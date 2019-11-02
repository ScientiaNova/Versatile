package com.emosewapixel.pixellib.machines.gui.layout

import com.emosewapixel.pixellib.machines.gui.BaseContainer

interface IInteractableGUIComponent : IGUIComponent {
    fun detectAndSendChanges(container: BaseContainer)
}