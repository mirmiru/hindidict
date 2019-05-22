package com.example.hindidict


import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.hindidict.model.Word
import com.example.hindidict.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_word.*


// Firestore is not present here - view separate from repo
class WordFragment : Fragment() {
    private val TEST_DOC_ID = "K9AJxbyJTlBhAnwABRwD"
    private val TEST_DOC_ID_ERROR = "XXX"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.let {
            val mainViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)

            observeWord(mainViewModel)
        }
    }

    private fun observeWord(mainViewModel: MainViewModel) {
        val liveData = mainViewModel.getWordLiveData(TEST_DOC_ID)

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
        })

    }


}
