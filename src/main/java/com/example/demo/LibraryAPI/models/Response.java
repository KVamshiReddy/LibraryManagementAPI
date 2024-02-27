package com.example.demo.LibraryAPI.models;

public class Response {

    private Object data;

    public Response() {
        super();
    }

    public Response(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
