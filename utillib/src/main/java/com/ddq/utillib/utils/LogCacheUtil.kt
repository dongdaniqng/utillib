package com.ddq.utillib.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * Author : ddq
 * Time : 2019/12/19 12:33
 * Description :
 */

fun Write(logFileName: String, logContent: String?, appendLine: Boolean = true) {
    LogCacheUtil.writeLog(logFileName, logContent, appendLine)
}

fun Write(logFileName: String, logContent: List<String>, appendLine: Boolean = true) {
    LogCacheUtil.writeLog(logFileName, logContent, appendLine)
}

fun Read(logFileName: String, block: (ArrayList<String>?) -> Unit) {
    LogCacheUtil.readLog(logFileName, block)
}

object LogCacheUtil {
    private const val LOG_DIR_NAME = "log_cache"
    private val lockMap = ConcurrentHashMap<String, ReentrantReadWriteLock>()
    private lateinit var logPath: String
    private val handler = Handler(Looper.getMainLooper())
    private val cacheThreadPool = ThreadPoolExecutor(
        2,
        3,
        60,
        TimeUnit.SECONDS,
        LinkedBlockingDeque(),
        ThreadPoolExecutor.DiscardPolicy()
    )

    internal fun init(context: Context) {
        this.logPath = context.filesDir.absolutePath + File.separator + LOG_DIR_NAME
        createLogDir(logPath)
    }

    fun setLogDir(path: String) {
        this.logPath = path + File.separator + LOG_DIR_NAME
        createLogDir(logPath)
    }

    fun readLog(logFileName: String, block: (ArrayList<String>?) -> Unit) {
        createLock(logFileName)
        readFromDisk(logFileName, block)
    }

    fun writeLog(logFileName: String, logContent: String?, appendLine: Boolean = true) {
        createLock(logFileName)
        val content = generateHeadText() + logContent
        cacheThreadPool.execute {
            Thread.currentThread().name.log()
            writeToDisk(logFileName, content, appendLine)
        }
    }

    fun writeLog(logFileName: String, logContent: List<String>, appendLine: Boolean = true) {
        createLock(logFileName)
        val content = generateHeadText() + list2Str(logContent, appendLine)
        cacheThreadPool.execute {
            Thread.currentThread().name.log()
            writeToDisk(logFileName, content, appendLine)
        }
    }

    fun clearLogCache(logFileName: String): Boolean {
        return try {
            val logDirFile = File(getLogFilePath(logFileName))
            if (logDirFile.exists()) {
                logDirFile.delete()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun clearAllLogCache(): Boolean {
        return try {
            val logDirFile = File(logPath)
            if (logDirFile.exists() && logDirFile.isDirectory) {
                logDirFile.delete()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getAllCacheFile(): Array<File>? {
        return try {
            File(logPath).listFiles()
        } catch (ignore: Exception) {
            null
        }
    }

    fun getAllCacheSize(): Long {
        var totalSize = 0L
        getAllCacheFile()?.forEach {
            totalSize += it.length()
        }
        return totalSize
    }

    fun release() {
        lockMap.clear()
    }

    private fun readFromDisk(logFileName: String, block: (ArrayList<String>?) -> Unit) {
        val lock = lockMap[logFileName]!!
        try {
            cacheThreadPool.execute {
                var result: ArrayList<String>? = null
                val file = File(getLogFilePath(logFileName))
                if (!file.exists()) {
                    handler.post {
                        block(null)
                    }
                }
                lock.read {
                    result = file.bufferedReader().use { it.readLines() as ArrayList<String> }
                }
                handler.post {
                    block(result)
                }
            }
        } catch (ignore: Exception) {
            handler.post {
                block(null)
            }
        }

    }

    private fun writeToDisk(
        logFileName: String,
        logContent: String?,
        appendLine: Boolean
    ): Boolean {
        val lock = lockMap[logFileName]!!
        try {
            if (logContent.isNullOrEmpty()) {
                return false
            }
            val logFile = File(getLogFilePath(logFileName))
            if (!logFile.exists()) {
                logFile.createNewFile()
            }
            lock.write {
                logFile.appendText(if (appendLine) "$logContent\n" else logContent)
            }
            return true
        } catch (ignore: Exception) {
            return false
        }
    }

    private fun createLock(logFileName: String) {
        if (lockMap[logFileName] == null) {
            val lock = ReentrantReadWriteLock()
            lockMap[logFileName] = lock
        }
    }

    private fun getLogFilePath(logFileName: String): String {
        return logPath + File.separator + logFileName
    }

    private fun createLogDir(logDirPath: String) {
        val logDir = File(logDirPath)
        if (!logDir.exists()) {
            logDir.mkdirs()
        }
    }

    private fun generateHeadText(): String {
        var headText = "\n" + SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS", Locale.CHINA).format(Date()) + "    " + Thread.currentThread().name + "\n"
        headText += "-----------------------------\n"
        return headText
    }

    private fun list2Str(list: List<String>, appendLine: Boolean): String {
        return list.joinToString(separator = if (appendLine) "\n" else "")
    }
}