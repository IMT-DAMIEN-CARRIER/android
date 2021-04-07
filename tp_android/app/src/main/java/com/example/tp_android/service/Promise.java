package com.example.tp_android.service;

public class Promise<T> {
    CallbackInterface<T> promiseThen;
    CallbackInterface<String> promiseCatch;

    public Promise<T> then(CallbackInterface<T> promiseThen)
    {
        this.promiseThen = promiseThen;

        return this;
    }

    public Promise<T> catchError(CallbackInterface<String> promiseCatch)
    {
        this.promiseCatch = promiseCatch;

        return this;
    }
}
