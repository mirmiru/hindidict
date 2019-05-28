package com.example.hindidict


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.model.Sentence
import com.example.hindidict.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_add_sentence.*
import kotlinx.android.synthetic.main.fragment_add_word.*

class AddSentenceFragment : Fragment() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_sentence, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        button_dialog_add.setOnClickListener {
            addNewSentence()
        }

        button_dialog_cancel.setOnClickListener {
            // Return to Word fragment
        }
    }

    fun addNewSentence() {
        val sentence = Sentence(
            // GET WORDID AS BUNDLE FROM WORD FRAG
            containsWord = "WORD_ID",
            engSentence = editText_sentence_eng.text.toString(),
            hindiSentence = editText_sentence_hindi.text.toString()
        )

        if (sentence.hindiSentence.trim().isEmpty() || sentence.engSentence.trim().isEmpty()) {
            Toast.makeText(this.context, "Please insert a sentence pair.", Toast.LENGTH_SHORT).show()
            return
        }

        mainViewModel.addSentenceToWord(sentence, object : IEmptyCallback {
            override fun onCallback() {
                // Return to Word fragment
            }
        })
    }


}
