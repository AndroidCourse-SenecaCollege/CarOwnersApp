package com.example.carsownersapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Owner {
    @PrimaryKey(autoGenerate = true)
    int owner_id;
    String name;

    public int getOwner_id() {
        return owner_id;
    }

    public String getName() {
        return name;
    }

    public Owner( String name) {
        this.name = name;
    }

}
