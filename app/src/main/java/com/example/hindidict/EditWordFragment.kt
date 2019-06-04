package com.example.hindidict


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.hindidict.helper.ICallback
import com.example.hindidict.helper.ICallbackResult
import com.example.hindidict.model.Definition
import com.example.hindidict.model.Word
import com.example.hindidict.viewmodel.MainViewModel
import com.example.hindidict.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.fragment_edit_word.*

class EditWordFragment : DialogFragment () {

    lateinit var WORD_ID: String
    lateinit var mainViewModel: MainViewModel
    lateinit var wordViewModel: WordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
        loadArguments()

        fab_update_word.setOnClickListener {
            val updatedWord = createWord()
//            mainViewModel.updateWord(updatedWord, object: ICallback {
////                override fun onCallback(uuid: String) {
////                    if (uuid != null) {
////                        findNavController().popBackStack(R.id.wordFragment, true)
////                    }
////                }
////            })
            wordViewModel.updateWord(updatedWord, object: ICallbackResult {
                override fun onCallbackResult(successful: Boolean) {
                    val message = when (successful) {
                        true -> "Updated word"
                        else -> "An error occurred"
                    }
                    Toast.makeText(this@EditWordFragment.context, message, Toast.LENGTH_SHORT).show()

                    findNavController().popBackStack()
                }
            })
        }
    }

    private fun loadArguments() {
        arguments?.let {
            val safeArgs = EditWordFragmentArgs.fromBundle(it)
            WORD_ID = safeArgs.word_id

            fillViews(WORD_ID)
        }
    }

    private fun createWord(): Word {
        val word = Word(
            uuid = WORD_ID,
            definition = Definition(
                eng = editText_edit_word_eng.text.toString(),
                hindi = editText_edit_word_hindi.text.toString()
            ),
            category = "nullForNow",
            difficult = false
        )
        return word
    }

    private fun fillViews(uuid: String) {
        val liveData = mainViewModel.getWordLiveData(uuid)
        liveData.observe(this, Observer<Word> { word ->
            if (word != null) {
                word.let {
                    val def = Definition(
                        hindi = it.definition!!.hindi,
                        eng = it.definition!!.eng
                    )
                    editText_edit_word_hindi.setText(def.hindi)
                    editText_edit_word_eng.setText(def.eng)
                    // TODO Category spinner
                }
            }
        })
    }
}
