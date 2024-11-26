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


public class ACQ20Fragment extends LoadFragment {

    public ACQ20Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q20, container, false);

        TextView textview = view.findViewById(R.id.Q20Response);
        set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 2);

        Button zero = view.findViewById(R.id.Q20Zero);
        Button one = view.findViewById(R.id.Q20One);
        Button two = view.findViewById(R.id.Q20Two);
        Button three = view.findViewById(R.id.Q20Three);
        Button back = view.findViewById(R.id.Q20Back);
        Button next = view.findViewById(R.id.Q20Next);

        zero.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Consumption[2] = 1;
                AnnualCarbonInformation.C[2] = "0";
                set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 2);
            }
        });

        one.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Consumption[2] = 2;
                AnnualCarbonInformation.C[2] = "1";
                set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 2);
            }
        });

        two.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Consumption[2] = 3;
                AnnualCarbonInformation.C[2] = "2";
                set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 2);
            }
        });

        three.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Consumption[2] = 4;
                AnnualCarbonInformation.C[2] = "3";
                set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 2);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                loadFragment(new ACQ19Fragment());
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.Consumption[2] != 0){
                    loadFragment(new ACQ21Fragment());
                }
            }
        });

        return view;
    }
}