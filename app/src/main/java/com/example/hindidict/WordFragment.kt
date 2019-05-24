package com.example.hindidict


import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData
import com.example.hindidict.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_word.*


// Firestore is not present here - view separate from repo
class WordFragment : Fragment() {
    lateinit var WORD_ID: String
    lateinit var mainViewModel: MainViewModel
//    lateinit var currentWord: Word
    lateinit var liveData: WordLiveData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadArguments()

        activity?.let {
            mainViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)

            observeWord(mainViewModel)

            val navController = findNavController()
            Navigation.setViewNavController(fab_navigate_to_edit_word, navController)
            fab_navigate_to_edit_word.setOnClickListener {
                val actionDetail = WordFragmentDirections.action_wordFragment_to_editWordFragment()
                actionDetail.setWord_id(WORD_ID)
                findNavController().navigate(actionDetail)
//                navController.navigate(R.id.editWordFragment)
            }
        }
    }

    private fun loadArguments() {
        arguments?.let {
            val safeArgs = WordFragmentArgs.fromBundle(it)
            WORD_ID = safeArgs.word_id
        }
    }

    private fun observeWord(mainViewModel: MainViewModel) {
        liveData = mainViewModel.getWordLiveData(WORD_ID)

        liveData.observe(this, Observer<Word> { word ->
            if (word != null) {
                word.let {
                    textView_word_hindi.text = it.definition?.hindi
                    textView_word_eng.text = it.definition?.eng
                    word.sentences[0].let {
                        textView_sentence_hindi.text = it.hindiSentence
                        textView_sentence_eng.text = it.engSentence

                        // TODO Use for ordering sentences NEW -> OLD?
                        val date = it.dateCreated?.toDate()
                    }
                }
            }
//            currentWord = word
        })
    }


}
