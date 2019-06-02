package com.example.hindidict.model

data class Card (
    var word: Word = Word(),
    var sentences: MutableList<Sentence> = mutableListOf()
)