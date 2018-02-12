package com.bignerdranch.android.criminalintentkotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_crime.*
import java.util.zip.Inflater

/**
 * Created by mateusz on 08.02.18.
 */
class CrimeFragment:Fragment() {
    var mCrime: Crime? =null
    var mDateButton:Button?=null
    var mSolvedCheckBox:CheckBox?=null
    var mTitleField:EditText?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCrime= Crime()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v:View?= inflater?.inflate(R.layout.fragment_crime,container,false) ?:null
        mTitleField=v?.findViewById(R.id.crime_title)
        mDateButton=v?.findViewById(R.id.crime_date)
        mSolvedCheckBox=v?.findViewById(R.id.crime_solved)
        mTitleField?.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mCrime?.mTitle=s.toString()
            }
        })
        mDateButton?.text=mCrime?.mDate.toString()
        mDateButton?.isEnabled=false
        mSolvedCheckBox?.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                mCrime?.mSolved=isChecked
            }

        })
        return v
    }
}