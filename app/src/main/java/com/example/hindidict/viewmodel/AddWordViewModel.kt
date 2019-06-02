package com.example.hindidict.viewmodel

import androidx.lifecycle.ViewModel
import com.example.hindidict.helper.ICallback
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.Word
import com.example.hindidict.repo.FirestoreRepository

class AddWordViewModel : ViewModel() {

    private val spinnerList = listOf(
        "Noun", "Verb", "Adjective", "Adverb", "Grammar"
    )
    private var repository: FirestoreRepository = FirestoreRepository()

    fun populateSpinner(): List<String> {
        return spinnerList
    }

    fun getCategory(position: Int): String {
        return spinnerList[position]
    }

    fun addWord(word: Word, callback: ICallback) {
        repository.addNewWord(word, callback)
    }

    fun addSentence(sentence: Sentence, callback: ICallback) {
        repository.addSentence(sentence, callback)
    }

}