package com.example.hindidict.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.hindidict.R
import com.example.hindidict.adapter.WordListAdapter
import com.example.hindidict.model.Word
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.SnapshotParser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_list_eng_to_hindi.*

class ListEngToHindiFragment : Fragment() {

    lateinit var listAdapter: WordListAdapter

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
            .collection("words")

        val options = FirestoreRecyclerOptions.Builder<Word>()
            .setQuery(query, object: SnapshotParser<Word> {
                override fun parseSnapshot(snapshot: DocumentSnapshot): Word {
                    return snapshot.toObject(Word::class.java)!!
                }
            })
            .build()

        listAdapter = WordListAdapter(options)

        recyclerView_list.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = listAdapter
        }

        listAdapter.apply {
            startListening()
            notifyDataSetChanged()
        }
    }

}
