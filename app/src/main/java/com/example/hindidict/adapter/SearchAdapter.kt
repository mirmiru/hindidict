package com.example.hindidict.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hindidict.R
import com.example.hindidict.model.Word
import kotlinx.android.synthetic.main.word.view.*

class SearchAdapter(
    private var resultsList: MutableList<String>
): RecyclerView.Adapter<SearchAdapter.ResultHolder>() {

    inner class ResultHolder(
        private val containerView: View
    ): RecyclerView.ViewHolder(containerView) {
        fun getData(word: String) {
            containerView.textView_listItem_word.text = word

        }
    }

    override fun onBindViewHolder(holder: ResultHolder, position: Int) {
        val word = resultsList.get(position)
        holder.getData(word)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word, parent, false)
        val vHolder = ResultHolder(view)
        return vHolder
    }

    override fun getItemCount(): Int {
        return resultsList.size
    }
}