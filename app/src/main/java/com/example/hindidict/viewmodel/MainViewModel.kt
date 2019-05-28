package com.example.hindidict.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.hindidict.R
import com.example.hindidict.WordFragment
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.SentenceLiveData
import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData
import com.example.hindidict.repo.FirestoreRepositoryI
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.SnapshotParser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_word.*

/*
Implement a ViewModel that yields a LiveData

- Stores all data needed by Activities and Fragments
- Exposes the LiveData for the UI to read
 */
class MainViewModel: ViewModel() {

    private var repository: FirestoreRepositoryI = FirestoreRepositoryI()

    // LiveData
    lateinit var sentences: SentenceLiveData

    // TODO: Method for fetching the LiveData (fetched from repo) for the UI to read
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

    fun updateWord(word: Word, callback: ICallback) {
        repository.updateWord(word, callback)
    }

    fun getSentences(uuid: String, lifecycleOwner: WordFragment) {
//        val a =  repository.getSentences(uuid)

    }
}


interface ICallback {
    fun onCallback(uuid: String)
}