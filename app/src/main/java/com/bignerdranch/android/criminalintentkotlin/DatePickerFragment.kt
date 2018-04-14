package com.bignerdranch.android.criminalintentkotlin

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.*
import java.util.*

/**
 * Created by mateusz on 19.02.18.
 */
class DatePickerFragment:DialogFragment() {

    lateinit var mDatePicker:DatePicker
    lateinit var mTimePicker:TimePicker
    lateinit var mDateImageButton: ImageButton
    lateinit var mTimeImageButton: ImageButton
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var v: View =LayoutInflater.from(this.context).inflate(R.layout.date_picker,null)

        var date:Date=arguments.getSerializable(ARG_DATE) as Date

        var calendar:Calendar= Calendar.getInstance()
        calendar.time=date
        var day=calendar.get(Calendar.DAY_OF_MONTH)
        var month=calendar.get(Calendar.MONTH)
        var year=calendar.get(Calendar.YEAR)
        var hour=calendar.get(Calendar.HOUR)
        var min=calendar.get(Calendar.MINUTE)





        mDatePicker=v.findViewById(R.id.dialog_date_picker)
        mTimePicker=v.findViewById(R.id.dialog_time_picker)
        mDateImageButton=v.findViewById(R.id.bt_picker_date)
        mTimeImageButton=v.findViewById(R.id.bt_picker_clock)

        mDateImageButton.setOnClickListener(View.OnClickListener {

            mTimePicker.visibility=View.GONE
            mDatePicker.visibility=View.VISIBLE
            mDateImageButton.setImageDrawable(resources.getDrawable(R.drawable.ic_date_blue))
            mTimeImageButton.setImageDrawable(resources.getDrawable(R.drawable.ic_action_black))

        })

        mTimeImageButton.setOnClickListener(View.OnClickListener {

            mTimePicker.visibility=View.VISIBLE
            mDatePicker.visibility=View.GONE
            mDateImageButton.setImageDrawable(resources.getDrawable(R.drawable.ic_date_black))
            mTimeImageButton.setImageDrawable(resources.getDrawable(R.drawable.ic_action_blue))
        })



        mDatePicker.init(year,month,day,null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker.hour=hour
            mTimePicker.minute=min
        }
        else
        {
            mTimePicker.currentHour=hour
            mTimePicker.currentMinute=min
        }
        return AlertDialog.Builder(this.context)
                .setTitle(R.string.date_picker_title)
                .setView(v)
                .setPositiveButton(android.R.string.ok,DialogInterface.OnClickListener { dialog, which ->
                    run {
                        var y = mDatePicker.year
                        var d = mDatePicker.dayOfMonth
                        var m=mDatePicker.month
                        var h= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            mTimePicker.hour
                        } else {
                            mTimePicker.currentHour
                        }
                        var mm= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            mTimePicker.minute
                        } else{
                            mTimePicker.currentMinute
                        }

                        var date=GregorianCalendar(y,m,d,h,mm).time
                        sendResult(Activity.RESULT_OK,date)
                    }
                })
                .create()
    }

    companion object {
        private val ARG_DATE:String="Date"
    val EXTRA_DATE:String="com.bignerdranch.android.cmininalintent.date"
        fun newInstance(date:Date):DatePickerFragment{

            var args:Bundle= Bundle()
            args.putSerializable(ARG_DATE,date)
            var fragment:DatePickerFragment= DatePickerFragment()
            fragment.arguments=args
            return fragment
        }
    }

    fun sendResult(resuldId:Int,date:Date){
        if(targetFragment==null) return

        var intent:Intent= Intent()
        intent.putExtra(EXTRA_DATE,date)

        targetFragment.onActivityResult(targetRequestCode,resuldId,intent)

    }
}