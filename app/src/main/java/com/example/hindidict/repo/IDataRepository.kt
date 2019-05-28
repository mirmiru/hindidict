package com.example.hindidict.repo

import androidx.lifecycle.LiveData
import com.example.hindidict.helper.ICallback
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData

/*
Has knowledge of where the data comes from
 */
interface IDataRepository {

    fun getWordData(uuid: String): WordLiveData

    fun addNewWord(word: Word, callback: ICallback)

    fun addSentence(sentence: Sentence, callback: ICallback)

    fun addSentenceToWord(sentence: Sentence, callback: IEmptyCallback)

    fun updateWord(word: Word, callback: ICallback)
}
