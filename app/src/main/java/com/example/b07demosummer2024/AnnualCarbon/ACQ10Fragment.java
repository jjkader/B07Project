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


public class ACQ10Fragment extends LoadFragment {


    public ACQ10Fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q10, container, false);

        TextView textview = view.findViewById(R.id.Q10Response);
        set(textview, AnnualCarbonInformation.Food, AnnualCarbonInformation.F, 5);

        Button never = view.findViewById(R.id.Q10Never);
        Button rarely = view.findViewById(R.id.Q10Rarely);
        Button occ = view.findViewById(R.id.Q10Occasionally);
        Button freq = view.findViewById(R.id.Q10Frequently);
        Button back = view.findViewById(R.id.Q10Back);
        Button next = view.findViewById(R.id.Q10Next);

        never.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Food[5] = 1;
                AnnualCarbonInformation.F[5] = "Never";
                set(textview, AnnualCarbonInformation.Food, AnnualCarbonInformation.F, 5);
            }
        });

        rarely.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Food[5] = 2;
                AnnualCarbonInformation.F[5] = "Rarely";
                set(textview, AnnualCarbonInformation.Food, AnnualCarbonInformation.F, 5);
            }
        });

        occ.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Food[5] = 3;
                AnnualCarbonInformation.F[5] = "Occasionally";
                set(textview, AnnualCarbonInformation.Food, AnnualCarbonInformation.F, 5);
            }
        });

        freq.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Food[5] = 4;
                AnnualCarbonInformation.F[5] = "Frequently";
                set(textview, AnnualCarbonInformation.Food, AnnualCarbonInformation.F, 5);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.Food[0] != 4){
                    loadFragment(new ACQ8Fragment());
                }
                else{
                    loadFragment(new ACQ9Fragment(4));
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.Food[5] != 0){
                    loadFragment(new ACQ11Fragment());
                }
            }
        });

        return view;
    }
}