package com.example.hindidict.viewmodel

import androidx.lifecycle.ViewModel
import com.example.hindidict.helper.ICallback
import com.example.hindidict.helper.ICallbackResult
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.SentenceLiveData
import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData
import com.example.hindidict.repo.FirestoreRepository

class WordViewModel : ViewModel() {

    private val spinnerList = listOf(
        "Noun", "Verb", "Adjective", "Adverb", "Grammar"
    )
    private var repository: FirestoreRepository = FirestoreRepository()

    fun populateSpinner(): List<String> {
        return spinnerList
    }

    fun getSpinnerPosition(string: String): Int {
        return spinnerList.indexOf(string)
    }

    fun getCategory(position: Int): String {
        return spinnerList[position]
    }

    fun addWord(word: Word, callback: ICallback) {
        repository.addNewWord(word, callback)
    }

    fun getWordLiveData(uuid: String): WordLiveData {
        val liveData = repository.getWordData(uuid)
        return liveData
    }

    fun addSentence(sentence: Sentence, callback: ICallback) {
        repository.addSentence(sentence, callback)
    }

    fun getSentence(uuid: String): SentenceLiveData {
        val sentenceLiveData = repository.getSentence(uuid)
        return sentenceLiveData
    }

    fun updateSentence(sentence: Sentence, callback: IEmptyCallback) {
        repository.updateSentence(sentence, callback)
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

    fun updateWord(word: Word, callback: ICallbackResult) {
        repository.updateWord(word, callback)
    }

//    fun addWord(word: Word, callback: ICallback) {
//        repository.addNewWord(word, callback)
//    }
//
//    fun addSentence(sentence: Sentence, callback: ICallback) {
//        repository.addSentence(sentence, callback)
//    }

}