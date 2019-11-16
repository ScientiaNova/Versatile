package com.emosewapixel.pixellib.machines.gui.layout

class GUIBook {
    val pages = mutableListOf<GUIPage>()

    operator fun get(index: Int) = pages[index]

    lateinit var current: GUIPage

    fun page(builder: GUIPage.() -> Unit) {
        pages += GUIPage(builder)
    }

    fun GUIPage.add(xOffset: Int = 0, yOffset: Int = 0, builder: GUIPage.() -> Unit = { }) {
        this.components.forEach {
            it.x += xOffset
            it.y += yOffset
        }
        this.builder()
        pages += this
    }
}

fun book(builder: GUIBook.() -> Unit): () -> GUIBook = {
    val book = GUIBook()
    book.builder()
    book.current = book[0]
    book
}