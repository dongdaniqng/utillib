package com.ddq.utillib.utils

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

/**
 * Author : ddq
 * Time : 2018/12/14 16:32
 * Description :log util
 */
const val TAG = "TAG"
const val TAG2 = "TAG2"
const val E = "E"
const val W = "W"
const val I = "I"
const val D = "D"
const val V = "V"

//if need log
var flagNeedLog = true

fun Any.log(tag: String = TAG, level: String = E) {
    if (!flagNeedLog) return
    when (level) {
        E -> Log.e(tag, this.toString())
        W -> Log.w(tag, this.toString())
        I -> Log.i(tag, this.toString())
        D -> Log.d(tag, this.toString())
        V -> Log.v(tag, this.toString())
        else -> Log.v(tag, this.toString())
    }
}

fun Any.log2(tag: String = TAG2, className: String? = null, level: String = E) {
    if (!flagNeedLog) return
    try {
        val traceElements = Thread.currentThread().stackTrace
        var element: StackTraceElement? = null
        if (className != null) {
            for (t in traceElements) {
                if (t.className == className) {
                    element = t
                    break
                }
            }
        }
        if (element != null) {
            Log.e(
                tag, "(" + element.fileName + ":" + element.lineNumber
                        + ") <==> " + Thread.currentThread().name
                        + " <==> " + SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA).format(Date())
            )
        }
    } finally {
        this.log(tag, level)
    }
}