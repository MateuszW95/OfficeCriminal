package com.bignerdranch.android.criminalintentkotlin

import android.content.Context
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by mateusz on 11.02.18.
 */
class CrimeLab {
    companion object {
        private  var sCrimeLab:CrimeLab?=null
        private lateinit var mCrimes:HashMap<UUID,Crime>

        fun get(context:Context):CrimeLab {
            if (sCrimeLab == null) {
                sCrimeLab=CrimeLab(context)
            }
            return sCrimeLab as CrimeLab

        }
    }
    private constructor(context: Context){
        mCrimes= HashMap()
        for(i in 0..99){
            var tmp= Crime()
            tmp.mTitle=("Sprawa #"+i.toString())
            tmp.mSolved=(i%2==0)
            mCrimes[tmp.mId]=tmp
        }


    }
    fun getCrime(id:UUID): Crime? {
        var c=mCrimes[id]
        return c
    }
    fun getCrimes():ArrayList<Crime>{
        return ArrayList(mCrimes.values)
    }
}