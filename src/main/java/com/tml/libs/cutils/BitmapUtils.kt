package com.tml.libs.cutils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat

//import android.support.annotation.DrawableRes
//import android.support.v4.content.res.ResourcesCompat

//import androidx.annotation.ColorInt
//import androidx.annotation.DrawableRes
//import androidx.core.content.res.ResourcesCompat
//import androidx.core.graphics.drawable.DrawableCompat


class BitmapUtils {
 companion object {
     fun vectorToBitmap(res: Resources, @DrawableRes id: Int): Bitmap? {
         val vectorDrawable: Drawable? = ResourcesCompat.getDrawable(res, id, null)
         vectorDrawable?.let {
             val bitmap = Bitmap.createBitmap(
                 vectorDrawable.intrinsicWidth,
                 vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
             )
             val canvas = Canvas(bitmap)
             vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
             //DrawableCompat.setTint(vectorDrawable, tintColor)
             vectorDrawable.draw(canvas)
             return bitmap
         }
         return null
     }
 }
}