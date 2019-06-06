package com.example.hindidict.fragment


import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.hindidict.fragment.EditWordFragmentArgs
import com.example.hindidict.R
import com.example.hindidict.helper.ICallbackResult
import com.example.hindidict.model.Definition
import com.example.hindidict.model.Word
import com.example.hindidict.viewmodel.MainViewModel
import com.example.hindidict.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.fragment_edit_word.*

//class EditWordFragment : DialogFragment () {
class EditWordFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel
    lateinit var wordViewModel: WordViewModel
    private var updatedWord = Word()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_word, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)

        setUpSpinner()
        loadArguments()

        fab_update_word.setOnClickListener {
            val updatedWord = createWord()
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
            fillViews(safeArgs.word_id)
        }
    }

    private fun setUpSpinner() {
        val arrayAdapter = ArrayAdapter<String>(
            this.context,
            android.R.layout.simple_spinner_dropdown_item,
            wordViewModel.populateSpinner()
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_edit_word_category.apply {
            adapter = arrayAdapter
            this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    updatedWord.category = wordViewModel.getCategory(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // TODO Implement
                }
            }
        }
    }

    private fun createWord(): Word {
        return Word(
            uuid = updatedWord.uuid,
            definition = Definition(
                eng = editText_edit_word_eng.text.toString(),
                hindi = editText_edit_word_hindi.text.toString()
            ),
            category = updatedWord.category,
            difficult = updatedWord.difficult
        )
    }

    private fun fillViews(uuid: String) {
        val liveData = mainViewModel.getWordLiveData(uuid)
        liveData.observe(this, Observer<Word> { word ->
            if (word != null) {
                updatedWord = word
                updatedWord.let {
                    val def = Definition(
                        hindi = it.definition!!.hindi,
                        eng = it.definition!!.eng
                    )
                    editText_edit_word_hindi.setText(def.hindi)
                    editText_edit_word_eng.setText(def.eng)
                }
                spinner_edit_word_category.setSelection(
                        wordViewModel.getSpinnerPosition(updatedWord.category)
                    )
            }
        })
    }
}
