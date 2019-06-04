package com.example.hindidict


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.hindidict.fragment.BaseFragment
import com.example.hindidict.helper.ICallback
import com.example.hindidict.model.Definition
import com.example.hindidict.model.QuizData
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.Word
import com.example.hindidict.viewmodel.WordViewModel
import com.example.hindidict.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_add_word.*

//class AddWordFragment : Fragment() {
class AddWordFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel
    lateinit var viewModel: WordViewModel
    private var word = Word()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.let {
            mainViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
            viewModel = ViewModelProviders.of(it).get(WordViewModel::class.java)
        }

        fab_add_new_word.setOnClickListener {
            addWord()
        }

        setUpSpinner()
    }

    private fun setUpSpinner() {
        val arrayAdapter = ArrayAdapter<String>(
            this.context!!,
            android.R.layout.simple_spinner_dropdown_item,
            viewModel.populateSpinner()
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_word_category.adapter = arrayAdapter

        spinner_word_category.onItemSelectedListener

        spinner_word_category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                word.category = viewModel.getCategory(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

    private fun addWord() {
        val word = Word(
            category = word.category,
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
