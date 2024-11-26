package com.example.b07demosummer2024.AnnualCarbon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.b07demosummer2024.R;


public class ACQ14Fragment extends LoadFragment {


    public ACQ14Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q14, container, false);

        TextView textview = view.findViewById(R.id.Q14Response);
        set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 3);

        Button gas = view.findViewById(R.id.Q14Gas);
        Button electricity = view.findViewById(R.id.Q14Electricity);
        Button oil = view.findViewById(R.id.Q14Oil);
        Button propane = view.findViewById(R.id.Q14Propane);
        Button wood = view.findViewById(R.id.Q14Wood);
        Button other = view.findViewById(R.id.Q14Other);
        Button back = view.findViewById(R.id.Q14Back);
        Button next = view.findViewById(R.id.Q14Next);

        gas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[3] = 1;
                AnnualCarbonInformation.H[3] = "Natural Gas";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 3);
            }
        });

        electricity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[3] = 2;
                AnnualCarbonInformation.H[3] = "Electricity";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 3);
            }
        });

        oil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[3] = 3;
                AnnualCarbonInformation.H[3] = "Oil";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 3);
            }
        });

        propane.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[3] = 4;
                AnnualCarbonInformation.H[3] = "Propane";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 3);
            }
        });

        wood.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[3] = 5;
                AnnualCarbonInformation.H[3] = "Wood";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 3);
            }
        });

        other.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[3] = 6;
                AnnualCarbonInformation.H[3] = "Other";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 3);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                loadFragment(new ACQ13Fragment());
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.Housing[3] != 0){
                    loadFragment(new ACQ15Fragment());
                }
            }
        });


        return view;
    }
}