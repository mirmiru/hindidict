package com.example.hindidict.repo

import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData
import com.example.hindidict.viewmodel.ICallback
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

    override fun addNewWord(word: Word, callback: ICallback) {
        try {
            // TODO: Callback to check for task finished?
            val documentRef = firestore.collection(COLLECTION_PATH).document()
            word.uuid = documentRef.id

            documentRef
                .set(word)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)
                        // Callback in order to move from AddWord -> Word fragment
                        callback.onCallback(word.uuid)
                }
                .addOnFailureListener {
                    // TODO Notify of failed addition
                }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun updateWord(word: Word, callback: ICallback) {
        val documentRef = firestore.collection(COLLECTION_PATH).document(word.uuid)
        documentRef
            .set(word)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    callback.onCallback(word.uuid)
            }
            .addOnFailureListener {
                // TODO Notify of failed update
            }
    }
}