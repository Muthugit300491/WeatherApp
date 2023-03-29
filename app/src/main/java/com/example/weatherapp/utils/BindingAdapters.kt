package com.example.productapp.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

import java.util.*


@BindingAdapter("setText")
fun bindDefaultText(mView: TextView, mText: String?) {
    if (!mText.isNullOrEmpty()) {
        mView.text = mText
    }
}







