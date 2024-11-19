package com.example.b07demosummer2024.AnnualCarbon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Switch;

import com.example.b07demosummer2024.R;


public class ACQ6Fragment extends LoadFragment {

    public ACQ6Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q6, container, false);

        TextView textview = view.findViewById(R.id.Q6Response);
        set(textview, AnnualCarbonInformation.AirTravel, AnnualCarbonInformation.AT, 0);

        Button none = view.findViewById(R.id.Q6None);
        Button one = view.findViewById(R.id.Q6One);
        Button three = view.findViewById(R.id.Q6Three);
        Button six = view.findViewById(R.id.Q6Six);
        Button ten = view.findViewById(R.id.Q6Ten);
        Button next = view.findViewById(R.id.Q6Next);
        Button back = view.findViewById(R.id.Q6Back);

        none.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.AirTravel[0] = 1;
                AnnualCarbonInformation.AT[0] = "None";
                set(textview, AnnualCarbonInformation.AirTravel, AnnualCarbonInformation.AT, 0);
            }
        });

        one.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.AirTravel[0] = 2;
                AnnualCarbonInformation.AT[0] = "1-2 Flights";
                set(textview, AnnualCarbonInformation.AirTravel, AnnualCarbonInformation.AT, 0);
            }
        });

        three.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.AirTravel[0] = 3;
                AnnualCarbonInformation.AT[0] = "3-5 Flights";
                set(textview, AnnualCarbonInformation.AirTravel, AnnualCarbonInformation.AT, 0);
            }
        });

        six.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.AirTravel[0] = 4;
                AnnualCarbonInformation.AT[0] = "6-10 Flights";
                set(textview, AnnualCarbonInformation.AirTravel, AnnualCarbonInformation.AT, 0);
            }
        });

        ten.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.AirTravel[0] = 5;
                AnnualCarbonInformation.AT[0] = "More than 10 Flights";
                set(textview, AnnualCarbonInformation.AirTravel, AnnualCarbonInformation.AT, 0);
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.AirTravel[0] != 0){
                    loadFragment(new ACQ7Fragment());
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                loadFragment(new ACQ5Fragment());
            }
        });

        return view;
    }
}