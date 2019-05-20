package com.example.hindidict.model

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.DocumentReference

class WordLiveData(
    private val documentRef: DocumentReference
): LiveData<Word>() {

    // Someone wants that data!
    override fun onActive() {
        super.onActive()
    }

    // No one's interested in the data
    override fun onInactive() {
        super.onInactive()
    }

}
