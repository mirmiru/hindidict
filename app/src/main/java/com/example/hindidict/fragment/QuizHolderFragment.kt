package com.example.hindidict.fragment


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.example.hindidict.R
import com.example.hindidict.viewmodel.QuizViewModel

class QuizHolderFragment : Fragment() {

    private lateinit var viewModel: QuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz_holder, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_menu_empty, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuizViewModel::class.java)

        arguments.let {
            val type = QuizHolderFragmentArgs.fromBundle(it).quiz_type
            when (type) {
//                "ALL"-> viewModel.getCardSet()
                "REVIEW" -> viewModel.getCardsDueToday()
                else -> finishQuiz()
            }
        }

//        viewModel.getCardsDueToday()

        viewModel.getNoCardsDue().observe(this, Observer { noCardsDue ->
            if (noCardsDue) {
                finishQuiz()
            }
        })
    }

    private fun finishQuiz() {
        findNavController().navigate(R.id.quizDoneFragment)
    }
}
