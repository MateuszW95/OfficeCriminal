package com.bignerdranch.android.criminalintentkotlin.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.bignerdranch.android.criminalintentkotlin.database.CrimeDbSchema.*

/**
 * Created by mateusz on 03.04.18.
 */
class CrimeBaseHelper(var context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null, VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("create table "+ CrimeTable.NAME+"("+
        " _id integer primary key autoincrement, "+
        CrimeTable.Cols.UUID+", "+ CrimeTable.Cols.TITLE+", "
                + CrimeTable.Cols.DATE+", "
                + CrimeTable.Cols.SOLVED+","
                +CrimeTable.Cols.SUSPECT+","
                + CrimeTable.Cols.SUSPECT_NUMBER+")")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val VERSION:Int=1
        const val DATABASE_NAME:String="crimeBase.db"

    }
}