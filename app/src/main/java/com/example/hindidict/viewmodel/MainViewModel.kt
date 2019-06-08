package com.example.hindidict.viewmodel

import android.app.AlarmManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hindidict.helper.ICallback
import com.example.hindidict.helper.ICallbackWord
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.helper.IWordsCallback
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.SentenceLiveData
import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData
import com.example.hindidict.repo.FirestoreRepository
import java.util.*

class MainViewModel: ViewModel() {

    private var repository: FirestoreRepository = FirestoreRepository()
    private var wordCount = MutableLiveData<Int>()
    fun getWordCount(): LiveData<Int> = wordCount

    private var allWords = mutableListOf<Word>()
    fun getAllWords(): List<Word> = allWords

//    fun getWordLiveData(uuid: String): WordLiveData {
//        val liveData = repository.getWordData(uuid)
//        return liveData
//    }

    fun getAllData(callback: IEmptyCallback) {
        repository.getAllWords(object : IWordsCallback{
            override fun onCallback(list: MutableList<Word>) {
                allWords = list
                callback.onCallback()
            }
        })
    }

//    fun getSentence(uuid: String): SentenceLiveData {
//        val sentenceLiveData = repository.getSentence(uuid)
//        return sentenceLiveData
//    }

//    fun addWord(word: Word, callback: ICallback) {
//        repository.addNewWord(word, callback)
//    }

//    fun addSentence(sentence: Sentence, callback: ICallback) {
//        repository.addSentence(sentence, callback)
//    }

    fun addSentenceToWord(sentence: Sentence, callback: IEmptyCallback) {
        repository.addSentenceToWord(sentence, callback)
    }

//    fun updateSentence(sentence: Sentence, callback: IEmptyCallback) {
//        repository.updateSentence(sentence, callback)
//    }

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

