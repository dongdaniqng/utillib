package com.ddq.util

import android.app.Application
import com.ddq.utillib.UtilInit
import com.ddq.utillib.utils.SPUtil
import com.ddq.utillib.utils.log2

/**
 * Author : ddq
 * Time : 2019/5/21 11:04
 * Description :
 */
class App : Application() {


    override fun onCreate() {
        super.onCreate()
        UtilInit.init(applicationContext)
//        var test by SPUtil("a",1)
//
//        test =1
//
//        test.log2()
//
//        test = 1
//
//        test.log2()
    }
}