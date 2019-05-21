package com.example.hindidict.repo

import androidx.lifecycle.LiveData
import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData
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

//        ref.addSnapshotListener { snapshot, exception ->
//            if (snapshot != null) {
//                // Do something - Reach into snapshot, assign to model object
//                val word = snapshot.get("definition")
//
//                // Do not mix data store and views, e.g. do not update UI here
//            } else if (exception == null) {
//                // Handle error
//            }
//
//        }
    }

}