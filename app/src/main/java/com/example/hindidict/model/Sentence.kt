package com.example.hindidict.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sentence (
    val sentenceId: String = "",
    val containsWord: String = "",
    val engSentence: String = "",
    val hindiSentence: String = ""
) : Parcelable