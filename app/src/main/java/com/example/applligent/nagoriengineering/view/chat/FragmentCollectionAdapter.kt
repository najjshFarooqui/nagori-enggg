package com.example.applligent.nagoriengineering.view.chat

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class FragmentCollectionAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = Frag1()
            1 -> fragment = Frag2()
            2 -> fragment = Frag3()
        }

        return fragment
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var position = position
        position = position + 1

        return if (position == 1) {
            "Chats"
        } else if (position == 2) {
            "Photos"
        } else {
            "Don't Know"
        }
    }
}
