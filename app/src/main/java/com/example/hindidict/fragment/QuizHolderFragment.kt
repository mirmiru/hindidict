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

class QuizHolderFragment : Fragment() {

    private lateinit var viewModel: QuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz_holder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(QuizViewModel::class.java)

        // Load card set
//        viewModel.getCardSet()

        // Cards due today
        viewModel.getCardsDueToday()

        // Get current card
        viewModel.currentCard.observe(this, Observer<Word>{ word ->
            cardview_card_front_mainText.text = word.definition?.hindi
            cardview_card_back_mainText.text = word.definition?.eng
        })
    }


}
