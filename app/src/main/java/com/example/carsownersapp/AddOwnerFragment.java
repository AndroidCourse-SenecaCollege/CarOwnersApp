package com.example.carsownersapp;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class AddOwnerFragment  extends DialogFragment {

        interface AddOwnerFragmentListener{
            void addNewOwner(String ownerName);
        }

        AddOwnerFragmentListener listener;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.add_owner_fragment, container, false);
            EditText task = v.findViewById(R.id.name_text);
            Button save = v.findViewById(R.id.save_id);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!task.getText().toString().isEmpty()) {
                        listener.addNewOwner(task.getText().toString());
                        dismiss();
                    }
                }
            });
            return v;
        }

}
