package com.example.hindidict.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hindidict.model.Word
import com.example.hindidict.repo.FirestoreRepository

class MainViewModel: ViewModel() {

    // TODO: Get a repository object
    private val repository: FirestoreRepository = FirestoreRepository()

    // TODO: Method for fetching the LiveData (fetched from repo) for the UI to read
    fun getWordLiveData(uuid: String): LiveData<Word> {
        val liveData = repository.getWordData(uuid)
        return liveData
    }
}