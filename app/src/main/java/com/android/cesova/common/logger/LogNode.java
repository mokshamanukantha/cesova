
package com.android.cesova.common.logger;

public interface LogNode {

    void println(int priority, String tag, String msg, Throwable tr);

}
