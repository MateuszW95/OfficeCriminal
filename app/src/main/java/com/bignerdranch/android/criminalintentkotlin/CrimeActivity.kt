package com.bignerdranch.android.criminalintentkotlin

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import java.util.*

class CrimeActivity : SingleFragmentActivity() {
    private val EXTRA_CRIME_ID:String="com.bignerdranch.android.ciminalintent.crime.id"
    override fun createFragment(): Fragment {
        var crimeId:UUID= intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID
        return CrimeFragment.newInstance(crimeId)
    }

    companion object {
        private val EXTRA_CRIME_ID:String="com.bignerdranch.android.ciminalintent.crime.id"
        public fun newIntent(packageContext: Context,crimeId:UUID):Intent{
            var intent:Intent= Intent(packageContext,CrimeActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID,crimeId)
            return intent
        }
    }



}

