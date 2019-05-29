package com.example.hindidict.helper

import com.example.hindidict.model.Sentence

interface ICallback {
    fun onCallback(uuid: String)
}

interface IEmptyCallback {
    fun onCallback()
}

interface ICallbackSentence {
    fun onCallback(sentence: Sentence)
}