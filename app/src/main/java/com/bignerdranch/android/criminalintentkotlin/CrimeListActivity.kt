package com.bignerdranch.android.criminalintentkotlin

import android.support.v4.app.Fragment
import android.view.View

/**
 * Created by mateusz on 11.02.18.
 */
class CrimeListActivity:SingleFragmentActivity(),CrimeListFragment.Callbacks, CrimeFragment.Callbacks {
    override fun onCrimeUpdated(crime: Crime) {
        var listFragment=supportFragmentManager.findFragmentById(R.id.fragment_container) as CrimeListFragment
        listFragment.updateUI()
    }

    private  lateinit var selectedCrime:Crime
    override fun onCrimeSelected(crime: Crime) {
        selectedCrime=crime
        if(findViewById<View>(R.id.detail_fragment_container)==null){
            val intent=CrimePagerActivity.newIntent(this, crime.mId!!)
            startActivity(intent)
        }else{
            val newDetail=CrimeFragment.newInstance(crime.mId!!)
            supportFragmentManager.beginTransaction().add(R.id.detail_fragment_container,newDetail).commit()
        }
    }

    override fun onCrimeDeleted(crime: Crime) {
        if (crime != null) {
            if (selectedCrime.mId!!.equals(crime.mId)) {
                for (fragment in supportFragmentManager.fragments) {

                    if (fragment != null && fragment is CrimeFragment)
                        supportFragmentManager.beginTransaction().remove(fragment).commit()
                }
            }
        }    }

    override fun createFragment(): Fragment {
        return CrimeListFragment()
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_masterdetail
    }
}