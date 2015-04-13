
package com.android.cesova.common.logger;

public class Log {
    // Grabbing the native values from Android's native logging facilities,
    // to make for easy migration and interop.
    public static final int NONE = -1;
    public static final int VERBOSE = android.util.Log.VERBOSE;
    public static final int DEBUG = android.util.Log.DEBUG;
    public static final int INFO = android.util.Log.INFO;
    public static final int WARN = android.util.Log.WARN;
    public static final int ERROR = android.util.Log.ERROR;
    public static final int ASSERT = android.util.Log.ASSERT;

    // Stores the beginning of the LogNode topology.
    private static LogNode mLogNode;

    public static LogNode getLogNode() {
        return mLogNode;
    }

    public static void setLogNode(LogNode node) {
        mLogNode = node;
    }

    public static void println(int priority, String tag, String msg, Throwable tr) {
        if (mLogNode != null) {
            mLogNode.println(priority, tag, msg, tr);
        }
    }

    public static void println(int priority, String tag, String msg) {
        println(priority, tag, msg, null);
    }

    public static void v(String tag, String msg, Throwable tr) {
        println(VERBOSE, tag, msg, tr);
    }

    public static void v(String tag, String msg) {
        v(tag, msg, null);
    }


    public static void d(String tag, String msg, Throwable tr) {
        println(DEBUG, tag, msg, tr);
    }

    public static void d(String tag, String msg) {
        d(tag, msg, null);
    }

    public static void i(String tag, String msg, Throwable tr) {
        println(INFO, tag, msg, tr);
    }

    public static void i(String tag, String msg) {
        i(tag, msg, null);
    }


    public static void w(String tag, String msg, Throwable tr) {
        println(WARN, tag, msg, tr);
    }

    public static void w(String tag, String msg) {
        w(tag, msg, null);
    }


    public static void w(String tag, Throwable tr) {
        w(tag, null, tr);
    }


    public static void e(String tag, String msg, Throwable tr) {
        println(ERROR, tag, msg, tr);
    }


    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

    public static void wtf(String tag, String msg, Throwable tr) {
        println(ASSERT, tag, msg, tr);
    }

    public static void wtf(String tag, String msg) {
        wtf(tag, msg, null);
    }


    public static void wtf(String tag, Throwable tr) {
        wtf(tag, null, tr);
    }
}
