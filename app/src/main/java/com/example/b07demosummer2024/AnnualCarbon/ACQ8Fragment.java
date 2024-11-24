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


public class ACQ8Fragment extends LoadFragment {


    public ACQ8Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q8, container, false);

        TextView textview = view.findViewById(R.id.Q8Response);
        set(textview, AnnualCarbonInformation.Food, AnnualCarbonInformation.F, 0);

        Button vegetarian = view.findViewById(R.id.Q8Vegetarian);
        Button vegan = view.findViewById(R.id.Q8Vegan);
        Button pesc = view.findViewById(R.id.Q8Pescatarian);
        Button meat = view.findViewById(R.id.Q8Meat);
        Button back = view.findViewById(R.id.Q8Back);
        Button next = view.findViewById(R.id.Q8Next);

        vegetarian.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Food[0] = 1;
                AnnualCarbonInformation.F[0] = "Vegetarian";
                set(textview, AnnualCarbonInformation.Food, AnnualCarbonInformation.F, 0);
                for (int i = 1; i <= 4; i++){
                    AnnualCarbonInformation.Food[i] = 0;
                    AnnualCarbonInformation.F[i] = null;
                }
            }
        });

        vegan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Food[0] = 2;
                AnnualCarbonInformation.F[0] = "Vegan";
                set(textview, AnnualCarbonInformation.Food, AnnualCarbonInformation.F, 0);
                for (int i = 1; i <= 4; i++){
                    AnnualCarbonInformation.Food[i] = 0;
                    AnnualCarbonInformation.F[i] = null;
                }
            }
        });

        pesc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Food[0] = 3;
                AnnualCarbonInformation.F[0] = "Pescatarian";
                set(textview, AnnualCarbonInformation.Food, AnnualCarbonInformation.F, 0);
                for (int i = 1; i <= 4; i++){
                    AnnualCarbonInformation.Food[i] = 0;
                    AnnualCarbonInformation.F[i] = null;
                }
            }
        });

        meat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Food[0] = 4;
                AnnualCarbonInformation.F[0] = "Meat-based";
                set(textview, AnnualCarbonInformation.Food, AnnualCarbonInformation.F, 0);
            }
        });


        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                loadFragment(new ACQ7Fragment());
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.Food[0] != 0){
                    if (AnnualCarbonInformation.Food[0] == 4){
                        loadFragment(new ACQ9Fragment(1));
                    }
                    else{
                        //loadFragment(new ACQ10Fragment());
                    }

                }
            }
        });

        return view;
    }
}