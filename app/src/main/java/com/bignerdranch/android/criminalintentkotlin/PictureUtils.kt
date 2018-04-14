package com.bignerdranch.android.criminalintentkotlin

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.view.View

/**
 * Created by mateusz on 13.04.18.
 */
object PictureUtils
{
    fun getScaledBitmap(path:String,destWidth:Int,destHeight:Int):Bitmap{
        var options:BitmapFactory.Options = BitmapFactory.Options()
        options.inJustDecodeBounds=true
        BitmapFactory.decodeFile(path,options)
        var srcWith:Float= options.outWidth.toFloat()
        var srcHeight:Float=options.outHeight.toFloat()

        //współczynik przeskalowania
        var inSampleSize:Int=1
        if(srcHeight>destHeight || srcWith>destWidth){
            var heightScale=srcHeight/destHeight.toFloat()
            var widthScale=srcWith/destWidth.toFloat()
            inSampleSize=Math.round(if(widthScale>heightScale) widthScale else heightScale)
        }
        options=BitmapFactory.Options()
        options.inSampleSize=inSampleSize

        return BitmapFactory.decodeFile(path,options)
    }

    fun getScaledBitmap(path:String,activity: Activity):Bitmap{
        var size:Point= Point()
        activity.windowManager.defaultDisplay.getSize(size)
        return getScaledBitmap(path,size.x,size.y)
    }

    fun getScaledBitmap(path: String,container:View): Bitmap {
        return getScaledBitmap(path,container.width,container.height)
    }
}