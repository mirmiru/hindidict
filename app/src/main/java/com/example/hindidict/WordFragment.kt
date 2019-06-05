package com.example.hindidict


import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hindidict.model.Sentence
import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData
import com.example.hindidict.viewmodel.MainViewModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.SnapshotParser
import com.example.hindidict.WordFragmentDirections.*
import com.example.hindidict.activity.MainActivity
import com.example.hindidict.fragment.BaseFragment
import com.example.hindidict.helper.ICallbackResult
import com.example.hindidict.viewmodel.WordViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_word.*
import kotlinx.android.synthetic.main.sentence.view.*


//class WordFragment : Fragment() {
class WordFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel
    lateinit var wordViewModel: WordViewModel

    // TODO Get ID from livedata - don't store data in fragment
    lateinit var WORD_ID: String
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
//        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.hide()
        adapter.stopListening()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadArguments()
        setUpRecyclerView()

//         TOOLBAR MENU TEST
        if(!(activity as MainActivity).isToolBarSetup) {
        setUpToolbar()
            (activity as MainActivity).isToolBarSetup = true
        }

        activity?.let {
            mainViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
            observeWord(mainViewModel)
            wordViewModel = ViewModelProviders.of(it).get(WordViewModel::class.java)
        }

        button_add_sentence_to_word.setOnClickListener {
            val navDirections = action_wordFragment_to_addSentenceFragment(WORD_ID)
            it.findNavController().navigate(navDirections)
        }
    }

    private fun setUpToolbar() {
        val toolbar = activity!!.findViewById<Toolbar>(R.id.top_toolbar)
        toolbar.inflateMenu(R.menu.action_menu_edit)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_edit -> {
                    val actionDetail = action_wordFragment_to_editWordFragment()
                    actionDetail.setWord_id(WORD_ID)
                    findNavController().navigate(actionDetail)
                    true
                }
                R.id.action_delete -> {
                    wordViewModel.deleteWord(WORD_ID, object : ICallbackResult {
                        override fun onCallbackResult(successful: Boolean) {
                            val message = when (successful) {
                                true -> "Deleted"
                                else -> "An error occurred"
                            }
                            Toast.makeText(this@WordFragment.context, message, Toast.LENGTH_SHORT).show()

                            findNavController().popBackStack(R.id.homeFragment, true)
                        }
                    })
                    true
                }
                else -> {
                    NavigationUI.onNavDestinationSelected(it, NavHostFragment.findNavController(this))
                    super.onOptionsItemSelected(it)
                }
            }
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
//        R.id.action_edit -> {
//            val actionDetail = action_wordFragment_to_editWordFragment()
//            actionDetail.setWord_id(WORD_ID)
//            findNavController().navigate(actionDetail)
//            true
//        }
//        R.id.action_delete -> {
//            wordViewModel.deleteWord(WORD_ID, object: ICallbackResult{
//                override fun onCallbackResult(successful: Boolean) {
//                    val message = when (successful) {
//                        true -> "Deleted"
//                        else -> "An error occurred"
//                    }
//                    Toast.makeText(this@WordFragment.context, message, Toast.LENGTH_SHORT).show()
//
//                    findNavController().popBackStack(R.id.homeFragment, true)
//                }
//            })
//            true
//        }
//        else -> {
//            NavigationUI.onNavDestinationSelected(item, NavHostFragment.findNavController(this))
//            super.onOptionsItemSelected(item)
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        menu.clear()
        inflater.inflate(R.menu.action_menu_edit, menu)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    private fun setUpRecyclerView() {
        val query = FirebaseFirestore.getInstance()
            .collection("sentences")
            .whereEqualTo("containsWord", WORD_ID)

        val options = FirestoreRecyclerOptions.Builder<Sentence>()
            .setQuery(query, object: SnapshotParser<Sentence> {
                override fun parseSnapshot(snapshot: DocumentSnapshot): Sentence {
                    return snapshot.toObject(Sentence::class.java)!!
                }
            })
            .build()

        adapter = FirestoreSentenceRecyclerAdapter(options)

        recyclerView_sentences_word.layoutManager = LinearLayoutManager(this.context)
        recyclerView_sentences_word.adapter = adapter

        adapter!!.startListening()
        adapter.notifyDataSetChanged()

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.deleteSentence(viewHolder.adapterPosition)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                val background = ColorDrawable(resources.getColor(R.color.colorAccent))
                background.setBounds(0, viewHolder.itemView.top, (viewHolder.itemView.left + dX).toInt(), viewHolder.itemView.bottom)
                background.draw(c)
            }
        }).attachToRecyclerView(recyclerView_sentences_word)
    }

    private fun loadArguments() {
        arguments?.let {
            val safeArgs = WordFragmentArgs.fromBundle(it)
            WORD_ID = safeArgs.word_id
        }
    }

    private fun observeWord(mainViewModel: MainViewModel) {
        liveData = mainViewModel.getWordLiveData(WORD_ID)
        liveData.observe(this, Observer<Word> { word ->
            if (word != null) {
                word.let {
                    textView_word_hindi.text = it.definition?.hindi
                    textView_word_eng.text = it.definition?.eng

                    textView_word_category.text = "--${it.category}"
                }
            }
        })
    }

    inner class SentenceHolder internal constructor(private val containerView: View): RecyclerView.ViewHolder(containerView) {

        internal fun getData(sentence: Sentence) {
            containerView.textView_list_sentence_hindi.text = sentence.hindiSentence
            containerView.textView_list_sentence_eng.text = sentence.engSentence

            containerView.setOnClickListener {
                val actionDetail = action_wordFragment_to_editSentenceFragment(sentence.sentenceId)
                findNavController().navigate(actionDetail)
            }
        }
    }

    inner class FirestoreSentenceRecyclerAdapter(options: FirestoreRecyclerOptions<Sentence>)
            : FirestoreRecyclerAdapter<Sentence, SentenceHolder>(options) {
        override fun onBindViewHolder(holder: SentenceHolder, position: Int, sentence: Sentence) {
            holder.getData(sentence)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentenceHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.sentence, parent, false)
            return SentenceHolder(view)
        }

        fun deleteSentence(position: Int) {
            snapshots.getSnapshot(position).reference.delete()
        }
    }

}
