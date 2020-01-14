package com.ddq.utillib.utils

import android.annotation.SuppressLint
import android.content.Context
import com.ddq.utillib.UtilInit
import kotlin.reflect.KProperty

/**
 * Author : ddq
 * Time : 2019/5/21 10:51
 * Description :
 */
/**
 * xmlFileName : save file's name
 * defaultValue : the data default value
 * isAsync : if true,the editor will use editor.apply(), else use editor.commit()
 */
class SPUtil<T> constructor(
    xmlFileName: String,
    private val defaultValue: T,
    private val isAsync: Boolean = true
) {

    private val context = UtilInit.getContext()

    //get sp
    private val sp = context.getSharedPreferences(xmlFileName, Context.MODE_PRIVATE)

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return when (defaultValue) {
            is Int -> {
                sp.getInt(property.name, defaultValue) as T
            }
            is String -> {
                sp.getString(property.name, defaultValue) as T
            }
            is Boolean -> {
                sp.getBoolean(property.name, defaultValue) as T
            }
            is Float -> {
                sp.getFloat(property.name, defaultValue) as T
            }
            is Long -> {
                sp.getLong(property.name, defaultValue) as T
            }
            else -> {
                throw UnsupportTypeException("unSupport get type")
            }
        }
    }

    @SuppressLint("ApplySharedPref")
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        if (value == null) {
            return
        }
        sp.edit().apply {
            when (value) {
                is String -> {
                    putString(property.name, value)
                }
                is Int -> {
                    putInt(property.name, value)
                }
                is Boolean -> {
                    putBoolean(property.name, value)
                }
                is Float -> {
                    putFloat(property.name, value)
                }
                is Long -> {
                    putLong(property.name, value)
                }
                else -> {
                    throw UnsupportTypeException("unSupport save type")
                }
            }
        }.let {
            if (isAsync) it.apply() else it.commit()
        }
    }


    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: Nothing?, property: KProperty<*>): T {
        return when (defaultValue) {
            is Int -> {
                sp.getInt(property.name, defaultValue) as T
            }
            is String -> {
                sp.getString(property.name, defaultValue) as T
            }
            is Boolean -> {
                sp.getBoolean(property.name, defaultValue) as T
            }
            is Float -> {
                sp.getFloat(property.name, defaultValue) as T
            }
            is Long -> {
                sp.getLong(property.name, defaultValue) as T
            }
            else -> {
                throw UnsupportTypeException("unSupport get type")
            }
        }
    }

    @SuppressLint("ApplySharedPref")
    operator fun setValue(thisRef: Nothing?, property: KProperty<*>, value: T?) {
        if (value == null) {
            return
        }
        sp.edit().apply {
            when (value) {
                is String -> {
                    putString(property.name, value)
                }
                is Int -> {
                    putInt(property.name, value)
                }
                is Boolean -> {
                    putBoolean(property.name, value)
                }
                is Float -> {
                    putFloat(property.name, value)
                }
                else -> {
                    throw UnsupportTypeException("unSupport save type")
                }
            }
        }.let {
            if (isAsync) it.apply() else it.commit()
        }
    }
}

/**
 * UnsupportTypeException
 * @constructor
 */
class UnsupportTypeException constructor(msg: String) : Exception(msg)