package com.example.b07demosummer2024.AnnualCarbon;

import static java.lang.Double.parseDouble;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.b07demosummer2024.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;


public class IntroToCalcFragment extends LoadFragment {

    public IntroToCalcFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_to_calc, container, false);

        Button introcontinue = view.findViewById(R.id.IntroContinue);
        Spinner countries = view.findViewById(R.id.countryDropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                view.getContext(),
                R.array.countries_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countries.setAdapter(adapter);
        AnnualCarbonInformation aci = new AnnualCarbonInformation(getContext());
        countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String country = (String) countries.getSelectedItem();
                if (country.equals("Choose a country or region")) {
                    aci.setCountry(null);
                } else {
                    aci.setCountry(country);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });


        introcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aci.country != null) {
                    loadFragment(new ACQ1Fragment());
                }
            }
        });

        return view;
    }
}