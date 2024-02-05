package com.solution.gdsc.ui.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.solution.gdsc.R

@BindingAdapter("imageUrl")
fun ImageView.load(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(this)
            .load("https://storage.googleapis.com/gces_bucket/$url")
            .placeholder(R.color.gray_300)
            .error(R.drawable.home_image)
            .into(this)
    } else {
        setImageResource(R.drawable.home_image)
    }
}