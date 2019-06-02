package com.example.hindidict.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Word(
    var uuid: String = "",
    var definition: Definition = Definition(),
    var category: String = "",
    var difficult: Boolean = false,
    var quizData: QuizData = QuizData()
//    val nextQuizDate: Long = 0
): Parcelable

@Parcelize
data class Definition (
    var eng: String = "",
    var hindi: String = ""
): Parcelable

@Parcelize
data class QuizData(
    var repetitions: Int = 0,
    var interval: Int = 1,
    var easiness: Float = 2.5F,
    var nextQuizDate: Long? = System.currentTimeMillis()
): Parcelable