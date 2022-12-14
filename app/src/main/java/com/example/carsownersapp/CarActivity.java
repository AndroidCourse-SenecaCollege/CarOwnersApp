package com.example.carsownersapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CarActivity extends AppCompatActivity implements
        OwnerCarDBService.DBCallBackInterface,
        CarsAdapter.AlertDialogListner, AddNewCarFragment.AddCarFragmentListener
{

RecyclerView carlist;
ArrayList<Car> carArrayList;
CarsAdapter adapter;
OwnerCars OwnersCarsObject;
    OwnerCarDBService dbService;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        id = getIntent().getExtras().getInt("ownerid");
        String name = getIntent().getExtras().getString("ownername");
        this.setTitle(name + "'s Cars");
        carArrayList = ((MyApp)getApplication()).getAllCarsForOneOwner(id);

        adapter = new CarsAdapter(this,new ArrayList<>(0));
        adapter.carList = carArrayList;
        carlist = findViewById(R.id.carlist);
        carlist.setAdapter(adapter);


        carlist.setLayoutManager(new LinearLayoutManager(this));
        dbService = ((MyApp)getApplication()).dbService;
        dbService.getInstance(this);
        dbService.listener = this;
        dbService.getAllCarsForOwner(id);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(carlist);


        FloatingActionButton fab = findViewById(R.id.addCarFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddNewCarFragment fragment = AddNewCarFragment.buildFragment("Enter New Car As A gift");

               // AddNewCarFragment fragment = new AddNewCarFragment();
               fragment.show(getSupportFragmentManager().beginTransaction(),"1");
                fragment.listener = CarActivity.this;
            }
        });
       }

    @Override
    protected void onResume() {
        super.onResume();
        dbService.listener = this;
    }

    @Override
    public void CarAdapterClickListener(Car selectedCar) {
        Toast.makeText(this,"Selected Car is "+ selectedCar.model,Toast.LENGTH_LONG).show();
    }

    @Override
    public void addNewCar(String carModel, int year) {
        dbService.insertNewCarAsync(carModel,year,id);
       // Car c = new Car(year,carModel,id);
        carArrayList.add(new Car(year,carModel,id));
    }








    // table view deleget
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.DOWN, ItemTouchHelper.LEFT |
            ItemTouchHelper.RIGHT |
            ItemTouchHelper.DOWN |
            ItemTouchHelper.UP) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Toast.makeText(CarActivity.this, "Item Moveing", Toast.LENGTH_SHORT).show();

            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView
            int position = viewHolder.getAdapterPosition();
            //dbService.deleteCar(OwnersCarsObject.cars.get(position));
            //dbService.getAllCarsForOwner(id);
            carArrayList.remove(position);
            adapter.carList = carArrayList;
           // adapter.carList.remove(position);
            // we have to remove it from db as well

            adapter.notifyDataSetChanged();

        }
    };

    @Override
    public void OwnerInserted() {

    }

    @Override
    public void listOfOwnersFormDB(List<Owner> list) {

    }

    @Override
    public void OwnerDeleted() {

    }

    @Override
    public void carInserted() {

    }

    @Override
    public void carsForOneOwnerCompleted(OwnerCar oc) {
        Log.d("cars for owner",oc.carList.size() + "");
        carArrayList = (ArrayList<Car>) oc.carList;
        adapter.carList = carArrayList;
        adapter.notifyDataSetChanged();
    }
}