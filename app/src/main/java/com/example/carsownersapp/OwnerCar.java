package com.example.carsownersapp;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class OwnerCar {

        @Embedded
        public Owner owner;
        @Relation(
                parentColumn = "owner_id",
                entityColumn = "ownerID"
        )
        public List<Car> carList;
}
