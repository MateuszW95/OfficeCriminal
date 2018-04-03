package com.bignerdranch.android.criminalintentkotlin

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import java.util.*

/**
 * Created by mateusz on 19.02.18.
 */
class DatePickerFragment:DialogFragment() {

    lateinit var mDatePicker:DatePicker
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var v: View =LayoutInflater.from(this.context).inflate(R.layout.date_picker,null)

        var date:Date=arguments.getSerializable(ARG_DATE) as Date

        var calendar:Calendar= Calendar.getInstance()
        calendar.time=date
        var day=calendar.get(Calendar.DAY_OF_MONTH)
        var month=calendar.get(Calendar.MONTH)
        var year=calendar.get(Calendar.YEAR)



        mDatePicker=v.findViewById(R.id.date_picker_element)

        mDatePicker.init(year,month,day,null)
        return AlertDialog.Builder(this.context)
                .setTitle(R.string.date_picker_title)
                .setView(v)
                .setPositiveButton(android.R.string.ok,DialogInterface.OnClickListener { dialog, which ->
                    run {
                        var y = mDatePicker.year
                        var d = mDatePicker.dayOfMonth
                        var m=mDatePicker.month

                        var date=GregorianCalendar(y,m,d).time
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