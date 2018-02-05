package com.example.demo.model;

public class GenericRequest<T> {
    private T request;

    public T getRequest() {
        return request;
    }

    public void setRequest(T request) {
        this.request = request;
    }
}
