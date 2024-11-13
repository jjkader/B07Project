package com.example.b07demosummer2024.AnnualCarbon;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.b07demosummer2024.R;

public abstract class AnnualCarbonInformation extends Fragment{
     static int[] PersonalVehicleUse = new int[3];
     static int[] PublicTransportation = new int[2];
     static int[] AirTravel = new int[2];
     static int[] Meat = new int[4];
     static int Waste;
     static int[] Housing = new int[7];
     static int[] Consumption = new int[4];

     public AnnualCarbonInformation(){

     }

     public void next(int i){
          if (i == 1){
               loadFragment(new ACQ2Fragment());
          }
     }
     private void loadFragment(Fragment fragment) {
          FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
          transaction.replace(R.id.fragment_container, fragment);
          transaction.addToBackStack(null);
          transaction.commit();
     }

}
