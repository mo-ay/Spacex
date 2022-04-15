package com.ayprotech.spacex.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ayprotech.spacex.R
import com.bumptech.glide.Glide

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
