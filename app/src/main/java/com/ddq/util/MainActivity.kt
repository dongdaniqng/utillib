package com.ddq.util

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.ddq.utillib.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), OnSingleClickListener {

    var info by SPUtil("app_info", "default value", true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)

        //设置防止重复点击时间
        SingleClickUtil.setDefaultTime(300)

        //展示sp的值
        tv1.text = info

        "message".log()
        info.log2("new_tag", this.javaClass.canonicalName, E)
        val strList = arrayListOf<String>()
        repeat(10) {
            strList.add(UUID.randomUUID().toString())
        }
        LogCacheUtil.writeLog("a", UUID.randomUUID().toString())
        LogCacheUtil.writeLog("a", strList)
        LogCacheUtil.readLog("a") {
            Thread.currentThread().name.log()
            it?.joinToString()?.log()
        }
    }


    override fun onSingleClick(v: View?) {
        when (v) {
            btn1 -> {
                Toast.makeText(this, "is click", Toast.LENGTH_SHORT).show()
            }
            btn2 -> {
                info = UUID.randomUUID().toString()
                info?.log2("new tag2", E)
                tv1.text = info
            }
        }
    }
}
