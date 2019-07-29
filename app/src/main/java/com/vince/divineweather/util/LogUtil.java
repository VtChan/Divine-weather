package com.vince.divineweather.util;

import android.util.Log;

/**
 * Created by : vince
 * Created at : 2019/7/28
 * Desc :在开发阶段将日志级别指定成VERBOSE，当项目正式上线时将level指定成NOTHING。
 *      这样用于调试的日志在项目上线时不会打印出来，提高程序效率，数据保密性也会
 *      好一点。
 * Version : 1.0
 */
public class LogUtil {
    public static final int VERBOSE = 1;

    public static final int DEBUG = 2;

    public static final int INFO = 3;

    public static final int WARN = 4;

    public static final int ERROR = 5;

    public static final int NOTHING = 6;

    public static int level = VERBOSE;

    public static void v(String tag, String msg){
        if (level <= VERBOSE){
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if (level <= DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg){
        if (level <= INFO){
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg){
        if (level <= WARN){
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if (level <= ERROR){
            Log.e(tag, msg);
        }
    }
}
