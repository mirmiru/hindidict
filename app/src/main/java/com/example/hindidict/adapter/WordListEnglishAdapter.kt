package com.example.hindidict.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hindidict.fragment.ListHolderFragmentDirections
import com.example.hindidict.R
import com.example.hindidict.model.Word
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.word.view.*

class WordListEnglishAdapter(
    options: FirestoreRecyclerOptions<Word>
): FirestoreRecyclerAdapter<Word, WordListEnglishAdapter.WordHolder>(options) {
    override fun onBindViewHolder(holder: WordHolder, position: Int, word: Word) {
        holder.getData(word)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word, parent, false)
        return WordHolder(view)
    }

    inner class WordHolder(
        private val containerView: View
    ): RecyclerView.ViewHolder(containerView) {

        fun getData(word: Word) {
            containerView.textView_listItem_word.text = word.definition?.eng

            containerView.setOnClickListener {
                val actionDetails = ListHolderFragmentDirections
                    .action_listHolderFragment_to_wordFragment(word.uuid)
                findNavController(it).navigate(actionDetails)
            }

            containerView.button_star.setOnClickListener {
                it.button_star.isActivated = !it.button_star.isActivated
            }
        }

    }
}