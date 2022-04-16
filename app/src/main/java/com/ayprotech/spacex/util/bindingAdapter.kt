package com.ayprotech.spacex.util

import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ayprotech.spacex.R
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@BindingAdapter("launchImage")
fun setLaunchImage(imageView: ImageView, item: String?) {
    try {
        item?.let {
            Glide.with(imageView.context)
                .load(it)
                .thumbnail(
                    Glide.with(imageView.context)
                        .load(R.drawable.loading)
                        .centerInside()
                )
                .fitCenter()
                .into(imageView)
        }
    } catch (ignored: Exception) {
    }
}
@BindingAdapter("dateFormat")
fun setDateText(textView: TextView, item: String?) {
    try {
        item?.let {
            val inputFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val outPutFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            val inputDate = inputFormat.parse(it)
            val outPutDate = inputDate?.let { it1 -> outPutFormat.format(it1) }
            textView.text = outPutDate
        }
    } catch (ignored: Exception) {
    }
}
@BindingAdapter("progressDate")
fun setProgressEvent( progressBar: ProgressBar, item: String?) {
    try {
        item?.let {
            val formater =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val launchDate = formater.parse(it)
            launchDate?.let { date ->
                val diffInMillisec =  Calendar.getInstance().timeInMillis - date.time
                val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec)
                val  percentage = 1000 + diffInDays
                if (percentage > 1000){
                    progressBar.visible(false)
                }else{
                    progressBar.visible(true)
                    progressBar.progress = percentage.toInt()
                }
            }

        }
    } catch (ignored: Exception) {
    }
}
