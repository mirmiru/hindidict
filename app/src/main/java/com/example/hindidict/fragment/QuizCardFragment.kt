package com.example.hindidict.fragment


import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible

import com.example.hindidict.R
import kotlinx.android.synthetic.main.fragment_quiz_bottom.*
import kotlinx.android.synthetic.main.fragment_quiz_card.*
import kotlinx.android.synthetic.main.fragment_quiz_card.view.*

class QuizCardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_quiz_answer.setOnClickListener {
            showAnswer()
        }
    }

    private fun showAnswer() {
        easyFlipView.flipTheView()
        button_quiz_answer.isEnabled = false
    }

}
