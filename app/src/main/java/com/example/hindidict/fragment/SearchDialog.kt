package com.example.hindidict.fragment


import android.app.Dialog
import android.os.Bundle
import android.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.hindidict.R
import com.example.hindidict.adapter.SearchAdapter
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.model.Word
import com.example.hindidict.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_search_dialog.*

class SearchDialog : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var results: RecyclerView
    private lateinit var list: List<Word>
    private var resultsList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getWordsDueCount()

        results = activity!!.findViewById(R.id.recyclerView_search)!!

        results.layoutManager = LinearLayoutManager(this.activity)
        results.adapter = SearchAdapter(resultsList)

        viewModel.getAllData(object : IEmptyCallback {
            override fun onCallback() {
                list = viewModel.getAllWords()
            }
        })
        search()
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
