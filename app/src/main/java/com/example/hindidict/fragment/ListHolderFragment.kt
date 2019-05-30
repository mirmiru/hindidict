package com.example.hindidict.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager

import com.example.hindidict.R
import com.example.hindidict.adapter.TabsPagerAdapter
import kotlinx.android.synthetic.main.fragment_list_holder.*

class ListHolderFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabsPagerAdapter: TabsPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_holder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.viewPager_listHolder)

        tabsPagerAdapter = TabsPagerAdapter(childFragmentManager)
        viewPager.adapter = tabsPagerAdapter
        tabs_listHolder.setupWithViewPager(viewPager)
    }

}
