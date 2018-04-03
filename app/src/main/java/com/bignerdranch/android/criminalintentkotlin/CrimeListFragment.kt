package com.bignerdranch.android.criminalintentkotlin

import android.app.Activity
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
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import java.text.SimpleDateFormat

/**
 * Created by mateusz on 11.02.18.
 */
class CrimeListFragment:android.support.v4.app.Fragment() {

    private lateinit var mCrimeRecyclerView:RecyclerView
    private   var mAdapter: CrimeAdapter?=null
    lateinit var mAContext:Context
    private lateinit var mTextView: TextView
    private lateinit var mButtonNew:Button
     var mPosition:Int=-1
    var showSubtilte:Boolean=false
    var CODE:Int=22
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v:View=inflater!!.inflate(R.layout.fragment_crime_list,container,false)

        mCrimeRecyclerView=v.findViewById(R.id.crime_recycle_view)
        mCrimeRecyclerView.layoutManager=LinearLayoutManager(this.context)//?
        mAContext=this.context
        mTextView=v.findViewById(R.id.tv_noElements)
        mButtonNew=v.findViewById(R.id.bt_new)

        mButtonNew.setOnClickListener(View.OnClickListener {

            var tmpCrime=Crime()
            CrimeLab.get(activity).addCrime(tmpCrime)
            var intent:Intent=CrimePagerActivity.newIntent(activity,tmpCrime.mId!!)
            startActivityForResult(intent,CODE)
        })

        updateUI(activity)
        updateScreen()
        return v

    }

    private inner class CrimeHolder(inflater: LayoutInflater,var parent: ViewGroup):RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_crime,parent,false)),View.OnClickListener{
        override fun onClick(v: View?) {
            var intent:Intent=CrimePagerActivity.newIntent(parent.context,mCrime.mId!!)
            mPosition= position
            startActivityForResult(intent,CODE)
            //ContextCompat.str(parent.context,intent,null)


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

    private inner class CrimeAdapter(var mCrimes:ArrayList<Crime>):RecyclerView.Adapter<CrimeHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CrimeHolder {
            var layoutInflater:LayoutInflater= LayoutInflater.from(parent!!.context) //?

            return CrimeHolder(layoutInflater,parent)

        }

        override fun getItemCount(): Int =mCrimes.size

        fun setCrimes(crimes:ArrayList<Crime>){
            mCrimes=crimes
        }

        override fun onBindViewHolder(holder: CrimeHolder?, position: Int) {
            var crime:Crime=mCrimes[position]
            holder!!.bind(crime)
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.fragment_crime_list,menu)
        var subtitleItem:MenuItem=menu!!.findItem(R.id.show_subtitle)
        if(!showSubtilte){
            subtitleItem.title=getString(R.string.show_subtitle)
        }
        else
        {
            subtitleItem.title=getString(R.string.hide_subtite)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.new_crime -> {
            var tmpCrime=Crime()
                CrimeLab.get(activity).addCrime(tmpCrime)
                var intent:Intent=CrimePagerActivity.newIntent(activity,tmpCrime.mId!!)
                startActivityForResult(intent,CODE)
                return true
            }
            R.id.show_subtitle->{
                showSubtilte=!showSubtilte
                activity.invalidateOptionsMenu()
                updateSubtile()
                return true
            }
            else-> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        updateUI(this.context)
        updateScreen()
        updateSubtile()
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
            mAdapter!!.setCrimes(crimes)
            mAdapter!!.notifyItemChanged(mPosition)
        }
        updateSubtile()
    }

    fun updateSubtile(){
        var crimeLab:CrimeLab= CrimeLab.get(activity)
        var crimeCount:Int=crimeLab.getCrimes().size
        var subtitle:String?=resources.getQuantityString(R.plurals.crimeCount,crimeCount,crimeCount)
        if(!showSubtilte) subtitle=null
        var activity:AppCompatActivity = activity as AppCompatActivity
        activity.supportActionBar!!.subtitle=subtitle

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode!=Activity.RESULT_OK) return
        if(requestCode==CODE){
            CrimeLab.get(activity).deleteCrime(CrimeLab.get(activity).getCrime(CrimeFragment.getDataFromIntent(data!!))!!)
            mAdapter!!.notifyItemRemoved(mPosition)
        }
    }

    fun updateScreen()
    {
        if(CrimeLab.get(activity).getCrimes().size!=0){
            mButtonNew.visibility=View.GONE
            mTextView.visibility=View.GONE
            mCrimeRecyclerView.visibility=View.VISIBLE
        }
        else
        {
            mButtonNew.visibility=View.VISIBLE
            mTextView.visibility=View.VISIBLE
            mCrimeRecyclerView.visibility=View.GONE
        }
    }
}