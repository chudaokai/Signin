package com.chudk.signin.entity.ResponseEntity;

public class ProjectInfoResponse implements IResponseEntity {
    private String id;
    private String objname;
    private String objname2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjname() {
        return objname;
    }

    public void setObjname(String objname) {
        this.objname = objname;
    }

    public String getObjname2() {
        return objname2;
    }

    public void setObjname2(String objname2) {
        this.objname2 = objname2;
    }
}
