package com.example.hindidict.repo

import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData

/*
Has knowledge of where the data comes from
 */
interface IDataRepository {

    fun getWordData(uuid: String): WordLiveData

    fun addNewWord(word: Word)
}
