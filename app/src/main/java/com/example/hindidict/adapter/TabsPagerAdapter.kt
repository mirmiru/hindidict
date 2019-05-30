package com.example.hindidict.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.hindidict.fragment.ListEngToHindiFragment
import com.example.hindidict.fragment.ListHindiToEngFragment

class TabsPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ListHindiToEngFragment()
            else -> ListEngToHindiFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Hindi -> Eng"
            else -> "Eng -> Hindi"
        }
    }

}