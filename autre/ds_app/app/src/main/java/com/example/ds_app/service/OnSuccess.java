package com.example.ds_app.service;

import com.example.ds_app.modele.Sol;

import java.util.ArrayList;

public interface OnSuccess<T> {
    void treatResponse(ArrayList<Sol> t);
}