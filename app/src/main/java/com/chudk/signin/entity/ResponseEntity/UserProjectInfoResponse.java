package com.chudk.signin.entity.ResponseEntity;

import java.util.List;

public class UserProjectInfoResponse implements IResponseEntity {
    private int time;
    private boolean single;
    private int totalSize;
    private List<ProjectInfoResponse> data;
    private String status;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<ProjectInfoResponse> getData() {
        return data;
    }

    public void setData(List<ProjectInfoResponse> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
