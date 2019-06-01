package com.example.hindidict.repo

import androidx.lifecycle.LiveData
import com.example.hindidict.helper.ICallback
import com.example.hindidict.helper.ICardsCallback
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.model.*

/*
Has knowledge of where the data comes from
 */
interface IDataRepository {

    fun getWordData(uuid: String): WordLiveData

    fun getSentence(uuid: String): SentenceLiveData

    fun addNewWord(word: Word, callback: ICallback)

    fun addSentence(sentence: Sentence, callback: ICallback)

    fun addWordToFavorites(uuid: String, isDifficult: Boolean, callback: IEmptyCallback)

    fun addSentenceToWord(sentence: Sentence, callback: IEmptyCallback)

    fun updateWord(word: Word, callback: ICallback)

    fun updateSentence(sentence: Sentence, callback: IEmptyCallback)

    fun getCardSet(callback: ICardsCallback)

    fun updateStudyDate(uuid: String, nextDate: Long,callback: IEmptyCallback)
}
