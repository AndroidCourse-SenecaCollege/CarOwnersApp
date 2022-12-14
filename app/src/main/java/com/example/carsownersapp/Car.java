package com.example.carsownersapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Car {
    @PrimaryKey(autoGenerate = true)
    int id;
    int year;
    String model;
    int ownerID;


    public int getOwnerID(){
        return ownerID;
    }
    public Car( int year, String model, int ownerID) {
        this.year = year;
        this.model = model;
        this.ownerID = ownerID;
    }


}
