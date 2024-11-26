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

public class ACQ1Fragment extends LoadFragment {

    public ACQ1Fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q1, container, false);
        TextView textview = view.findViewById(R.id.Q1Response);
        set(textview, AnnualCarbonInformation.PersonalVehicleUse, AnnualCarbonInformation.PVU, 0);
        Button yesbutton = view.findViewById(R.id.Q1Yes);
        yesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnualCarbonInformation.PersonalVehicleUse[0] = 1;
                AnnualCarbonInformation.PVU[0] = "Yes";
                set(textview, AnnualCarbonInformation.PersonalVehicleUse, AnnualCarbonInformation.PVU, 0);
            }
        });

        Button nobutton = view.findViewById(R.id.Q1No);
        nobutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AnnualCarbonInformation.PersonalVehicleUse[0] = 2;
                AnnualCarbonInformation.PVU[0] = "No";
                set(textview, AnnualCarbonInformation.PersonalVehicleUse, AnnualCarbonInformation.PVU, 0);
                for (int i = 1; i <= 2; i++){
                    AnnualCarbonInformation.PersonalVehicleUse[i] = 0;
                    AnnualCarbonInformation.PVU[i] = null;
                }
            }
        });

        Button nextbutton = view.findViewById(R.id.Q1Next);
        nextbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if (AnnualCarbonInformation.PersonalVehicleUse[0] == 1) {
                    loadFragment(new ACQ2Fragment());
                }

                else if (AnnualCarbonInformation.PersonalVehicleUse[0] == 2){
                    loadFragment(new ACQ4Fragment());
                }

            }
        });

        return view;

    }


}