package com.example.hindidict.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.hindidict.R
import com.example.hindidict.helper.ICallbackWord
import com.example.hindidict.model.Word
import com.example.hindidict.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getWordsDueCount()

        viewModel.getWordOfTheDay(object: ICallbackWord{
            override fun onCallbackWord(word: Word) {
                textView_home_word_of_the_day_hindi.text = word.definition.hindi
                textView_home_word_of_the_day_eng.text = word.definition.eng

            }
        })
        viewModel.getWordCount().observe(this, Observer { count ->
            textView_home_word_count.text = "${count.toString()} words due."
        })
    }


}
