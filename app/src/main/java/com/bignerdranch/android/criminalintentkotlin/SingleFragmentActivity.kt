package com.bignerdranch.android.criminalintentkotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

/**
 * Created by mateusz on 11.02.18.
 */
abstract class SingleFragmentActivity:AppCompatActivity() {
    protected abstract fun createFragment():Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        var fm: FragmentManager =supportFragmentManager
        var fragment: Fragment?=fm?.findFragmentById(R.id.fragment_container)

        if(fragment==null) {
            fragment = createFragment()
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit()
        }
    }
}