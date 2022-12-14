package com.example.carsownersapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OwnerCarDBService {

    interface DBCallBackInterface{
        void OwnerInserted();
        void listOfOwnersFormDB(List<Owner> list);
        void OwnerDeleted();
        void carInserted() ;
        void carsForOneOwnerCompleted(OwnerCar oc);
    }

    OwnerCarsDB db;
    DBCallBackInterface listener;
    ExecutorService dbExecutor = Executors.newFixedThreadPool(4);
    Handler dbHandler = new Handler(Looper.getMainLooper());

   public OwnerCarsDB getInstance(Context context){
       if (db == null) {
           db = Room.databaseBuilder(context.getApplicationContext(),
                   OwnerCarsDB.class, "owners_cars_db").build();
       }
       return db;
   }

   public void insertNewOwner(String  name){
       dbExecutor.execute(new Runnable() {
           @Override
           public void run() {
               db.getDao().insertNewOwner(new Owner(name));
               dbHandler.post(new Runnable() {
                   @Override
                   public void run() {
                       listener.OwnerInserted();
                   }
               });

           }
       });
   }


    public void insertNewCarAsync(String  model, int year, int oID){
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.getDao().insertCarForOwner(oID,year,model);
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.carInserted();
                    }
                });

            }
        });
    }

   public void getAllOwners(){
       dbExecutor.execute(new Runnable() {
           @Override
           public void run() {
               List<Owner> list = db.getDao().getAllOwners();
               dbHandler.post(new Runnable() {
                   @Override
                   public void run() {
                       listener.listOfOwnersFormDB(list);
                   }
               });

           }
       });
   }

    public void getAllCarsAsync(){
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Car[] list = db.getDao().getAllCars();
                Log.d("list",list.length + "");
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                    }
                });

            }
        });
    }


    public void getAllCarsForOwner(int oid){
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                OwnerCar ownerCar = db.getDao().getAllCarsForOwner(oid);
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.carsForOneOwnerCompleted(ownerCar);
                    }
                });

            }
        });
    }

public void deleteOwnerAndCars(Owner todelete){
    dbExecutor.execute(new Runnable() {
        @Override
        public void run() {
           db.getDao().deleteAllCarsForOwner(todelete.owner_id);
           db.getDao().deleteOwner(todelete);
            dbHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.OwnerDeleted();
                }
            });

        }
    });
}

}
