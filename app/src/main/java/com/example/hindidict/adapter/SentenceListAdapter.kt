package com.example.hindidict.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hindidict.R
import com.example.hindidict.model.Sentence
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.extensions.LayoutContainer
//
//class FirestoreSentenceRecyclerAdapter(options: FirestoreRecyclerOptions<Sentence>): FirestoreRecyclerAdapter<Sentence, FirestoreSentenceRecyclerAdapter.ViewHolder>(options) {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.sentence, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int, sentence: Sentence) {
//        holder.apply {
//            val a = sentence.engSentence
//            val h = sentence.hindiSentence
//        }
//    }
//    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer
//}
