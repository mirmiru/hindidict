package com.example.hindidict.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.example.hindidict.R
import com.example.hindidict.viewmodel.QuizViewModel
import kotlinx.android.synthetic.main.fragment_quiz_bottom.*

class QuizBottomFragment : Fragment() {

    private lateinit var viewModel: QuizViewModel
    private var isLastCard: Boolean = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(activity!!)
//            .get(QuizViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!)
            .get(QuizViewModel::class.java)

        viewModel.isLastCard.observe(this, Observer<Boolean> {
            if (it) {
                isLastCard = true
            }
        })

        button_quiz_0.setOnClickListener {
            setStudyDate(0)
        }
        button_quiz_1.setOnClickListener {
            setStudyDate(1)
        }
        button_quiz_2.setOnClickListener {
            setStudyDate(2)
        }
        button_quiz_3.setOnClickListener {
            setStudyDate(3)
        }
        button_quiz_4.setOnClickListener {
            setStudyDate(4)
        }
        button_quiz_5.setOnClickListener {
            setStudyDate(5)
        }
    }

    private fun setStudyDate(response: Int) {
        viewModel.setStudyDate(response)

        if (isLastCard) {
            val actionDetails = QuizHolderFragmentDirections.action_quizFragment_to_quizDoneFragment()
            findNavController().navigate(actionDetails)
        }
    }
}
