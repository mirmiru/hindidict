package com.example.hindidict.repo

import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData
import com.example.hindidict.viewmodel.ICallback

/*
Has knowledge of where the data comes from
 */
interface IDataRepository {

    fun getWordData(uuid: String): WordLiveData

    fun addNewWord(word: Word, callback: ICallback)

    fun updateWord(word: Word, callback: ICallback)
}
