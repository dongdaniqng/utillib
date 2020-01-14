package com.ddq.utillib.utils

import android.view.View
import java.lang.ref.WeakReference

/**
 * Author : ddq
 * Time : 2019/5/21 11:05
 * Description :single click
 */

object SingleClickUtil {
    //record latest click time
    var oldClickTime = 0L
    //record latest click view
    var oldView: WeakReference<View?>? = null
    //click time
    internal var defaultTime = 300L
    fun setDefaultTime(time:Long){
        defaultTime =time
    }
}

/**
 * handle single click
 */
interface OnSingleClickListener : View.OnClickListener {

    override fun onClick(v: View?) {
        //same view
        if (SingleClickUtil.oldView?.get() == v) {
            val currentTime = System.currentTimeMillis()
            //interrupt click
            if (currentTime - SingleClickUtil.oldClickTime <= SingleClickUtil.defaultTime) {
                SingleClickUtil.oldClickTime = currentTime
                return
            }
            //continue click
            else {
                SingleClickUtil.oldClickTime = currentTime
                onSingleClick(v)
            }
        }
        //not same view
        else {
            SingleClickUtil.oldClickTime = 0
            SingleClickUtil.oldView = WeakReference(v)
            onSingleClick(v)
        }
    }
    fun onSingleClick(v: View?)
}