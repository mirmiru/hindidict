package com.example.hindidict.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hindidict.fragment.ListHolderFragmentDirections
import com.example.hindidict.viewmodel.MainViewModel
import com.example.hindidict.R
import com.example.hindidict.fragment.ListEngToHindiFragment
import com.example.hindidict.fragment.ListHolderFragment
import com.example.hindidict.helper.IEmptyCallback
import com.example.hindidict.model.Word
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.word.view.*

class WordListEnglishAdapter(
    options: FirestoreRecyclerOptions<Word>,
    viewModel: MainViewModel
): FirestoreRecyclerAdapter<Word, WordListEnglishAdapter.WordHolder>(options) {
    private val viewModel = viewModel

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
                var a = it.context

                it.button_star.isActivated = !it.button_star.isActivated
                when (it.button_star.isActivated) {
                    true -> viewModel.addWordToFavorites(word.uuid, object : IEmptyCallback{
                        override fun onCallback() {
                            Toast
                                .makeText(it.context, "Added to favorites", Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
                    else -> ""
                    // TODO Remove from favorites

                }
            }
        }

    }
}