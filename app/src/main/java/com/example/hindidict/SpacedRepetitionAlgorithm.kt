package com.example.hindidict

import com.example.hindidict.model.QuizWord

class SpacedRepetitionAlgorithm(quizWord: QuizWord, userResponse: Int) {

    private var repetitions = quizWord.repetitions
    private var interval = quizWord.interval
    private var easiness = quizWord.easiness

    /*
    * 5 - perfect response
    * 4 - correct response after a hesitation
    * 3 - correct response recalled with serious difficulty
    * 2 - incorrect response; where the correct one seemed easy to recall
    * 1 - incorrect response; the correct one remembered
    * 0 - complete blackout.
    * */
    private var response = userResponse

    /*
    * Multiplier used to increase interval factor.
    * Range from 1.3 to 2.5
    * */
    private fun setEasiness() {
        easiness = Math.max(1.3F, easiness+0.1F - 5.0F-response) * (0.08F + (5.0F-response) * 0.02F)
    }

    // # of times card is shown to user.
    // 0 for new card, 1 when seeing card for the first time.
    private fun setRepetitions() {
        repetitions = when {
            response < 3 -> 0       // User does not remember card
            else -> repetitions++
        }
    }

    /*
    * Number of days between repetitions.
    * */
    private fun setInterval() {
        interval = when {
            repetitions <= 1 -> 1
            repetitions == 2 -> 6
            else -> Math.round(interval*easiness)
        }
    }

    /*
    * When will card be shown the next time.
    * */
    fun getNextStudyDate(): Long {
        setEasiness()
        setRepetitions()
        setInterval()

        var secondsPerDay = 86400
        var currentTime = System.currentTimeMillis()
        return currentTime + (secondsPerDay * interval)
    }
}
