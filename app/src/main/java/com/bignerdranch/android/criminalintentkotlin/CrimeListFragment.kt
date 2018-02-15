package com.bignerdranch.android.criminalintentkotlin

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat.startActivity
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat

/**
 * Created by mateusz on 11.02.18.
 */
class CrimeListFragment:android.support.v4.app.Fragment() {

    private lateinit var mCrimeRecyclerView:RecyclerView
    private   var mAdapter: CrimeAdapter?=null
    lateinit var mAContext:Context
     var mPosition:Int=-1
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v:View=inflater!!.inflate(R.layout.fragment_crime_list,container,false)

        mCrimeRecyclerView=v.findViewById(R.id.crime_recycle_view)
        mCrimeRecyclerView.layoutManager=LinearLayoutManager(this.context)//?
        mAContext=this.context
        updateUI(activity)
        return v

    }

    private inner class CrimeHolder(inflater: LayoutInflater,var parent: ViewGroup):RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_crime,parent,false)),View.OnClickListener{
        override fun onClick(v: View?) {
            var intent:Intent=CrimeActivity.newIntent(parent.context,mCrime.mId)
            mPosition= position
            ContextCompat.startActivity(parent.context,intent,null)


        }

        private  var mTitleTextView:TextView
        private  var mDateTextView: TextView
        private  var mImageViewSolved: ImageView
        private lateinit var mCrime:Crime

        init {
            itemView.setOnClickListener(this)
            mDateTextView=itemView.findViewById(R.id.crime_date)
            mTitleTextView=itemView.findViewById(R.id.crime_title)
            mImageViewSolved=itemView.findViewById(R.id.crime_solved)

        }
        fun bind( crime:Crime){
            mCrime=crime
            mTitleTextView.text=mCrime.mTitle
            var sdf:SimpleDateFormat= SimpleDateFormat("d MMMM Y")

            mDateTextView.text=sdf.format(mCrime.mDate)
            if(mCrime.mSolved) mImageViewSolved.visibility=View.VISIBLE else mImageViewSolved.visibility=View.INVISIBLE


        }

    }

    private inner class CrimeAdapter(var crimes:ArrayList<Crime>):RecyclerView.Adapter<CrimeHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CrimeHolder {
            var layoutInflater:LayoutInflater= LayoutInflater.from(parent!!.context) //?

            return CrimeHolder(layoutInflater,parent)

        }

        override fun getItemCount(): Int =crimes.size

        override fun onBindViewHolder(holder: CrimeHolder?, position: Int) {
            var crime:Crime=crimes[position]
            holder!!.bind(crime)
        }



    }

    override fun onResume() {
        super.onResume()
        updateUI(this.context)
    }
    fun updateUI( context:Context){
        var crimeLab:CrimeLab= CrimeLab.get(activity)
        var crimes=crimeLab.getCrimes()
        if(mAdapter==null){
            mAdapter=CrimeAdapter(crimes)
            mCrimeRecyclerView.adapter=mAdapter
        }
        else
        {
            mAdapter!!.notifyItemChanged(mPosition)
        }
    }
}