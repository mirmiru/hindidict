package com.example.hindidict.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Word(
    var uuid: String = "",
    val definition: Definition? = null,
    val category: String? = "",
    val isDifficult: Boolean = false
): Parcelable

@Parcelize
data class Definition (
    val eng: String? = "",
    val hindi: String? = ""
) : Parcelable

// Data classes enable Destructuring declarations, i.e.
/*
val steveJobs= User("Steve Jobs", 56)

fun print() {
    val (name, age) = steveJobs
    println("$name, $age years of age") // prints "Steve Jobs, 56 years of age"

    steveJobs.component1() // name
    steveJobs.component2() // age
}*/