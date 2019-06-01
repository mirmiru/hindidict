package com.example.hindidict


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.hindidict.helper.ICallback
import com.example.hindidict.model.Definition
import com.example.hindidict.model.QuizData
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.Word
import com.example.hindidict.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_add_word.*

class AddWordFragment : Fragment() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.let {
            mainViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        }

        fab_add_new_word.setOnClickListener {
            addWord()
        }
    }

    private fun addWord() {
        val word = Word(
            category = "adjective",
            definition = Definition(
                eng = editText_word_eng.text.toString(),
                hindi = editText_word_hindi.text.toString()
            ),
            difficult = false,
            quizData = QuizData()
        )

        mainViewModel.addWord(word, object : ICallback {
            override fun onCallback(uuid: String) {
                addSentence(uuid)
            }
        })
    }

    private fun addSentence(wordId: String) {
        val sentence = Sentence(
            containsWord = wordId,
            engSentence = editText_sentence_eng.text.toString(),
            hindiSentence = editText_sentence_hindi.text.toString()
        )
        mainViewModel.addSentence(sentence, object : ICallback {
            override fun onCallback(uuid: String) {
                val actionDetail = AddWordFragmentDirections.action_addWordFragment_to_wordFragment2(uuid)
                findNavController().navigate(actionDetail)
            }
        })
    }

}
