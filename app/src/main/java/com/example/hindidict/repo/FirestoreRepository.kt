package com.example.hindidict.repo

import com.example.hindidict.helper.ICallback
import com.example.hindidict.helper.ICardsCallback
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.SentenceLiveData
import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

/*
Manages the database
 */
class FirestoreRepository: IDataRepository {

    private val FIRESTORE = FirebaseFirestore.getInstance()
    private val COLLECTION_WORDS = "words"
    private val COLLECTION_SENTENCES = "sentences"
    private val COLLECTION_QUIZ_CARDS = "quiz_cards"

    override fun getWordData(uuid: String): WordLiveData {
        val ref = FIRESTORE.collection(COLLECTION_WORDS).document(uuid)
        return WordLiveData(ref)
    }

    override fun addNewWord(word: Word, callback: ICallback) {
        try {
            val documentRef = FIRESTORE.collection(COLLECTION_WORDS).document()
            word.uuid = documentRef.id

            documentRef
                .set(word)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)
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
        val documentRef = FIRESTORE.collection(COLLECTION_SENTENCES).document()
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

    override fun getSentence(uuid: String): SentenceLiveData {
        val documentRef = FIRESTORE.collection(COLLECTION_SENTENCES).document(uuid)
        return SentenceLiveData(documentRef)
    }

    override fun addSentenceToWord(sentence: Sentence, callback: IEmptyCallback) {
        val documentRef = FIRESTORE.collection(COLLECTION_SENTENCES).document()
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
                    callback.onCallback()
                }
            }
            .addOnFailureListener { e ->
                e.stackTrace
            }
    }

    override fun updateWord(word: Word, callback: ICallback) {
        val documentRef = FIRESTORE.collection(COLLECTION_WORDS).document(word.uuid)
        documentRef
            .set(word, SetOptions.merge())
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    callback.onCallback(word.uuid)
            }
            .addOnFailureListener { e ->
                e.stackTrace
            }
    }

    override fun updateSentence(sentence: Sentence, callback: IEmptyCallback) {
        val s = sentence
        val documentRef = FIRESTORE.collection(COLLECTION_SENTENCES).document(sentence.sentenceId)
        documentRef
            .set(sentence, SetOptions.merge())
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    callback.onCallback()
            }
            .addOnFailureListener { e ->
                e.stackTrace
            }
    }

    override fun addWordToFavorites(uuid: String, isDifficult: Boolean, callback: IEmptyCallback) {
        val documentRef = FIRESTORE.collection(COLLECTION_WORDS).document(uuid)

        documentRef
            .update("difficult",isDifficult)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback.onCallback()
                }
            }
            .addOnFailureListener { e ->
                e.stackTrace
            }
    }

    override fun updateStudyDate(uuid: String, nextDate: Long, callback: IEmptyCallback) {
        val documentRef = FIRESTORE.collection(COLLECTION_WORDS).document(uuid)

        documentRef
            .update("nextQuizDate", nextDate)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    callback.onCallback()
            }.addOnFailureListener { e ->
                e.stackTrace
            }
    }

    override fun getCardSet(callback: ICardsCallback) {
        FIRESTORE
            .collection(COLLECTION_WORDS)
            .whereEqualTo("difficult", true)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Word>()
                    task.result?.forEach {
                        list.add(it.toObject(Word::class.java))
                    }
                    callback.onCallback(list)
                }
            }
    }

}