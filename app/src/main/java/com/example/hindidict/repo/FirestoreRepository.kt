package com.example.hindidict.repo

import com.example.hindidict.model.WordLiveData
import com.google.firebase.firestore.FirebaseFirestore

/*
Manages the database
 */
class FirestoreRepository: DataRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val COLLECTION_PATH = "WORDS"

    override fun getWordData(uuid: String): WordLiveData {
        val ref = firestore.collection(COLLECTION_PATH).document(uuid)
        return WordLiveData(ref)
    }

}