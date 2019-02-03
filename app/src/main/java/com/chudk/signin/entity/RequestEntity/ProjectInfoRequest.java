package com.chudk.signin.entity.RequestEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProjectInfoRequest implements IRequestEntity {
    private String fieldname_pid;
    private String fieldname_pname;
    private String fieldname_startdate;
    private String fieldname_timeworked;
    private String fieldname_worklogbody;

    public ProjectInfoRequest(Date dt){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.setFieldname_startdate(format.format(dt));
    }

    public String getFieldname_pid() {
        return fieldname_pid;
    }

    public void setFieldname_pid(String fieldname_pid) {
        this.fieldname_pid = fieldname_pid;
    }

    public String getFieldname_pname() {
        return fieldname_pname;
    }

    public void setFieldname_pname(String fieldname_pname) {
        this.fieldname_pname = fieldname_pname;
    }

    public String getFieldname_startdate() {
        return fieldname_startdate;
    }

    public void setFieldname_startdate(String fieldname_startdate) {
        this.fieldname_startdate = fieldname_startdate;
    }

    public String getFieldname_timeworked() {
        return fieldname_timeworked;
    }

    public void setFieldname_timeworked(String fieldname_timeworked) {
        this.fieldname_timeworked = fieldname_timeworked;
    }

    public String getFieldname_worklogbody() {
        return fieldname_worklogbody;
    }

    public void setFieldname_worklogbody(String fieldname_worklogbody) {
        this.fieldname_worklogbody = fieldname_worklogbody;
    }

    public String toString(){
        String str = "\"{\"fieldname_pid\":\"\",\"fieldname_pname\":\"\",\"fieldname_startdate\":\""+getFieldname_timeworked()+"\",\"fieldname_timeworked\":\"\",\"fieldname_worklogbody\":\"\"}\"";
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }
}
