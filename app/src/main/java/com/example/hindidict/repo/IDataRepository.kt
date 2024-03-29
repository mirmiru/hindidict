package com.example.hindidict.repo

import com.example.hindidict.helper.*
import com.example.hindidict.model.*
import com.google.firebase.firestore.DocumentReference

interface IDataRepository {

    fun getWordData(uuid: String): WordLiveData

    fun getWordId(word: String, callback: ICallbackWord)

    fun getSentence(uuid: String): SentenceLiveData

    fun addNewWord(word: Word, callback: ICallback)

    fun deleteWord(uuid: String, callback: ICallbackResult)

    fun deleteSentences(uuid: String, callback: ICallbackResult)

    fun addSentence(sentence: Sentence, callback: ICallback)

    fun addWordToFavorites(uuid: String, isDifficult: Boolean, callback: IEmptyCallback)

    fun addSentenceToWord(sentence: Sentence, callback: IEmptyCallback)

    fun updateWord(word: Word, callback: ICallbackResult)

    fun updateSentence(sentence: Sentence, callback: IEmptyCallback)

    fun getCardSet(callback: IWordsCallback)

    fun getCardsDueToday(callback: IWordsCallback)

    fun getQuizSentences(uuid: String, callback: ISentencesCallback)

    fun getQuizWords(callback: IWordsCallback)

    fun resetQuizData(documentRef: DocumentReference, callback: IEmptyCallback)

    fun updateStudyDate(uuid: String, quizData: QuizData, callback: IEmptyCallback)

    fun getWordOfTheDay(callback: ICallbackWord)

    fun getAllWords(callback: IWordsCallback)
}
