package com.bignerdranch.android.criminalintentkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_crime_pager.*
import java.util.*

/**
 * Created by mateusz on 16.02.18.
 */
class CrimePagerActivity:AppCompatActivity(),CrimeFragment.Callbacks {
    override fun onCrimeUpdated(crime: Crime) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private lateinit var mViewPager: ViewPager
    private lateinit var mCrimes:List<Crime>

    companion object {
        private val EXTRA_CRIME_ID:String="com.bignerdranch.android.cmininalintent.crime_id"

        fun newIntent(packageContext:Context,crimeId:UUID):Intent{
            var intent: Intent = Intent(packageContext,CrimePagerActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID,crimeId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime_pager)
        var crimeId:UUID=intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID
        var pos:Int=-2
        mViewPager=findViewById(R.id.crime_view_pager)

        mCrimes=CrimeLab.get(this).getCrimes()
        var fragmentManager:FragmentManager=supportFragmentManager
        bt_first.setOnClickListener(View.OnClickListener {
            mViewPager.currentItem=0
            bt_first.isEnabled=false
        })
        bt_last.setOnClickListener(View.OnClickListener {
            mViewPager.currentItem=mCrimes.size-1
            bt_last.isEnabled=false
        })
        mViewPager.adapter=object: FragmentStatePagerAdapter(fragmentManager){

            override fun getItem(position: Int): Fragment {
                var crime:Crime= mCrimes[position]
                bt_first.isEnabled = position != 0
                bt_last.isEnabled = position != (mCrimes.size-1)
                pos=position
                return CrimeFragment.newInstance(crime.mId!!)
            }

            override fun startUpdate(container: ViewGroup?) {
                super.startUpdate(container)
                Log.d("kkka","Update")
                bt_first.isEnabled = pos != 0
                bt_last.isEnabled = pos != (mCrimes.size-1)
            }

            override fun getCount(): Int {
                return mCrimes.size
            }

        }
        for (i in 0..(mCrimes.size-1)){
            if(mCrimes[i].mId!!.equals(crimeId)){
                mViewPager.currentItem=i
                break
            }
        }
    }
}