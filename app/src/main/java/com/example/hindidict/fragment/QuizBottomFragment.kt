package com.example.hindidict.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.hindidict.R
import com.example.hindidict.model.Word
import com.example.hindidict.viewmodel.QuizViewModel
import kotlinx.android.synthetic.main.fragment_quiz_bottom.*

class QuizBottomFragment : Fragment() {

    private lateinit var viewModel: QuizViewModel
    private var canFlip = MutableLiveData<Boolean>()
    private lateinit var currentCard: Word

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!)
            .get(QuizViewModel::class.java)
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

        viewModel.isLastCard.observe(this, Observer {
            canFlip.postValue(!it)
        })

        button_quiz_5.setOnClickListener {
//            viewModel.getNextCard()
            setStudyDate(5)
        }
    }

    // User rates card 0-5 -> get next study date
    private fun setStudyDate(response: Int) {
        viewModel.setStudyDate(response)
    }


}
