package fr.billygirboux.asteroiddetector.service;

public class Promise<T> {

    Callback<T> promiseThen;
    Callback<String> promiseCatch;

    public Promise<T> then(Callback<T> promiseThen) {
        this.promiseThen = promiseThen;
        return this;
    }

    public Promise<T> catchError(Callback<String> promiseCatch) {
        this.promiseCatch = promiseCatch;
        return this;
    }

}
