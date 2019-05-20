package com.example.hindidict.repo

import com.example.hindidict.model.WordLiveData

/*
Has knowledge of where the data comes from
 */
interface DataRepository {

    fun getWordData(uuid: String): WordLiveData

}
