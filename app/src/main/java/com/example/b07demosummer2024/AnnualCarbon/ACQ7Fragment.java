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


public class ACQ7Fragment extends LoadFragment {


    public ACQ7Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q7, container, false);

        TextView textview = view.findViewById(R.id.Q7Response);
        set(textview, AnnualCarbonInformation.AirTravel, AnnualCarbonInformation.AT, 1);

        Button none = view.findViewById(R.id.Q7None);
        Button one = view.findViewById(R.id.Q7One);
        Button three = view.findViewById(R.id.Q7Three);
        Button six = view.findViewById(R.id.Q7Six);
        Button ten = view.findViewById(R.id.Q7Ten);
        Button next = view.findViewById(R.id.Q7Next);
        Button back = view.findViewById(R.id.Q7Back);

        none.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.AirTravel[1] = 1;
                AnnualCarbonInformation.AT[1] = "None";
                set(textview, AnnualCarbonInformation.AirTravel, AnnualCarbonInformation.AT, 1);
            }
        });

        one.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.AirTravel[1] = 2;
                AnnualCarbonInformation.AT[1] = "1-2 Flights";
                set(textview, AnnualCarbonInformation.AirTravel, AnnualCarbonInformation.AT, 1);
            }
        });

        three.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.AirTravel[1] = 3;
                AnnualCarbonInformation.AT[1] = "3-5 Flights";
                set(textview, AnnualCarbonInformation.AirTravel, AnnualCarbonInformation.AT, 1);
            }
        });

        six.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.AirTravel[1] = 4;
                AnnualCarbonInformation.AT[1] = "6-10 Flights";
                set(textview, AnnualCarbonInformation.AirTravel, AnnualCarbonInformation.AT, 1);
            }
        });

        ten.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.AirTravel[1] = 5;
                AnnualCarbonInformation.AT[1] = "More than 10 Flights";
                set(textview, AnnualCarbonInformation.AirTravel, AnnualCarbonInformation.AT, 1);
            }
        });


        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                loadFragment(new ACQ6Fragment());
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.AirTravel[1] != 0){
                    //loadFragment(new ACQ8Fragment());
                }
            }
        });
        return view;
    }
}