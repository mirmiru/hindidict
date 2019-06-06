package com.example.hindidict.viewmodel

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.hindidict.SpacedRepetitionAlgorithm
import com.example.hindidict.helper.IWordsCallback
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.helper.ISentencesCallback
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.Word
import com.example.hindidict.repo.FirestoreRepository
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class QuizViewModel : ViewModel() {

    private var repository: FirestoreRepository = FirestoreRepository()
    private var cardSet: MutableList<Word> = mutableListOf()

    private var currentCard = SingleLiveEvent<Word>()
    fun getCurrentCard(): LiveData<Word> = currentCard

    private var isLastCard = MutableLiveData<Boolean>()
    fun getIsLastCard(): LiveData<Boolean> = isLastCard

    private var sentenceData = SingleLiveEvent<Sentence>()
    fun getSentenceData(): LiveData<Sentence> = sentenceData

//    private var noCardsDue = SingleLiveEvent<Boolean>()
    private var noCardsDue = MutableLiveData<Boolean>()
    fun getNoCardsDue(): LiveData<Boolean> = noCardsDue

//    fun getCardSet() {
//        repository.getCardSet(object : IWordsCallback{
//            override fun onCallback(list: MutableList<Word>) {
//                cardSet = list
//                if (list.isEmpty()) {
//                    noCardsDue.postValue(true)
//                }
//                getNextCard()
//                isLastCard.value = false
//            }
//        })
//    }

    private fun getNextCard() {
        if (cardSet.isNotEmpty()) {
            if (cardSet.size == 1) {
                isLastCard.postValue(true)
            }
            currentCard.postValue(cardSet.first())
            sentenceData.postValue(Sentence())
            val next = cardSet.first()
            cardSet.remove(next)
        }
//        else if (cardSet.isEmpty()) {
//            noCardsDue.postValue(true)
//        }
    }

    fun getCardsDueToday() {
        repository.getCardsDueToday(object : IWordsCallback{
            override fun onCallback(list: MutableList<Word>) {
                cardSet = list
                if (list.isEmpty()) {
                    noCardsDue.postValue(true)
                }
                getNextCard()
                isLastCard.value = false
            }
        })
    }

    fun getSentenceForCard() {
        repository.getQuizSentences(currentCard.value?.uuid!!, object : ISentencesCallback{
            override fun onCallback(sentences: MutableList<Sentence>) {
                // Given multiple sentences, get random
                val i = Random().nextInt(sentences.size)
                sentenceData.postValue(sentences[i])
            }
        })
    }

    fun clearSentence() {
        sentenceData.postValue(Sentence())
    }

    fun setStudyDate(response: Int) {
        val quizData = currentCard.value?.quizData
        val algorithm = SpacedRepetitionAlgorithm()
        val studyDate = algorithm.getNextStudyDate(quizData!!, response)

        repository.updateStudyDate(currentCard.value!!.uuid, studyDate, object : IEmptyCallback {
            override fun onCallback() {
                getNextCard()
            }
        })
    }
}

// Use SingleLiveEvent in order to remove old LiveData when reloading fragment
class SingleLiveEvent<T>: MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        if (hasActiveObservers()) {

        }
        super.observe(owner, Observer { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(value: T) {
        pending.set(true)
        super.setValue(value)
    }
}