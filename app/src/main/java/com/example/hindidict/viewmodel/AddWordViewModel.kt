package com.example.hindidict.viewmodel

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.ViewModel

class AddWordViewModel : ViewModel(), AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun populateSpinner(): List<String> {
        return listOf<String>("Noun", "Verb", "Adjective", "Adverb")
    }

}