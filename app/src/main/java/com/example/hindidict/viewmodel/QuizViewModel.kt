package com.example.hindidict.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hindidict.SpacedRepetitionAlgorithm
import com.example.hindidict.helper.ICardsCallback
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.model.Word
import com.example.hindidict.repo.FirestoreRepository
import java.util.*

class QuizViewModel : ViewModel() {

    private var repository: FirestoreRepository = FirestoreRepository()
    private var cardSet: MutableList<Word> = mutableListOf()
    private var todaysCards: MutableList<Word> = mutableListOf()

    var currentCard = MutableLiveData<Word>()
    var isLastCard = MutableLiveData<Boolean>()

    fun getCardSet() {
        repository.getCardSet(object : ICardsCallback{
            override fun onCallback(list: MutableList<Word>) {
                cardSet = list
                getNextCard()
                isLastCard.value = false
            }
        })
    }

    fun getNextCard() {
        if (cardSet.isNotEmpty()) {
            val next = cardSet.first()
            cardSet.remove(next)
            currentCard.postValue(next)

            if (cardSet.size == 1) {
                isLastCard.postValue(true)
            }
        } else {
            // TODO Go to Quiz start fragment
        }
    }

    fun getTodaysCards() {
        repository.getTodaysCards(object : ICardsCallback{
            override fun onCallback(list: MutableList<Word>) {
                todaysCards = list
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