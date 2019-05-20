package com.example.hindidict.repo

import com.example.hindidict.model.WordLiveData
import com.google.firebase.firestore.FirebaseFirestore

/*
Manages the database
 */
class FirestoreRepository: DataRepository {

    private val firestore = FirebaseFirestore.getInstance()

    override fun getWordData(uuid: String): WordLiveData {
        val ref = firestore.collection("coll-name").document(uuid)
        return WordLiveData(ref)
    }

}