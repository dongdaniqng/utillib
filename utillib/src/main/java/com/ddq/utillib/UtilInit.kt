package com.ddq.utillib

import android.annotation.SuppressLint
import android.content.Context
import com.ddq.utillib.utils.LogCacheUtil

/**
 * Author : ddq
 * Time : 2019/5/21 10:55
 * Description :set default context
 */
object UtilInit {
    private var ctx: Context? = null

    /**
     * @param context Context must use application context
     */
    fun init(context: Context) {
        ctx = context
        LogCacheUtil.init(ctx!!)
    }

    @Synchronized
    internal fun getContext(): Context {
        if (ctx == null) {
            throw NullPointerException("context must not null")
        }
        return ctx!!
    }
}