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


public class ACQ21Fragment extends LoadFragment {

    public ACQ21Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q21, container, false);

        TextView textview = view.findViewById(R.id.Q21Response);
        set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 3);

        Button never = view.findViewById(R.id.Q21Never);
        Button occ = view.findViewById(R.id.Q21Occ);
        Button freq = view.findViewById(R.id.Q21Frequently);
        Button always = view.findViewById(R.id.Q21Always);
        Button back = view.findViewById(R.id.Q21Back);
        Button next = view.findViewById(R.id.Q21Next);

        never.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Consumption[3] = 1;
                AnnualCarbonInformation.C[3] = "Never";
                set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 3);
            }
        });

        occ.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Consumption[3] = 2;
                AnnualCarbonInformation.C[3] = "Occasionally";
                set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 3);
            }
        });

        freq.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Consumption[3] = 3;
                AnnualCarbonInformation.C[3] = "Frequently";
                set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 3);
            }
        });

        always.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Consumption[3] = 4;
                AnnualCarbonInformation.C[3] = "Always";
                set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 3);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                loadFragment(new ACQ20Fragment());
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.Consumption[3] != 0){
                    // loadFragment(new ACQFinishFragment());
                }
            }
        });
        return view;
    }
}