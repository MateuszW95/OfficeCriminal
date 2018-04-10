package com.bignerdranch.android.criminalintentkotlin.database

/**
 * Created by mateusz on 03.04.18.
 */
class CrimeDbSchema {

  object CrimeTable{
      val NAME:String="crimes"


      object Cols{
          val UUID:String="uuid"
          val TITLE:String="title"
          val DATE:String="date"
          val SOLVED:String="solved"
          val SUSPECT:String="suspect"
          val SUSPECT_NUMBER:String="number"
      }
    }
}