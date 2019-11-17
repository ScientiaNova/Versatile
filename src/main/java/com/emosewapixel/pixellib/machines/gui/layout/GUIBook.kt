package com.emosewapixel.pixellib.machines.gui.layout

class GUIBook {
    val pages = mutableListOf<GUIPage>()

    operator fun get(index: Int) = pages[index]

    lateinit var current: GUIPage

    fun page(minWidth: Int = 0, minHeight: Int = 0, builder: GUIPage.() -> Unit) {
        pages += GUIPage(minWidth, minHeight, builder)
    }

    fun GUIPage.add(xOffset: Int = 0, yOffset: Int = 0, builder: GUIPage.() -> Unit = { }) {
        this.offset(xOffset, yOffset)
        this.builder()
        pages += this
    }
}

fun book(builder: GUIBook.() -> Unit): GUIBook {
    val book = GUIBook()
    book.builder()
    book.current = book[0]
    return book
}