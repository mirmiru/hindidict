package com.example.hindidict.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData
import com.example.hindidict.repo.FirestoreRepositoryI

/*
Implement a ViewModel that yields a LiveData

- Stores all data needed by Activities and Fragments
- Exposes the LiveData for the UI to read
 */
class MainViewModel: ViewModel() {

    private var repository: FirestoreRepositoryI = FirestoreRepositoryI()

    // TODO: Method for fetching the LiveData (fetched from repo) for the UI to read
    fun getWordLiveData(uuid: String): WordLiveData {
        val liveData = repository.getWordData(uuid)

        // TODO: Cache LiveData to hold it internally

        return liveData
    }

    fun addWord(word: Word, callback: ICallback) {
        repository.addNewWord(word, callback)
    }

    fun updateWord(word: Word, callback: ICallback) {
        repository.updateWord(word, callback)
    }
}

interface ICallback {
    fun onCallback(uuid: String)
}