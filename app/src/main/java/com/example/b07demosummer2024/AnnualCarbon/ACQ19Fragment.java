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


public class ACQ19Fragment extends LoadFragment {

    public ACQ19Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q19, container, false);

        TextView textview = view.findViewById(R.id.Q19Response);
        set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 1);

        Button reg = view.findViewById(R.id.Q19Reg);
        Button occ = view.findViewById(R.id.Q19Occ);
        Button no = view.findViewById(R.id.Q19No);
        Button back = view.findViewById(R.id.Q19Back);
        Button next = view.findViewById(R.id.Q19Next);

        reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Consumption[1] = 1;
                AnnualCarbonInformation.C[1] = "Yes, regularly";
                set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 1);
            }
        });

        occ.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Consumption[1] = 2;
                AnnualCarbonInformation.C[1] = "Yes, occasionally";
                set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 1);
            }
        });

        no.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Consumption[1] = 3;
                AnnualCarbonInformation.C[1] = "No";
                set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 1);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                loadFragment(new ACQ18Fragment());
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.Consumption[1] != 0){
                    loadFragment(new ACQ20Fragment());
                }
            }
        });

        return view;
    }
}