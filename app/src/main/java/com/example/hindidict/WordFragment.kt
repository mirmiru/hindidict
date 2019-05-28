package com.example.hindidict


import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData
import com.example.hindidict.viewmodel.MainViewModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.SnapshotParser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_word.*
import kotlinx.android.synthetic.main.sentence.view.*


// Firestore is not present here - view separate from repo
class WordFragment : Fragment() {
    // TODO Move WORD_ID to mainviewmodel
    lateinit var WORD_ID: String
    lateinit var mainViewModel: MainViewModel
    lateinit var liveData: WordLiveData
    lateinit var adapter: FirestoreSentenceRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_word, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadArguments()
        setUpRecyclerView()

        activity?.let {
            mainViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
            observeWord(mainViewModel)
        }

        button_add_sentence_to_word.setOnClickListener {
            //Open dialog
            AlertDialog.Builder(this.context)
                .setView(R.layout.fragment_add_sentence)
                .create()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_edit -> {
            val actionDetail = WordFragmentDirections.action_wordFragment_to_editWordFragment()
            actionDetail.setWord_id(WORD_ID)
            findNavController().navigate(actionDetail)

            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_menu_edit, menu)
    }

    private fun setUpRecyclerView() {
        val query = FirebaseFirestore.getInstance()
            .collection("sentences")
            .whereEqualTo("containsWord", WORD_ID)

        val options = FirestoreRecyclerOptions.Builder<Sentence>()
            .setQuery(query, object: SnapshotParser<Sentence> {
                override fun parseSnapshot(snapshot: DocumentSnapshot): Sentence {
                    val a = snapshot.data
                    return snapshot.toObject(Sentence::class.java)!!
                }
            })
            .build()

        adapter = FirestoreSentenceRecyclerAdapter(options)

        recyclerView_sentences_word.layoutManager = LinearLayoutManager(this.context)
        recyclerView_sentences_word.adapter = adapter

        adapter!!.startListening()
        adapter.notifyDataSetChanged()
    }

    private fun loadArguments() {
        arguments?.let {
            val safeArgs = WordFragmentArgs.fromBundle(it)
            WORD_ID = safeArgs.word_id
//            WORD_ID = safeArgs.word_id
        }
    }

    private fun observeWord(mainViewModel: MainViewModel) {
        liveData = mainViewModel.getWordLiveData(WORD_ID)
        liveData.observe(this, Observer<Word> { word ->
            if (word != null) {
                word.let {
                    textView_word_hindi.text = it.definition?.hindi
                    textView_word_eng.text = it.definition?.eng
                }
            }
        })
    }

    inner class SentenceHolder internal constructor(
        private val containerView: View
    ): RecyclerView.ViewHolder(containerView) {
        internal fun getData(s: Sentence) {
            containerView.textView_list_sentence_hindi.text = s.hindiSentence
            containerView.textView_list_sentence_eng.text = s.engSentence
        }
    }

    inner class FirestoreSentenceRecyclerAdapter internal constructor(
        options: FirestoreRecyclerOptions<Sentence>)
        : FirestoreRecyclerAdapter<Sentence, SentenceHolder>(options) {
        override fun onBindViewHolder(holder: SentenceHolder, position: Int, sentence: Sentence) {
            holder.getData(sentence)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentenceHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.sentence, parent, false)
            return SentenceHolder(view)
        }

    }

}
