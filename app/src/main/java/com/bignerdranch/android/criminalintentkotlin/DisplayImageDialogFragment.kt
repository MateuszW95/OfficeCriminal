package com.bignerdranch.android.criminalintentkotlin

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.view.LayoutInflaterCompat
import android.view.LayoutInflater
import android.widget.ImageView
import java.util.zip.Inflater

/**
 * Created by mateusz on 14.04.18.
 */
class DisplayImageDialogFragment:android.support.v4.app.DialogFragment() {

    companion object {
        private const val EXTRA_PATH:String="extra_file_path"
        fun newInstance(path:String):DisplayImageDialogFragment{
            var bundle=Bundle()
            bundle.putString(EXTRA_PATH,path)
            var fragment=DisplayImageDialogFragment()
            fragment.arguments=bundle
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var v=LayoutInflater.from(this.activity).inflate(R.layout.image_display_dialog,null)
        var showImageView=v.findViewById<ImageView>(R.id.IV_display_image)

        var path=arguments.getString(EXTRA_PATH)
        var bitmap=BitmapFactory.decodeFile(path)

        showImageView.minimumHeight=bitmap.height
        showImageView.minimumWidth=bitmap.width
        showImageView.maxHeight=bitmap.height
        showImageView.maxWidth=bitmap.width

        showImageView.setImageBitmap(bitmap)

        return AlertDialog.Builder(activity).setView(v).create()
    }
}