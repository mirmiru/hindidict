package com.example.hindidict.model

// Getters and setters are created automatically

data class Word(
    var uuid: String = "",
    var wordHindi: String = "",
    var wordEng: String = "",
    var sentenceHindi: String = "",
    var sentenceEnd: String = ""
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