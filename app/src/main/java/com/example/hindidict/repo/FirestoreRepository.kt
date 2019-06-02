package com.example.hindidict.repo

import android.text.format.DateUtils
import com.example.hindidict.helper.*
import com.example.hindidict.model.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.*
import kotlin.collections.HashMap

/*
Manages the database
 */
class FirestoreRepository: IDataRepository {

    private val FIRESTORE = FirebaseFirestore.getInstance()
    private val COLLECTION_WORDS = "wordsss"
    private val COLLECTION_SENTENCES = "sentences"

    override fun getWordData(uuid: String): WordLiveData {
        val ref = FIRESTORE.collection(COLLECTION_WORDS).document(uuid)
        return WordLiveData(ref)
    }

    override fun addNewWord(word: Word, callback: ICallback) {
        try {
            val documentRef = FIRESTORE.collection(COLLECTION_WORDS)
                .document()

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

    override fun updateStudyDate(uuid: String, quizData: QuizData, callback: IEmptyCallback) {
        val documentRef = FIRESTORE.collection(COLLECTION_WORDS).document(uuid)

        documentRef
            .update("quizData", quizData)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    callback.onCallback()
            }.addOnFailureListener { e ->
                e.stackTrace
            }
    }

    override fun getCardSet(callback: IWordsCallback) {
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

    fun getTodaysCards(callback: IWordsCallback) {
        getQuizWords(object : IWordsCallback{
            override fun onCallback(list: MutableList<Word>) {
                val words = mutableListOf<Word>()
                list.forEach {word ->
                    val date = Date(word.quizData.nextQuizDate!!)
                    if (DateUtils.isToday(word.quizData?.nextQuizDate!!)) {
                        words.add(word)
                    }
                }
                callback.onCallback(words)
            }
        })
    }

    private fun getQuizWords(callback: IWordsCallback) {
        FIRESTORE.collection(COLLECTION_WORDS)
            .whereEqualTo("difficult", true)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var words = mutableListOf<Word>()
                    task.result?.forEach {
                        val word = it.toObject(Word::class.java)
                        if (DateUtils.isToday(word.quizData?.nextQuizDate!!)) {
                            words.add(word)
                        }
                    }
                    callback.onCallback(words)
                }
            }
    }

    fun getQuizSentences(uuid: String, callback: ISentencesCallback) {
        val documentRef = FIRESTORE.collection(COLLECTION_SENTENCES)
            .whereEqualTo("containsWord", uuid)

        documentRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var sentences = mutableListOf<Sentence>()
                task.result?.forEach { it ->
                    val sentence = it.toObject(Sentence::class.java)
                    sentences.add(sentence)
                }
                callback.onCallback(sentences)
            }
        }
    }

}