package com.example.hindidict


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.hindidict.model.Definition
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.Word
import com.example.hindidict.viewmodel.ICallback
import com.example.hindidict.viewmodel.MainViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import kotlinx.android.synthetic.main.fragment_add_word.*
import java.util.*

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

    // TODO Fix category
    // TODO Sentences needs to take in all sentences in list / add to existing list
    private fun addWord() {
        val word = Word(
            definition = Definition(
                eng = editText_word_eng.text.toString(),
                hindi = editText_word_hindi.text.toString()
            ),
            category = "nullForNow",
            isDifficult = false,
            sentences = mutableListOf(
                Sentence(
                    contains = mutableListOf(editText_word_hindi.text.toString()),
                    engSentence = editText_sentence_eng.text.toString(),
                    hindiSentence = editText_sentence_hindi.text.toString(),
                    dateCreated = Timestamp(Date())
                )
            )
        )
        mainViewModel.addWord(word, object : ICallback {
            override fun onCallback(uuid: String) {
                if (uuid != null) {
                    var a = uuid

                    val navController = findNavController()
                    val action = AddWordFragmentDirections.action_addWordFragment_to_wordFragment2(uuid)
                    findNavController().navigate(action)
                }
            }
        })
    }

}
