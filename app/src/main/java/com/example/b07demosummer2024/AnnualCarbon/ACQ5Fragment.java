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


public class ACQ5Fragment extends LoadFragment {

    public ACQ5Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q5, container, false);

        TextView textview = view.findViewById(R.id.Q5Response);
        set(textview, AnnualCarbonInformation.PublicTransportation, AnnualCarbonInformation.PT, 1);

        Button one = view.findViewById(R.id.Q5One);
        Button three = view.findViewById(R.id.Q5Three);
        Button five = view.findViewById(R.id.Q5Five);
        Button ten = view.findViewById(R.id.Q5Ten);
        Button gten = view.findViewById(R.id.Q5G10);
        Button next = view.findViewById(R.id.Q5Next);
        Button back = view.findViewById(R.id.Q5Back);

        one.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.PublicTransportation[1] = 1;
                AnnualCarbonInformation.PT[1] = "Under 1 Hour";
                set(textview, AnnualCarbonInformation.PublicTransportation, AnnualCarbonInformation.PT, 1);
            }
        });

        three.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.PublicTransportation[1] = 2;
                AnnualCarbonInformation.PT[1] = "1-3 Hours";
                set(textview, AnnualCarbonInformation.PublicTransportation, AnnualCarbonInformation.PT, 1);
            }
        });

        five.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.PublicTransportation[1] = 3;
                AnnualCarbonInformation.PT[1] = "3-5 Hours";
                set(textview, AnnualCarbonInformation.PublicTransportation, AnnualCarbonInformation.PT, 1);
            }
        });

        ten.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.PublicTransportation[1] = 4;
                AnnualCarbonInformation.PT[1] = "5-10 Hours";
                set(textview, AnnualCarbonInformation.PublicTransportation, AnnualCarbonInformation.PT, 1);
            }
        });

        gten.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.PublicTransportation[1] = 5;
                AnnualCarbonInformation.PT[1] = "More than 10 Hours";
                set(textview, AnnualCarbonInformation.PublicTransportation, AnnualCarbonInformation.PT, 1);
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.PublicTransportation[1] != 0){
                    loadFragment(new ACQ6Fragment());
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                loadFragment(new ACQ4Fragment());
            }
        });

        return view;
    }
}