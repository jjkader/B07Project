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


public class ACQ16Fragment extends LoadFragment {

    public ACQ16Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q16, container, false);


        TextView textview = view.findViewById(R.id.Q16Response);
        set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 5);

        Button gas = view.findViewById(R.id.Q16Gas);
        Button electricity = view.findViewById(R.id.Q16Electricity);
        Button oil = view.findViewById(R.id.Q16Oil);
        Button propane = view.findViewById(R.id.Q16Propane);
        Button solar = view.findViewById(R.id.Q16Solar);
        Button other = view.findViewById(R.id.Q16Other);
        Button back = view.findViewById(R.id.Q16Back);
        Button next = view.findViewById(R.id.Q16Next);

        gas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[5] = 1;
                AnnualCarbonInformation.H[5] = "Natural Gas";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 5);
            }
        });

        electricity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[5] = 2;
                AnnualCarbonInformation.H[5] = "Electricity";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 5);
            }
        });

        oil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[5] = 3;
                AnnualCarbonInformation.H[5] = "Oil";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 5);
            }
        });

        propane.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[5] = 4;
                AnnualCarbonInformation.H[5] = "Propane";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 5);
            }
        });

        solar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[5] = 5;
                AnnualCarbonInformation.H[5] = "Solar";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 5);
            }
        });

        other.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[5] = 6;
                AnnualCarbonInformation.H[5] = "Other";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 5);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                loadFragment(new ACQ15Fragment());
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.Housing[5] != 0){
                    loadFragment(new ACQ17Fragment());
                }
            }
        });
        return view;
    }
}