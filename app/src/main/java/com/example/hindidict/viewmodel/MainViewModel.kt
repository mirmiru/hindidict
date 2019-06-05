package com.example.hindidict.viewmodel

import androidx.lifecycle.ViewModel
import com.example.hindidict.helper.ICallback
import com.example.hindidict.helper.ICallbackWord
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.SentenceLiveData
import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData
import com.example.hindidict.repo.FirestoreRepository

class MainViewModel: ViewModel() {

    private var repository: FirestoreRepository = FirestoreRepository()

    fun getWordLiveData(uuid: String): WordLiveData {
        val liveData = repository.getWordData(uuid)
        return liveData
    }

    fun getSentence(uuid: String): SentenceLiveData {
        val sentenceLiveData = repository.getSentence(uuid)
        return sentenceLiveData
    }

    fun addWord(word: Word, callback: ICallback) {
        repository.addNewWord(word, callback)
    }

    fun addSentence(sentence: Sentence, callback: ICallback) {
        repository.addSentence(sentence, callback)
    }

    fun addSentenceToWord(sentence: Sentence, callback: IEmptyCallback) {
        repository.addSentenceToWord(sentence, callback)
    }

//    fun updateWord(word: Word, callback: ICallback) {
//        repository.updateWord(word, callback)
//    }

    fun updateSentence(sentence: Sentence, callback: IEmptyCallback) {
        repository.updateSentence(sentence, callback)
    }

    fun addWordToFavorites(uuid: String, isDifficult: Boolean, callback: IEmptyCallback) {
        repository.addWordToFavorites(uuid, isDifficult, callback)
    }

    fun getWordOfTheDay(callback: ICallbackWord) {
        repository.getWordOfTheDay(callback)
    }
}

