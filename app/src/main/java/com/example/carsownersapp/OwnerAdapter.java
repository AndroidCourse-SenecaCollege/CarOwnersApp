package com.example.carsownersapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OwnerAdapter extends
        RecyclerView.Adapter<OwnerAdapter.OwnerCarsViewHolder> {

    interface AdapterListner {
        void OwnerAdapterClickListener(Owner selectedOwner);
    }

    private Context mCtx;
    public List<Owner> ownerList;
    AdapterListner listner;

    public OwnerAdapter(Context mCtx, List<Owner> ownerList) {
        this.mCtx = mCtx;
        this.ownerList = ownerList;
    }
    @Override
    public OwnerCarsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.owner_item, parent, false);
        return new OwnerCarsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OwnerCarsViewHolder holder, int position) {
            Owner t = ownerList.get(position);
            holder.cityTextView.setText(t.name);

    }

    @Override
    public int getItemCount() {
            return ownerList.size();
        }
    class OwnerCarsViewHolder extends
            RecyclerView.ViewHolder implements
            View.OnClickListener {

        TextView cityTextView, countryTextView;

        public OwnerCarsViewHolder(View itemView) {
            super(itemView);

            cityTextView = itemView.findViewById(R.id.ownername);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Owner owner = ownerList.get(getAdapterPosition());
            listner.OwnerAdapterClickListener(owner);
        }
    }

}

