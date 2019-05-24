package com.example.hindidict


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.hindidict.model.Definition
import com.example.hindidict.model.Word
import com.example.hindidict.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_edit_word.*

class EditWordFragment : DialogFragment () {

    lateinit var uneditedWord: Word
    lateinit var WORD_ID: String
    lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        loadArguments()
    }

    private fun loadArguments() {
        arguments?.let {
            val safeArgs = EditWordFragmentArgs.fromBundle(it)
            WORD_ID = safeArgs.word_id

            fillViews(WORD_ID)
        }
    }

    // TODO Do not use livedata when editing word
    private fun fillViews(uuid: String) {
        val liveData = mainViewModel.getWordLiveData(uuid)
        liveData.observe(this, Observer<Word> { word ->
            if (word != null) {
                word.let {
                    val def = Definition(
                        hindi = it.definition!!.hindi,
                        eng = it.definition!!.eng
                    )
                    editText_edit_word_hindi.setText(def.hindi)
                    editText_edit_word_eng.setText(def.eng)

                    editText_edit_sentence_hindi.setText(it.sentences[0].hindiSentence)
                    editText_edit_sentence_eng.setText(it.sentences[0].engSentence)

                    // TODO Category spinner
                }
            }
        })
    }
}
