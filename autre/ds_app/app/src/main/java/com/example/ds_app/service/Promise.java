package com.example.ds_app.service;

public class Promise<T> {

    OnSuccess<T> onSuccess;
    OnError onError;

    public Promise<T> then(OnSuccess<T> onSuccess) {
        this.onSuccess = onSuccess;
        return this;
    }

    public Promise<T> catchError(OnError onError) {
        this.onError = onError;
        return this;
    }

}
