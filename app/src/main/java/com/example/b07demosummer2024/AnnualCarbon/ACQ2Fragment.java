package com.example.b07demosummer2024.AnnualCarbon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.b07demosummer2024.R;

public class ACQ2Fragment extends LoadFragment{
    public ACQ2Fragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q2, container, false);

        TextView textview = view.findViewById(R.id.Q2Response);

        set(textview, AnnualCarbonInformation.PersonalVehicleUse, AnnualCarbonInformation.PVU, 1);

        Button gasoline = view.findViewById(R.id.Q2Gasoline);
        Button diesel = view.findViewById(R.id.Q2Diesel);
        Button hybrid = view.findViewById(R.id.Q2Hybrid);
        Button electric = view.findViewById(R.id.Q2Electric);
        Button idk = view.findViewById(R.id.Q2IDK);
        Button back = view.findViewById(R.id.Q2Back);
        Button next = view.findViewById(R.id.Q2Next);

        gasoline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnualCarbonInformation.PersonalVehicleUse[1] = 1;
                AnnualCarbonInformation.PVU[1] = "Gasoline";
                set(textview, AnnualCarbonInformation.PersonalVehicleUse, AnnualCarbonInformation.PVU, 1);
            }
        });

        diesel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnualCarbonInformation.PersonalVehicleUse[1] = 2;
                AnnualCarbonInformation.PVU[1] = "Diesel";
                set(textview, AnnualCarbonInformation.PersonalVehicleUse, AnnualCarbonInformation.PVU, 1);
            }
        });

        hybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnualCarbonInformation.PersonalVehicleUse[1] = 3;
                AnnualCarbonInformation.PVU[1] = "Hybrid";
                set(textview, AnnualCarbonInformation.PersonalVehicleUse, AnnualCarbonInformation.PVU, 1);
            }
        });

        electric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnualCarbonInformation.PersonalVehicleUse[1] = 4;
                AnnualCarbonInformation.PVU[1] = "Electric";
                set(textview, AnnualCarbonInformation.PersonalVehicleUse, AnnualCarbonInformation.PVU, 1);
            }
        });

        idk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnualCarbonInformation.PersonalVehicleUse[1] = 5;
                AnnualCarbonInformation.PVU[1] = "I don't know";
                set(textview, AnnualCarbonInformation.PersonalVehicleUse, AnnualCarbonInformation.PVU, 1);
            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ACQ1Fragment());
            }

        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AnnualCarbonInformation.PersonalVehicleUse[1] != 0){
                    loadFragment(new ACQ3Fragment());
                }
            }

        });
        return view;
    }

}