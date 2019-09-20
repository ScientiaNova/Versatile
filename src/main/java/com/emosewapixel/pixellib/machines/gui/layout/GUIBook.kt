package com.emosewapixel.pixellib.machines.gui.layout

class GUIBook {
    val pages = mutableListOf<GUIPage>()

    fun page(builder: GUIPage.() -> Unit) {
        val page = GUIPage()
        page.builder()
        pages += page
    }
}

fun book(builder: GUIBook.() -> Unit): () -> GUIBook = {
    val book = GUIBook()
    book.builder()
    book
}