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

public class ACQ4Fragment extends LoadFragment{
    public ACQ4Fragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q4, container, false);
        TextView textview = view.findViewById(R.id.Q4Response);
        set(textview, AnnualCarbonInformation.PublicTransportation, AnnualCarbonInformation.PT, 0);

        Button never = view.findViewById(R.id.Q4Never);
        Button occasionally = view.findViewById(R.id.Q4Occasionally);
        Button frequently = view.findViewById(R.id.Q4Frequently);
        Button always = view.findViewById(R.id.Q4Always);
        Button back = view.findViewById(R.id.Q4Back);
        Button next = view.findViewById(R.id.Q4Next);

        never.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.PublicTransportation[0] = 1;
                AnnualCarbonInformation.PT[0] = "Never";
                set(textview, AnnualCarbonInformation.PublicTransportation, AnnualCarbonInformation.PT, 0);
            }
        });

        occasionally.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.PublicTransportation[0] = 2;
                AnnualCarbonInformation.PT[0] = "Occasionally";
                set(textview, AnnualCarbonInformation.PublicTransportation, AnnualCarbonInformation.PT, 0);
            }
        });

        frequently.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.PublicTransportation[0] = 3;
                AnnualCarbonInformation.PT[0] = "Frequently";
                set(textview, AnnualCarbonInformation.PublicTransportation, AnnualCarbonInformation.PT, 0);
            }
        });

        always.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.PublicTransportation[0] = 4;
                AnnualCarbonInformation.PT[0] = "Always";
                set(textview, AnnualCarbonInformation.PublicTransportation, AnnualCarbonInformation.PT, 0);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.PersonalVehicleUse[0] == 2){
                    loadFragment(new ACQ1Fragment());
                }

                else{
                    loadFragment(new ACQ3Fragment());
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.PublicTransportation[0] != 0){
                    loadFragment(new ACQ5Fragment());
                }
            }
        });

        return view;
    }

}