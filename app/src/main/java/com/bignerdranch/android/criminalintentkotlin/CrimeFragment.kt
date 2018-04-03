package com.bignerdranch.android.criminalintentkotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_crime.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.Inflater

/**
 * Created by mateusz on 08.02.18.
 */
class CrimeFragment:Fragment() {
    var mCrime: Crime? =null
    var mDateButton:Button?=null
    var mSolvedCheckBox:CheckBox?=null
    var mTitleField:EditText?=null
    val REQUEST_CODE:Int=0
    val DATE_PICER_TAG:String="Date"
    var mDeleteButton:Button?=null


    companion object {
        private val ARG_CRIME_ID="crime_id"
        val KEY_ID:String="!@####"
        fun newInstance(crimeId:UUID):Fragment{
            var args:Bundle= Bundle()
            args.putSerializable(ARG_CRIME_ID,crimeId)
            var fragment:CrimeFragment= CrimeFragment()
            fragment.arguments=args
            return fragment
        }
        public fun getDataFromIntent(intent: Intent):UUID
        {
            return intent.getSerializableExtra(KEY_ID) as UUID
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var crimeId:UUID=arguments.getSerializable(ARG_CRIME_ID) as UUID
        var cc:CrimeLab= CrimeLab.get(activity)
        mCrime= cc.getCrime(crimeId)
    }

    override fun onPause() {
        super.onPause()
        CrimeLab.get(activity).updateCrime(mCrime!!)
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v:View?= inflater?.inflate(R.layout.fragment_crime,container,false) ?:null
        mTitleField=v?.findViewById(R.id.crime_title)
        mDateButton=v?.findViewById(R.id.crime_date)
        mSolvedCheckBox=v?.findViewById(R.id.crime_solved)
        mTitleField!!.setText(mCrime!!.mTitle,TextView.BufferType.EDITABLE)
        mDeleteButton=v!!.findViewById(R.id.bt_deleteCrime)
        updateDate()
        mDateButton!!.setOnClickListener(View.OnClickListener {

            var manager:FragmentManager=fragmentManager
            var fragment=DatePickerFragment.newInstance(mCrime!!.mDate)
            fragment.setTargetFragment(this,REQUEST_CODE)
            fragment.show(manager,DATE_PICER_TAG)
        })
        mDeleteButton!!.setOnClickListener(View.OnClickListener {
            var intent:Intent= Intent()
            intent.putExtra(KEY_ID,mCrime!!.mId)
            activity.setResult(Activity.RESULT_OK,intent)
            activity.finish()
        })
        mSolvedCheckBox!!.isChecked=mCrime!!.mSolved
        mTitleField?.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mCrime?.mTitle=s.toString()
            }
        })

        mSolvedCheckBox?.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                mCrime?.mSolved=isChecked
            }

        })
        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode!= Activity.RESULT_OK) return

        if(requestCode==REQUEST_CODE){

            var date:Date= data!!.getSerializableExtra(DatePickerFragment.EXTRA_DATE) as Date
            mCrime!!.mDate=date
            updateDate()

        }
    }
    fun updateDate(){
        var sdf:SimpleDateFormat=SimpleDateFormat("d/M/YYYY")
        mDateButton!!.text=sdf.format(mCrime!!.mDate)
    }


}