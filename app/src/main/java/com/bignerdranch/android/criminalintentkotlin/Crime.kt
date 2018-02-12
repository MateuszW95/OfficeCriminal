package com.bignerdranch.android.criminalintentkotlin

import java.util.*

/**
 * Created by mateusz on 08.02.18.
 */
class Crime()  {
    var mId:UUID=UUID.randomUUID()
    var mTitle:String=""
    var mDate:Date= Date()
    var mSolved:Boolean= false
}