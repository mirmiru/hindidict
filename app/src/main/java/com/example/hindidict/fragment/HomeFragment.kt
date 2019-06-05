package com.example.hindidict.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hindidict.R
import com.example.hindidict.adapter.SearchAdapter
import com.example.hindidict.helper.ICallbackWord
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.model.Word
import com.example.hindidict.viewmodel.MainViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.SnapshotParser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    lateinit var results: RecyclerView
    lateinit var list: List<Word>
    private var resultsList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // SEARCG
        results = activity!!.findViewById(R.id.recyclerView_search)
        results.layoutManager = LinearLayoutManager(this.context)
        results.adapter = SearchAdapter(resultsList)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getWordsDueCount()

        // serch
        viewModel.getAllData(object : IEmptyCallback{
            override fun onCallback() {
                list = viewModel.getAllWords()
            }
        })


        // SEARCG
        search()

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

    fun search() {
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
                return true
            }
        })

    }


}
