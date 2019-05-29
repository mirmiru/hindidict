package com.example.hindidict.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hindidict.WordFragment
import com.example.hindidict.helper.ICallback
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.SentenceLiveData
import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData
import com.example.hindidict.repo.FirestoreRepository

/*
Implement a ViewModel that yields a LiveData

- Stores all data needed by Activities and Fragments
- Exposes the LiveData for the UI to read
 */
class MainViewModel: ViewModel() {

    private var repository: FirestoreRepository = FirestoreRepository()

    fun getWordLiveData(uuid: String): WordLiveData {
        val liveData = repository.getWordData(uuid)

        // TODO: Cache LiveData to hold it internally
        return liveData
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

    fun updateWord(word: Word, callback: ICallback) {
        repository.updateWord(word, callback)
    }

}

