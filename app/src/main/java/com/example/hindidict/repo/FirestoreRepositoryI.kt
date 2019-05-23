package com.example.hindidict.repo

import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore

/*
Manages the database
 */
class FirestoreRepositoryI: IDataRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val COLLECTION_PATH = "words"

    override fun getWordData(uuid: String): WordLiveData {
        // Reference to the document
        val ref = firestore.collection(COLLECTION_PATH).document(uuid)

        return WordLiveData(ref)
    }

    override fun addNewWord(word: Word) {
        try {
            // TODO: Callback to check for task finished?
            val documentRef = firestore
                .collection(COLLECTION_PATH)
                .add(word)
            val a = documentRef
        } catch (e: Throwable) {
            e.printStackTrace()
            // TODO: Handle e.
        }
    }

    override fun editWord(word: Word) {
        val ref = firestore.collection(COLLECTION_PATH).document(word.uuid)
    }
}