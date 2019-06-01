package com.example.hindidict.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Word(
    var uuid: String = "",
    val definition: Definition? = null,
    val category: String? = "",
    val difficult: Boolean = false,
    val quizData: QuizData? = null,
    val nextQuizDate: Long = 0
): Parcelable

@Parcelize
data class Definition (
    val eng: String? = "",
    val hindi: String? = ""
): Parcelable

@Parcelize
data class QuizData(
    var repetitions: Int = 0,
    var interval: Int = 1,
    var easiness: Float = 2.5F
): Parcelable