package com.example.hindidict.fragment

import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.hindidict.R

open class BaseFragment : Fragment() {

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_menu_empty, menu)
    }

    override fun onDestroyView() {
        hideKeyboard(view!!)
        super.onDestroyView()
    }

    private fun hideKeyboard(view: View) {
        if (view != null) {
            val manager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}