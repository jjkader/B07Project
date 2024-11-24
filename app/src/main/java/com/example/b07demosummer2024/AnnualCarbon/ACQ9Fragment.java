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


public class ACQ9Fragment extends LoadFragment {

    int currpage;
    public ACQ9Fragment(int currpage) {
        this.currpage = currpage;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q9, container, false);

        TextView textview = view.findViewById(R.id.Q9Response);

        String[] FoodArray = new String[5];
        FoodArray[1] = "beef";
        FoodArray[2] = "pork";
        FoodArray[3] = "chicken";
        FoodArray[4] = "fish/seafood";

        TextView question = view.findViewById(R.id.Q9);
        question.append(" " + FoodArray[currpage] + "?");
        set(textview, AnnualCarbonInformation.Food, AnnualCarbonInformation.F, currpage);

        Button daily = view.findViewById(R.id.Q9Daily);
        Button freq = view.findViewById(R.id.Q9Frequently);
        Button occ = view.findViewById(R.id.Q9Occasionally);
        Button never = view.findViewById(R.id.Q9Never);
        Button back = view.findViewById(R.id.Q9Back);
        Button next = view.findViewById(R.id.Q9Next);

        daily.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Food[currpage] = 1;
                AnnualCarbonInformation.F[currpage] = "Daily";
                set(textview, AnnualCarbonInformation.Food, AnnualCarbonInformation.F, currpage);
            }
        });

        freq.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Food[currpage] = 2;
                AnnualCarbonInformation.F[currpage] = "Frequently";
                set(textview, AnnualCarbonInformation.Food, AnnualCarbonInformation.F, currpage);
            }
        });

        occ.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Food[currpage] = 3;
                AnnualCarbonInformation.F[currpage] = "Occasionally";
                set(textview, AnnualCarbonInformation.Food, AnnualCarbonInformation.F, currpage);
            }
        });

        never.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Food[currpage] = 4;
                AnnualCarbonInformation.F[currpage] = "Never";
                set(textview, AnnualCarbonInformation.Food, AnnualCarbonInformation.F, currpage);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (currpage == 1){
                    loadFragment(new ACQ8Fragment());
                }
                else{
                    loadFragment(new ACQ9Fragment(currpage-1));
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (currpage == 4){
                    if (AnnualCarbonInformation.Food[4] != 0){
                        loadFragment(new ACQ10Fragment());
                    }
                }
                else{
                    if (AnnualCarbonInformation.Food[currpage] != 0){
                        loadFragment(new ACQ9Fragment(currpage+1));
                    }
                }
            }
        });

        return view;




    }
}