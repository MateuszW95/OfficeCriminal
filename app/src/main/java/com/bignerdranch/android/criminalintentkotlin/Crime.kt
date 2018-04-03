package com.bignerdranch.android.criminalintentkotlin

import java.util.*

/**
 * Created by mateusz on 08.02.18.
 */
class Crime(id:UUID)  {
    var mId:UUID?=null
    var mTitle:String=""
    var mDate:Date= Date()
    var mSolved:Boolean= false
    init {
        mId =id
        mDate= Date()
    }


    constructor() : this(UUID.randomUUID())

}