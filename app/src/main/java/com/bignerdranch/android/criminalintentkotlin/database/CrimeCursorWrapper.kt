package com.bignerdranch.android.criminalintentkotlin.database

import android.database.Cursor
import android.database.CursorWrapper
import com.bignerdranch.android.criminalintentkotlin.Crime
import java.util.*

/**
 * Created by mateusz on 03.04.18.
 */
class CrimeCursorWrapper(cursor: Cursor):CursorWrapper(cursor){

    fun getCrime(): Crime {
        var uuidString:String=getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.UUID))
        var title:String=getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.TITLE))
        var date:Long=getLong(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.DATE))
        var isSolved:Int=getInt(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SOLVED))

        var crime=Crime(UUID.fromString(uuidString))
        crime.mTitle=title
        crime.mDate= Date(date)
        crime.mSolved=(isSolved!=0)
        return crime
    }
}