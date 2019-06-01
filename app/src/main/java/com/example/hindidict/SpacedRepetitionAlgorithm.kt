package com.example.hindidict

import com.example.hindidict.model.QuizData

class SpacedRepetitionAlgorithm {

    private var repetitions = 0
    private var interval = 0
    private var easiness = 0F
    private val secondsPerDay = 60*60*24

    /*
    * 5 - perfect response
    * 4 - correct response after a hesitation
    * 3 - correct response recalled with serious difficulty
    * 2 - incorrect response; where the correct one seemed easy to recall
    * 1 - incorrect response; the correct one remembered
    * 0 - complete blackout.
    * */
    private var response = 0

    private fun setValues(quizData: QuizData, userResponse: Int) {
        repetitions = quizData.repetitions
        interval = quizData.interval
        easiness = quizData.easiness
        response = userResponse
    }

    /*
    * Multiplier used to increase interval factor.
    * Range from 1.3 to 2.5
    * */
    private fun setEasiness() {
        easiness = Math.max(1.3F, easiness+0.1F - (5.0F-response) * (0.08F + (5.0F-response) * 0.02F))
    }

    // # of times card is shown to user.
    // 0 for new card, 1 when seeing card for the first time.
    private fun setRepetitions() {
        repetitions = when {
            response < 3 -> 0       // User does not remember card
            else -> repetitions+1
        }
    }

    /*
    * Number of days between repetitions.
    * */
    private fun setInterval() {
            if (repetitions <= 1) {
                interval = 1
            }
            else if (repetitions == 2) {
                interval = 6
            }
            else {
                interval = Math.round(interval * easiness)
            }
    }

    /*
    * When will card be shown the next time.
    * */
    fun getNextStudyDate(quizData: QuizData, response: Int): QuizData {
        setValues(quizData, response)

        setEasiness()
        setRepetitions()
        setInterval()

        val currentTime = System.currentTimeMillis()
        val nextDate =  currentTime + (secondsPerDay * interval)

        return QuizData(
            easiness = this.easiness,
            repetitions = this.repetitions,
            interval = this.interval,
            nextQuizDate = nextDate
        )
    }
}
