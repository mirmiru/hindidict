package com.example.hindidict.helper

import com.example.hindidict.model.Sentence
import com.example.hindidict.model.Word

interface ICallback {
    fun onCallback(uuid: String)
}

interface IEmptyCallback {
    fun onCallback()
}

interface ICardsCallback {
    fun onCallback(list: MutableList<Word>)
}