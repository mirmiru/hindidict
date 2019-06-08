package com.example.hindidict.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hindidict.helper.ICallbackWord
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.helper.IWordsCallback
import com.example.hindidict.model.Word
import com.example.hindidict.repo.FirestoreRepository

class MainViewModel: ViewModel() {

    private var repository: FirestoreRepository = FirestoreRepository()
    private var wordCount = MutableLiveData<Int>()
    fun getWordCount(): LiveData<Int> = wordCount
    private var allWords = mutableListOf<Word>()
    fun getAllWords(): List<Word> = allWords

    fun getAllData(callback: IEmptyCallback) {
        repository.getAllWords(object : IWordsCallback{
            override fun onCallback(list: MutableList<Word>) {
                allWords = list
                callback.onCallback()
            }
        })
    }

    fun addWordToFavorites(uuid: String, isDifficult: Boolean, callback: IEmptyCallback) {
        repository.addWordToFavorites(uuid, isDifficult, callback)
    }

    fun getWordOfTheDay(callback: ICallbackWord) {
        repository.getWordOfTheDay(callback)
    }

    fun getWordsDueCount() {
        repository.getCardsDueToday(object: IWordsCallback{
            override fun onCallback(list: MutableList<Word>) {
                val count = list.size
                wordCount.postValue(count)
            }
        })
    }
}

