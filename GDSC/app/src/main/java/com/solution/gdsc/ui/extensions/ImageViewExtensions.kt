package com.solution.gdsc.ui.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun ImageView.load(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(this)
            .load("https://storage.googleapis.com/gces_bucket/$url")
            .into(this)
    }
}