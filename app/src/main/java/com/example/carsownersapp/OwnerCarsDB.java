package com.example.carsownersapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1,entities = {Car.class,Owner.class})
abstract public class OwnerCarsDB extends RoomDatabase {
    public abstract CarDAO getDao();
}
