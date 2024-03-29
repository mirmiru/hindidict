package com.example.hindidict.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.*

class WordLiveData(
    // DocumentReference points to one document in the database
    private val documentRef: DocumentReference
): LiveData<Word>(), EventListener<DocumentSnapshot> {

    private var listenerRegistration: ListenerRegistration? = null

    // Someone wants that data!
    // observers 0 -> 1
    override fun onActive() {
        listenerRegistration = documentRef.addSnapshotListener(this)
    }

    // No one's interested in the data
    override fun onInactive() {
        listenerRegistration!!.remove()
    }

    override fun onEvent(snap: DocumentSnapshot?, e: FirebaseFirestoreException?) {
        if (snap != null && snap.exists()) {
            val model = snap.toObject(Word::class.java)
            // Send object to observers
            super.setValue(model)
        } else if (e != null) {
           e.stackTrace
        }
    }
}

class SentenceLiveData(
    private val documentRef: DocumentReference
): LiveData<Sentence>(), EventListener<DocumentSnapshot> {
    private var listenerRegistration: ListenerRegistration? = null

    override fun onActive() {
        listenerRegistration = documentRef.addSnapshotListener(this)
    }

    override fun onInactive() {
        listenerRegistration!!.remove()
    }

    override fun onEvent(snap: DocumentSnapshot?, e: FirebaseFirestoreException?) {
        if (snap != null && snap.exists()) {
            val model = snap.toObject(Sentence::class.java)
            super.setValue(model)
        } else if (e != null) {
            e.stackTrace
        }
    }
}
