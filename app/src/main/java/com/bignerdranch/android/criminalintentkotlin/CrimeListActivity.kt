package com.bignerdranch.android.criminalintentkotlin

import android.support.v4.app.Fragment

/**
 * Created by mateusz on 11.02.18.
 */
class CrimeListActivity:SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return CrimeListFragment()
    }
}