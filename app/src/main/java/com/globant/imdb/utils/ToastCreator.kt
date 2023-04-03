package com.globant.imdb.utils

import android.content.Context
import android.widget.Toast

object ToastCreator{
    fun showToastMessage(context: Context?, message: String){
        context.let { Toast.makeText(it, message, Toast.LENGTH_SHORT).show() }
    }
}