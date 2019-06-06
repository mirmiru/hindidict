package com.example.hindidict.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.hindidict.fragment.EditSentenceFragmentArgs
import com.example.hindidict.R
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.SentenceLiveData
import com.example.hindidict.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_edit_sentence.*

class EditSentenceFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var SENTENCE_ID: String
    private lateinit var WORD_ID: String
    private lateinit var sentenceLiveData: SentenceLiveData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_sentence, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadArguments()

        activity.let {
            viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
            observeWord(viewModel)
        }

        fab_update_sentence.setOnClickListener {
            updateSentence()
        }
    }

    private fun loadArguments() {
        arguments?.let {
            SENTENCE_ID = EditSentenceFragmentArgs.fromBundle(it).sentence_id
        }
    }

    private fun observeWord(viewModel: MainViewModel) {
        sentenceLiveData = viewModel.getSentence(SENTENCE_ID)
        sentenceLiveData.observe(this, Observer<Sentence> { sentence ->
            if (sentence != null) {
                sentence.let {
                    WORD_ID = it.containsWord
                    editText_edit_sentence_hindi.setText(it.hindiSentence)
                    editText_edit_sentence_eng.setText(it.engSentence)
                }
            }
        })
    }

    private fun updateSentence() {
        if (editText_edit_sentence_hindi.text.trim().isEmpty() || editText_edit_sentence_eng.text.trim().isEmpty()) {
            Toast.makeText(this.context, "Please enter a sentence", Toast.LENGTH_SHORT).show()
            return
        }

        val sentence = Sentence(
            sentenceId = SENTENCE_ID,
            containsWord = WORD_ID,
            engSentence = editText_edit_sentence_eng.text.toString(),
            hindiSentence = editText_edit_sentence_hindi.text.toString()
        )
        viewModel.updateSentence(sentence, object : IEmptyCallback {
           override fun onCallback() {
               findNavController().popBackStack()
           }
        })
    }
}
