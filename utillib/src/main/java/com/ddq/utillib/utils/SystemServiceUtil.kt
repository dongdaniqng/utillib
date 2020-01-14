package com.ddq.utillib.utils

import android.app.NotificationManager
import android.content.Context
import com.ddq.utillib.UtilInit

/**
 * Author : ddq
 * Time : 2019/11/8 11:24
 * Description :system service manager
 */

/**notification*/
val Ext_NotificationManager = UtilInit.getContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

/**packageManager*/
val Ext_PackageManager = UtilInit.getContext().packageManager