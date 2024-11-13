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

public class ACQ1Fragment extends Fragment {

    public ACQ1Fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q1, container, false);

        TextView textView = view.findViewById(R.id.Q1Response);
        Button yesbutton = view.findViewById(R.id.Q1Yes);
        yesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnualCarbonInformation.PersonalVehicleUse[0] = 0;
                String text = "You have entered: Yes";
                textView.setText(text);
            }
        });

        Button nobutton = view.findViewById(R.id.Q1No);
        nobutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AnnualCarbonInformation.PersonalVehicleUse[0] = 1;
                String text = "You have entered: No";
                textView.setText(text);
            }
        });

        Button nextbutton = view.findViewById(R.id.Q1Next);
        nextbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if (AnnualCarbonInformation.PersonalVehicleUse[0] == 1) {
                    loadFragment(new ACQ2Fragment());
                }
            }
        });

        return view;

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}