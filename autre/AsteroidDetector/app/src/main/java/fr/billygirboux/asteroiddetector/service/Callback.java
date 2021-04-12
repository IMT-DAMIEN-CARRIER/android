package fr.billygirboux.asteroiddetector.service;

public interface Callback<T> {

    void onResponse(T t);
}
