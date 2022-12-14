package com.example.carsownersapp;

import android.app.Application;

import java.util.ArrayList;


public class MyApp  extends Application {

    OwnerCarDBService dbService = new OwnerCarDBService();


    ArrayList<Owner> ownerList = new ArrayList<Owner>(0);
    ArrayList<Car> carList = new ArrayList<Car>(0);


    ArrayList<Car> getAllCarsForOneOwner( int ownerID){
        ArrayList <Car> list = new ArrayList<>(0);
        for (Car car :
                carList) {
            if (car.getOwnerID() == ownerID){
                list.add(car);
            }
        }
        return list;

    }
}
