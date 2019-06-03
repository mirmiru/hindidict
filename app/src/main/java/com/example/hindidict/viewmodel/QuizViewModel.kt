package com.example.hindidict.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hindidict.SpacedRepetitionAlgorithm
import com.example.hindidict.helper.IWordsCallback
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.helper.ISentencesCallback
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.Word
import com.example.hindidict.repo.FirestoreRepository
import java.util.*

class QuizViewModel : ViewModel() {

    private var repository: FirestoreRepository = FirestoreRepository()
    private var cardSet: MutableList<Word> = mutableListOf()
    private var cardsDueToday: MutableList<Word> = mutableListOf()

    var currentCard = MutableLiveData<Word>()
    var isLastCard = MutableLiveData<Boolean>()
    var sentenceData = MutableLiveData<Sentence>()

    fun clearStack() {
        currentCard.postValue(null)
    }

    fun getCardSet() {
        repository.getCardSet(object : IWordsCallback{
            override fun onCallback(list: MutableList<Word>) {
                cardSet = list
                getNextCard()
                isLastCard.value = false
            }
        })
    }

    fun getNextCard() {
        if (cardSet.size == 1) {
            isLastCard.postValue(true)
        }

        if (cardSet.isNotEmpty()) {
            val next = cardSet.first()
            cardSet.remove(next)
            currentCard.postValue(next)
        } else {
            // TODO Go to Quiz start fragment
        }
    }

    fun getCardsDueToday() {
        repository.getCardsDueToday(object : IWordsCallback{
            override fun onCallback(list: MutableList<Word>) {
                cardSet = list
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

    fun setStudyDate(response: Int) {
        val quizData = currentCard.value?.quizData
        val algorithm = SpacedRepetitionAlgorithm()
        val studyDate = algorithm.getNextStudyDate(quizData!!, response)

        //TODO Remove test
        val readableDate = Date(studyDate.nextQuizDate!!)

        repository.updateStudyDate(currentCard.value!!.uuid, studyDate, object : IEmptyCallback {
            override fun onCallback() {
                getNextCard()
            }
        })
    }
}