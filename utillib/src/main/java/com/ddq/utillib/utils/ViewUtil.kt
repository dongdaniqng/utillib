package com.ddq.utillib.utils

import android.view.View

/**
 * Author : ddq
 * Time : 2019/11/8 11:25
 * Description :view ext function
 */
fun View.extVisible() {
    this.visibility = View.VISIBLE
}

fun View.extGone() {
    this.visibility = View.GONE
}

fun View.extInvisible() {
    this.visibility = View.INVISIBLE
}

fun extAllViewVisible(vararg views: View) {
    views.forEach {
        it.extVisible()
    }
}

fun extAllViewGone(vararg views: View) {
    views.forEach {
        it.extGone()
    }
}

fun extAllViewInvisible(vararg views: View) {
    views.forEach {
        it.extInvisible()
    }
}