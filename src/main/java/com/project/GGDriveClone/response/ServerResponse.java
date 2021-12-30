package com.project.GGDriveClone.response;

public class ServerResponse {
    private ResponseStatus status;
    private Object data;

    public ServerResponse(ResponseStatus responseStatus) {
        this.status = responseStatus;
    }

    public ServerResponse(ResponseStatus responseStatus, Object data) {
        this.status = responseStatus;
        this.data = data;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
