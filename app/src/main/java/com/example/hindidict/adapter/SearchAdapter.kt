package com.example.hindidict.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hindidict.R
import com.example.hindidict.fragment.HomeFragmentDirections
import com.example.hindidict.helper.ICallbackWord
import com.example.hindidict.model.Word
import com.example.hindidict.repo.FirestoreRepository
import kotlinx.android.synthetic.main.word.view.*
import kotlinx.android.synthetic.main.word_search.view.*

class SearchAdapter(
    private var resultsList: MutableList<String>
): RecyclerView.Adapter<SearchAdapter.ResultHolder>() {

    inner class ResultHolder(
        private val containerView: View
    ): RecyclerView.ViewHolder(containerView) {
        fun getData(word: String) {
            containerView.textView_listItem_word.text = word

            containerView.setOnClickListener {
                val repository = FirestoreRepository()
                repository.getWordId(word, object: ICallbackWord {
                    override fun onCallbackWord(word: Word) {
                        val navDirections = HomeFragmentDirections.action_homeFragment_to_wordFragment(word.uuid)
                        containerView.findNavController().navigate(navDirections)
                    }
                })

            }
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