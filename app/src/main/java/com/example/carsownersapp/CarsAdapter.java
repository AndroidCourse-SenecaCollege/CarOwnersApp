package com.example.carsownersapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarsAdapter extends
        RecyclerView.Adapter<CarsAdapter.OwnerCarsViewHolder> {

    interface AlertDialogListner {
        void CarAdapterClickListener(Car selectedCar);
    }

    private Context mCtx;
    public List<Car> carList;
    AlertDialogListner listner;

    public CarsAdapter(Context mCtx, List<Car> carList) {
        this.mCtx = mCtx;
        this.carList = carList;
    }
    @Override
    public OwnerCarsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.car_item, parent, false);
        return new OwnerCarsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OwnerCarsViewHolder holder, int position) {
            Car t = carList.get(position);
            holder.modelTextView.setText(t.model);
            holder.yearTextView.setText(t.year+"");

    }

    @Override
    public int getItemCount() {
            return carList.size();
        }
    class OwnerCarsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView modelTextView, yearTextView;

        public OwnerCarsViewHolder(View itemView) {
            super(itemView);

            modelTextView = itemView.findViewById(R.id.model);
            yearTextView = itemView.findViewById(R.id.year);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Car car = carList.get(getAdapterPosition());
            listner.CarAdapterClickListener(car);
        }
    }



}

