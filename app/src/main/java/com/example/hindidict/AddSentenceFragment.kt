package com.example.hindidict


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.model.Sentence
import com.example.hindidict.viewmodel.MainViewModel
import com.example.hindidict.AddSentenceFragmentArgs.fromBundle
import com.example.hindidict.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_sentence.*

class AddSentenceFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel
    private val WORD_ID by lazy {
        fromBundle(arguments).word_id
    }

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
            findNavController().popBackStack()
        }
    }

    fun addNewSentence() {
        val sentence = Sentence(
            containsWord = WORD_ID,
            engSentence =   editText_add_sentence_eng.text.toString(),
            hindiSentence = editText_add_sentence_hindi.text.toString()
        )

        if (sentence.hindiSentence.trim().isEmpty() || sentence.engSentence.trim().isEmpty()) {
            Toast.makeText(this.context, "Please insert a sentence pair.", Toast.LENGTH_SHORT).show()
            return
        }

        mainViewModel.addSentenceToWord(sentence, object : IEmptyCallback {
            override fun onCallback() {
                findNavController().popBackStack()
            }
        })
    }


}
