package com.android.cesova.obd.ErrorCodes;

/**
 * Created by mokshaDev on 3/21/2015.
 */
public class OBDErrorCode {

    public static final String TABLE = "OBDErrorCode";

    // Labels Table Columns names
    public static final String KEY_PID = "pid";
    public static final String KEY_TYPE = "type";
    public static final String KEY_DESCRIPTION = "description";

    public static String pid;
    public static String type;
    public static String description;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        OBDErrorCode.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        OBDErrorCode.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        OBDErrorCode.description = description;
    }
}
