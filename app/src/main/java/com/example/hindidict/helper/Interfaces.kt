package com.example.hindidict.helper

import com.example.hindidict.model.Card
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.Word

interface ICallback {
    fun onCallback(uuid: String)
}

interface IEmptyCallback {
    fun onCallback()
}

interface IWordsCallback {
    fun onCallback(list: MutableList<Word>)
}

interface ISentencesCallback {
    fun onCallback(sentences: MutableList<Sentence>)
}

interface ICardsCallback {
    fun onCallback(cards: MutableList<Card>)
}