package com.bignerdranch.android.criminalintentkotlin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.bignerdranch.android.criminalintentkotlin.database.CrimeBaseHelper
import com.bignerdranch.android.criminalintentkotlin.database.CrimeCursorWrapper
import com.bignerdranch.android.criminalintentkotlin.database.CrimeDbSchema
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by mateusz on 11.02.18.
 */
class CrimeLab {
    companion object {
        private  var sCrimeLab:CrimeLab?=null
        fun get(context:Context):CrimeLab {
            if (sCrimeLab == null) {
                sCrimeLab=CrimeLab(context)
            }
            return sCrimeLab as CrimeLab
        }

        private fun getContentValues(crime: Crime):ContentValues{
            var values=ContentValues()
            values.put(CrimeDbSchema.CrimeTable.Cols.UUID,crime.mId.toString())
            values.put(CrimeDbSchema.CrimeTable.Cols.TITLE,crime.mTitle)
            values.put(CrimeDbSchema.CrimeTable.Cols.DATE,crime.mDate.time)
            values.put(CrimeDbSchema.CrimeTable.Cols.SOLVED,if(crime.mSolved) 1 else 0)
            return values
        }
    }
    private  lateinit var mContext: Context
    private  lateinit var mDatabase:SQLiteDatabase
    //private lateinit var mCrimes:ArrayList<Crime>
    private constructor(context: Context){
        //mCrimes= ArrayList()
        mContext=context.applicationContext
        mDatabase=CrimeBaseHelper(mContext).writableDatabase

    }

    fun getCrimesHM():HashMap<UUID,Crime>{
        var mCrimes=getCrimes()
        var tmp=HashMap<UUID,Crime>()
        for ( crime in mCrimes){
            tmp.put(crime.mId!!,crime)
        }
        return tmp
    }

    fun updateCrime(crime: Crime){
        var uuidString:String=crime.mId.toString()
        var values= getContentValues(crime)

        mDatabase.update(CrimeDbSchema.CrimeTable.NAME,values,CrimeDbSchema.CrimeTable.Cols.UUID+"=?",
                Array<String>(1,{uuidString}))
    }

    fun queryCrimes(whereClause: String?, whereArgs: Array<String>?):CrimeCursorWrapper{
        var cursor:Cursor=mDatabase.query(CrimeDbSchema.CrimeTable.NAME,null,
                whereClause,
                whereArgs,
                null,
                null,
                null)
        return CrimeCursorWrapper(cursor)
    }

    fun addCrime(crime:Crime){
        val values= getContentValues(crime)
        mDatabase.insert(CrimeDbSchema.CrimeTable.NAME,null,values)
    }
    fun getCrime(id:UUID): Crime? {
        var cursor:CrimeCursorWrapper=queryCrimes(CrimeDbSchema.CrimeTable.Cols.UUID+"= ?",
                Array<String>(1,{id.toString()}))

        try {
            if(cursor.count==0){
                return null
            }
            cursor.moveToFirst();
            return  cursor.getCrime()
        }
        finally {
            cursor.close()
        }
    }
    fun deleteCrime(crime:Crime){
        mDatabase.delete(CrimeDbSchema.CrimeTable.NAME,CrimeDbSchema.CrimeTable.Cols.UUID+"= ?",Array(1,{crime.mId.toString()}))
    }
    fun getCrimes():ArrayList<Crime>{
       var crimes=ArrayList<Crime>()
        var cursor:CrimeCursorWrapper=queryCrimes(null,null)
        try{
            cursor.moveToFirst()
            while (!cursor.isAfterLast){
                crimes.add(cursor.getCrime())
                cursor.moveToNext()
            }
        }
        finally {
            cursor.close()
        }
        return crimes
    }
}