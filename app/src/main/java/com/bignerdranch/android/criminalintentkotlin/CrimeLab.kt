package com.bignerdranch.android.criminalintentkotlin

import android.content.Context
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by mateusz on 11.02.18.
 */
class CrimeLab {
    companion object {
        private  var sCrimeLab:CrimeLab?=null
        private lateinit var mCrimes:ArrayList<Crime>

        fun get(context:Context):CrimeLab {
            if (sCrimeLab == null) {
                return CrimeLab(context)
            }
            else
            {
                return sCrimeLab as CrimeLab
            }
        }
    }
    private constructor(context: Context){
        mCrimes=ArrayList()
        for(i in 0..99){
            var tmp:Crime= Crime()
            tmp.mTitle=("Sprawa #"+i.toString())
            tmp.mSolved=(i%2==0)
            mCrimes.add(tmp)
        }

    }
    fun getCrime(id:UUID):Crime{
        return (mCrimes.filter { it.mId==id }.first())
    }
    fun getCrimes():ArrayList<Crime>{
        return mCrimes
    }
}