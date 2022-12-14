package com.example.carsownersapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        AddOwnerFragment.AddOwnerFragmentListener,
        OwnerAdapter.AdapterListner, OwnerCarDBService.DBCallBackInterface
{
RecyclerView ownerList;
ArrayList<Owner> listOfOwners;
    OwnerAdapter adapter;
   OwnerCarDBService dbService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbService = ((MyApp)getApplication()).dbService;
        dbService.getInstance(this);
        dbService.listener = this;
        dbService.getAllCarsAsync();
        ownerList = findViewById(R.id.ownerlist);
        ownerList.setLayoutManager(new LinearLayoutManager(this));

        dbService.getAllOwners();

        adapter = new OwnerAdapter(this,new ArrayList<>(0));
        ownerList.setAdapter(adapter);
        adapter.listner = this;


        listOfOwners = ((MyApp)getApplication()).ownerList;
        adapter.ownerList = listOfOwners;

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(ownerList);

        FloatingActionButton fab = findViewById(R.id.addOwnerFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddOwnerFragment fragment = new AddOwnerFragment();
                fragment.show(getSupportFragmentManager().beginTransaction(),"1");
                fragment.listener = MainActivity.this;
            }
        });
    }



    @Override
    public void OwnerAdapterClickListener(Owner selectedOwner) {
        Intent intent = new Intent(this,CarActivity.class);
        intent.putExtra("ownerid",selectedOwner.owner_id);
        intent.putExtra("ownername",selectedOwner.name);

        startActivity(intent);
    }


    @Override
    public void addNewOwner(String ownerName) {

        dbService.insertNewOwner(ownerName);



    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
            new ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView
            int position = viewHolder.getAdapterPosition();
           // listOfOwners.remove(position);
            dbService.deleteOwnerAndCars(adapter.ownerList.get(position));



        }
    };

    @Override
    public void OwnerInserted() {
        // db service and get all owners
        dbService.getAllOwners();


    }
    @Override
    public void OwnerDeleted() {
        dbService.getAllOwners();
    }

    @Override
    public void carInserted() {

    }

    @Override
    public void carsForOneOwnerCompleted(OwnerCar oc) {

    }

    @Override
    public void listOfOwnersFormDB(List<Owner> list) {
        adapter.ownerList = list;
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbService = ((MyApp)getApplication()).dbService;
        dbService.listener = this;
    }
}