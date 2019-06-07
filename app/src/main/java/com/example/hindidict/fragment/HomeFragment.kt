package com.example.hindidict.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hindidict.R
import com.example.hindidict.adapter.SearchAdapter
import com.example.hindidict.helper.ICallbackWord
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.model.Word
import com.example.hindidict.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var results: RecyclerView
    private lateinit var list: List<Word>
    private var resultsList = mutableListOf<String>()

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

        results = activity!!.findViewById(R.id.recyclerView_search)
        results.layoutManager = LinearLayoutManager(this.context)
        results.adapter = SearchAdapter(resultsList)

        viewModel.getAllData(object : IEmptyCallback{
            override fun onCallback() {
                list = viewModel.getAllWords()
            }
        })
//        recyclerView_search.addOnLayoutChangeListener(object : View.OnLayoutChangeListener{
//            override fun onLayoutChange(
//                v: View?,
//                left: Int,
//                top: Int,
//                right: Int,
//                bottom: Int,
//                oldLeft: Int,
//                oldTop: Int,
//                oldRight: Int,
//                oldBottom: Int
//            ) {
//                recyclerView_search.scrollToPosition(results.size)
//            }
//        })

        search()

        viewModel.getWordOfTheDay(object: ICallbackWord{
            override fun onCallbackWord(word: Word) {
                textView_home_word_of_the_day_hindi.text = word.definition.hindi
                textView_home_word_of_the_day_eng.text = word.definition.eng
                textView_home_word_of_the_day_category.text = word.category
            }
        })
        viewModel.getWordCount().observe(this, Observer { count ->
            when (count) {
                0 -> {
                    textView_home_word_count.isVisible = false
                    cardview_home_cards.isClickable = false
                }
                else -> {
                    textView_home_word_count.apply {
                        isVisible = true
                        text = count.toString()
                    }
                    cardview_home_cards.isClickable = true
                }
            }
        })

        cardview_home_search.setOnClickListener {
            search_bar.isVisible = true
            recyclerView_search.isVisible = true
        }

        cardview_home_cards.setOnClickListener {
            val navDirections = HomeFragmentDirections.action_homeFragment_to_quizFragment()
            navDirections.setQuiz_type("REVIEW")
            findNavController().navigate(navDirections)
        }
    }

    private fun search() {
        search_bar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty()) {
                    resultsList.clear()
                    val search = newText.toLowerCase()
                    list.forEach {word ->
                        if (word.definition.eng.toLowerCase().contains(search)) {
                            resultsList.add(word.definition.eng)
                        } else if (word.definition.hindi.toLowerCase().contains(search)) {
                            resultsList.add(word.definition.hindi)
                        }
                    }
                    results.adapter!!.notifyDataSetChanged()
                } else {
                    resultsList.clear()
                    results.adapter!!.notifyDataSetChanged()
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                search_bar.clearFocus()
                return true
            }
        })
    }


}
