package com.example.hindidict


import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.hindidict.model.Word
import com.example.hindidict.model.WordLiveData
import com.example.hindidict.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_word.*


// Firestore is not present here - view separate from repo
class WordFragment : Fragment() {
    // TODO Move WORD_ID to mainviewmodel
    lateinit var WORD_ID: String
    lateinit var mainViewModel: MainViewModel
    lateinit var liveData: WordLiveData

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadArguments()

        activity?.let {
            mainViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)

            observeWord(mainViewModel)

//            val navController = findNavController()
//            Navigation.setViewNavController(fab_navigate_to_edit_word, navController)
//            fab_navigate_to_edit_word.setOnClickListener {
//                val actionDetail = WordFragmentDirections.action_wordFragment_to_editWordFragment()
//                actionDetail.setWord_id(WORD_ID)
//                findNavController().navigate(actionDetail)
//            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_edit -> {
//            val navController = findNavController()
//            Navigation.setViewNavController(fab_navigate_to_edit_word, navController)
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
//        super.onCreateOptionsMenu(menu, inflater)
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
                    word.sentences[0].let {
                        textView_sentence_hindi.text = it.hindiSentence
                        textView_sentence_eng.text = it.engSentence

                        // TODO Use for ordering sentences NEW -> OLD?
//                        val date = it.dateCreated?.toDate()
                    }
                }
            }
        })
    }
}
