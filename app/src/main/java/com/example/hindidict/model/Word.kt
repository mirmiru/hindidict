package com.example.hindidict.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

// Getters and setters are created automatically
data class Word(
    val uuid: String = "",
    val definition: Definition? = null,
    val category: String? = "",
    val isDifficult: Boolean = false,
    val sentences: MutableList<Sentence> = mutableListOf()
)

data class Definition (
    val eng: String? = "",
    val hindi: String? = ""
)

data class Sentence (
    val contains: MutableList<String> = mutableListOf(),
    val engSentence: String? = "",
    val hindiSentence: String? = "",
    @ServerTimestamp val dateCreated: Timestamp? = null
    //val dateCreated: String? = 0
)

// Data classes enable Destructuring declarations, i.e.
/*
val steveJobs= User("Steve Jobs", 56)

fun print() {
    val (name, age) = steveJobs
    println("$name, $age years of age") // prints "Steve Jobs, 56 years of age"

    steveJobs.component1() // name
    steveJobs.component2() // age
}*/