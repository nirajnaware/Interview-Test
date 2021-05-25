package com.example.taskdl.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

class CustomBindingAdapters {

    companion
    object {

        @JvmStatic
        @BindingAdapter(value = ["android:srcCompat"])
        fun setSrcCompat(imageView: ImageView, resource: Int) {
            imageView.setImageResource(resource)
        }
    }

}