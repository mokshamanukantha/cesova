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

    public static final String KEY_SYMPTOMS = "symptom";
    public static final String KEY_CAUSES = "cause";
    public static final String KEY_SOLUTIONS = "solution";
    public static final String KEY_RELATED = "related";


    public static String pid;
    public static String type;
    public static String description;

    public static String symptom;
    public static String cause;
    public static String solution;
    public static String related;

    public OBDErrorCode()
    {

    }

    public OBDErrorCode(String pid,String type,String description)
    {
        this.setPid(pid);
        this.setType(type);
        this.setDescription(description);
    }
    public static String getSymptom() {
        return symptom;
    }

    public static void setSymptom(String symptom) {
        OBDErrorCode.symptom = symptom;
    }

    public static String getCause() {
        return cause;
    }

    public static void setCause(String cause) {
        OBDErrorCode.cause = cause;
    }

    public static String getSolution() {
        return solution;
    }

    public static void setSolution(String solution) {
        OBDErrorCode.solution = solution;
    }

    public static String getRelated() {
        return related;
    }

    public static void setRelated(String related) {
        OBDErrorCode.related = related;
    }

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
