package com.example.hindidict.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData
import com.example.hindidict.viewmodel.ICallback
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

/*
Manages the database
 */
class FirestoreRepositoryI: IDataRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val COLLECTION_PATH = "words"
    private val COLLECTION_SENTENCES = "sentences"

    override fun getWordData(uuid: String): WordLiveData {
        val ref = firestore.collection(COLLECTION_PATH).document(uuid)

        return WordLiveData(ref)
    }

    override fun addNewWord(word: Word, callback: ICallback) {
        try {
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
                    it.stackTrace
                }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun addSentence(sentence: Sentence, callback: ICallback) {
        val documentRef = firestore.collection(COLLECTION_SENTENCES).document()
        val sentenceId = documentRef.id

        documentRef
            .set(HashMap<String, String>().apply {
                this.put("sentenceId", sentenceId)
                this.put("containsWord", sentence.containsWord)
                this.put("engSentence", sentence.engSentence)
                this.put("hindiSentence", sentence.hindiSentence)
            })
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback.onCallback(sentence.containsWord)
                }
            }
    }

    override fun updateWord(word: Word, callback: ICallback) {
        val documentRef = firestore.collection(COLLECTION_PATH).document(word.uuid)
        documentRef
            .set(word, SetOptions.merge())
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    callback.onCallback(word.uuid)
            }
            .addOnFailureListener {
                // TODO Notify of failed update
            }
    }

    override fun getSentences(uuid: String): LiveData<List<Sentence>> {
        var list: MutableLiveData<List<Sentence>> = MutableLiveData()
        val ref = firestore.collection(COLLECTION_PATH).document(uuid)
        return list
    }

}