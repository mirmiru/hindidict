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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.let {
            val mainViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)

            observeWord(mainViewModel)
        }
    }

    private fun observeWord(mainViewModel: MainViewModel) {
        /*mainViewModel.getWordLiveData(TEST_DOC_ID).observe(this, Observer {
            it?.let {
                //textView_word_hindi.text = it.wordHindi
                var a = it.wordHindi
            }
        })*/

        val liveData = mainViewModel.getWordLiveData(TEST_DOC_ID)
        liveData.observe(this, Observer<Word> { word ->
            if (word != null) {
                textView_word_hindi.text = word.wordHindi
            }
        })

    }


}
