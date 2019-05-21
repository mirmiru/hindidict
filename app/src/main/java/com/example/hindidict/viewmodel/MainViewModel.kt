package com.example.hindidict.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hindidict.model.Word
import com.example.hindidict.repo.FirestoreRepository

/*
Stores all data needed by Activities and Fragments

Exposes the LiveData for the UI to read
 */
class MainViewModel: ViewModel() {

    private var repository: FirestoreRepository = FirestoreRepository()

    // TODO: Method for fetching the LiveData (fetched from repo) for the UI to read
    fun getWordLiveData(uuid: String): LiveData<Word> {
        val liveData = repository.getWordData(uuid)
        return liveData
    }
}