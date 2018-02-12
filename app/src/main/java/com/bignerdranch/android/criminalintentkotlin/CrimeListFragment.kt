package com.bignerdranch.android.criminalintentkotlin

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView

/**
 * Created by mateusz on 11.02.18.
 */
class CrimeListFragment:android.support.v4.app.Fragment() {

    private lateinit var mCrimeRecyclerView:RecyclerView
    private  lateinit var mAdapter: Adapter
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v:View=inflater!!.inflate(R.layout.fragment_crime_list,container,false)

        mCrimeRecyclerView=v.findViewById(R.id.crime_recycle_view)
        mCrimeRecyclerView.layoutManager=LinearLayoutManager(this.context)//?
        updateUI(activity)
        return v

    }

    private class CrimeHolder(inflater: LayoutInflater,parent: ViewGroup):RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_crime,parent,false)){
        private lateinit var mTitleTextView:TextView
        private lateinit var mDateTextView: TextView
        private lateinit var mCrime:Crime

        init {
            mDateTextView=itemView.findViewById(R.id.crime_date)
            mTitleTextView=itemView.findViewById(R.id.crime_title)
        }
        fun bind( crime:Crime){
            mCrime=crime
            mTitleTextView.text=mCrime.mTitle
            mDateTextView.text=mCrime.mDate.toString()


        }

    }

    private class CrimeAdapter(var crimes:ArrayList<Crime>):RecyclerView.Adapter<CrimeHolder>(){
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
    fun updateUI( context:Context){
        var crimeLab:CrimeLab= CrimeLab.get(activity)
        var crimes=crimeLab.getCrimes()

       mCrimeRecyclerView.adapter=CrimeAdapter(crimes)
    }
}