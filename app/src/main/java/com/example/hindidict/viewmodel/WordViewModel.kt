package com.example.hindidict.viewmodel

import androidx.lifecycle.ViewModel
import com.example.hindidict.helper.ICallbackResult
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.repo.FirestoreRepository

class WordViewModel : ViewModel() {

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

    fun deleteWord(uuid: String, callback: ICallbackResult) {
        repository.deleteWord(uuid, object : ICallbackResult{
            override fun onCallbackResult(successful: Boolean) {
                if (successful) {
                    repository.deleteSentences(uuid, callback)
                }
            }
        })
    }

//    fun addWord(word: Word, callback: ICallback) {
//        repository.addNewWord(word, callback)
//    }
//
//    fun addSentence(sentence: Sentence, callback: ICallback) {
//        repository.addSentence(sentence, callback)
//    }

}