package com.example.hindidict.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.SentenceLiveData
import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData
import com.example.hindidict.viewmodel.ICallback

/*
Has knowledge of where the data comes from
 */
interface IDataRepository {

    fun getWordData(uuid: String): WordLiveData

    fun addNewWord(word: Word, callback: ICallback)

    fun addSentence(sentence: Sentence, callback: ICallback)

    fun updateWord(word: Word, callback: ICallback)

    fun getSentences(uuid: String): LiveData<List<Sentence>>
}
