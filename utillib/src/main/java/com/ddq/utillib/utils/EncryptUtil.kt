package com.ddq.utillib.utils

import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest

/**
 * Author : ddq
 * Time : 2019/12/12 14:43
 * Description :
 */
object EncryptUtil {

    fun md5Str(str: String): String {
        val digest = MessageDigest.getInstance("MD5")
        val bt = digest.digest(str.toByteArray())
        val sb = StringBuffer(bt.size * 2)
        for (b in bt) {
            if ((b.toInt() and 0xff) < 0x0f) {
                sb.append("0")
            }
            sb.append(Integer.toHexString((b.toInt() and 0xff)))
        }
        return sb.toString()
    }


    fun sha256Str(str: String): String {
        val digest = MessageDigest.getInstance("SHA256")
        val bt = digest.digest(str.toByteArray())
        val sb = StringBuffer(bt.size * 2)
        for (b in bt) {
            if ((b.toInt() and 0xff) < 0x0f) {
                sb.append("0")
            }
            sb.append(Integer.toHexString((b.toInt() and 0xff)))
        }
        return sb.toString()
    }

    fun md5File(path: String): String {
        val digest = MessageDigest.getInstance("MD5")
        val ip = FileInputStream(path)
        val by = ByteArray(1024)
        var line: Int
        while (ip.read(by, 0, 1024).also { line = it } != -1) {
            digest.update(by, 0, line)
        }
        val bt = digest.digest()
        val sb = StringBuffer(bt.size * 2)
        for (b in bt) {
            if ((b.toInt() and 0xff) < 0x0f) {
                sb.append("0")
            }
            sb.append(Integer.toHexString((b.toInt() and 0xff)))
        }
        return sb.toString()
    }

    fun md5File(file: File) {
        md5File(file.absolutePath)
    }
}