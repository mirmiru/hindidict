package com.example.hindidict.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.hindidict.R
import com.example.hindidict.model.Word
import com.example.hindidict.viewmodel.QuizViewModel
import kotlinx.android.synthetic.main.fragment_quiz_card.*

class QuizCardFragment : Fragment() {

    private lateinit var viewModel: QuizViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!)
            .get(QuizViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!)
            .get(QuizViewModel::class.java)
            .also {
                it.getCardSet()
                // TODO Remove this test
                it.getCardsDueToday()
            }

        viewModel.currentCard.observe(this, Observer<Word> {
            if (easyFlipView.isBackSide) {
                cardview_card_front_mainText.text = it?.definition?.eng
                cardview_card_back_mainText.text = it?.definition?.hindi
            } else {
                cardview_card_front_mainText.text = it?.definition?.hindi
                cardview_card_back_mainText.text = it?.definition?.eng
            }
            button_quiz_answer.isEnabled = true
        })

        button_quiz_answer.setOnClickListener {
            showAnswer()
        }
    }

    private fun showAnswer() {
        easyFlipView.flipTheView()
        button_quiz_answer.isEnabled = false
    }

}
