package com.example.hindidict.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.hindidict.R
import com.example.hindidict.adapter.WordListEnglishAdapter
import com.example.hindidict.model.Word
import com.example.hindidict.viewmodel.MainViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.SnapshotParser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.l4digital.fastscroll.FastScrollRecyclerView
import kotlinx.android.synthetic.main.fragment_list_eng_to_hindi.*

class ListEngToHindiFragment : Fragment() {

    lateinit var listAdapter: WordListEnglishAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_eng_to_hindi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        listAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        listAdapter.stopListening()
    }

    private fun setUpRecyclerView() {
        val query = FirebaseFirestore.getInstance()
//            .collection("words")
            .collection("wordsfinal")
            .orderBy("definition.eng")

        val options = FirestoreRecyclerOptions.Builder<Word>()
            .setQuery(query, object: SnapshotParser<Word> {
                override fun parseSnapshot(snapshot: DocumentSnapshot): Word {
                    return snapshot.toObject(Word::class.java)!!
                }
            })
            .build()

        // TEST
        val recyclerView: FastScrollRecyclerView? = view?.findViewById(R.id.recyclerView_list_english)
        recyclerView?.layoutManager = LinearLayoutManager(this.context)
        listAdapter = WordListEnglishAdapter(
            options, ViewModelProviders.of(this).get(MainViewModel::class.java)
        )
        recyclerView?.adapter = listAdapter

        listAdapter.startListening()


//        listAdapter = WordListEnglishAdapter(options, ViewModelProviders.of(this).get(MainViewModel::class.java))
//        listAdapter.startListening
//        listAdapter.notifyDataSetChanged()

        //TEST

//        listAdapter = WordListEnglishAdapter(
//            options, ViewModelProviders.of(this).get(MainViewModel::class.java)
//        )
//
//        recyclerView_list_english.apply {
//            layoutManager = LinearLayoutManager(this.context)
//            adapter = listAdapter
//        }

//        listAdapter.apply {
//            startListening()
//            notifyDataSetChanged()
//        }
    }
}
