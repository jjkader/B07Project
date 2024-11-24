package com.example.b07demosummer2024.AnnualCarbon;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class AnnualCarbonInformation{
     static int[] PersonalVehicleUse = new int[3];
     static String[] PVU = new String[3];
     static int[] PublicTransportation = new int[2];
     static String[] PT = new String[2];
     static int[] AirTravel = new int[2];
     static String[] AT = new String[2];
     static int[] Food = new int[6];

     static String[] F = new String[6];

     static int[] Housing = new int[7];
     static String[] H = new String[7];
     static int[] Consumption = new int[4];
     static String[] C = new String[4];
     static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

     static FirebaseUser user = firebaseAuth.getCurrentUser();
     public static String uid = user.getUid();
     DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
     public AnnualCarbonInformation(){
     }

     public static double transportationCalc(){
          double total = 0;
          //PVU Calculation
          if (PersonalVehicleUse[0] == 1){
               double[] EF = {0.24,0.27,0.16,0.05,0}; //Emission factor based on type of car
               double[] distance = {5000, 10000, 15000, 20000, 25000, 35000}; //Total distance driven
               int typeOfCar = PersonalVehicleUse[1];
               int distanceTravelled = PersonalVehicleUse[2];
               total += EF[typeOfCar - 1] * distance[distanceTravelled - 1];
          }
          //Public Transport Calculation
          if (PublicTransportation[0] != 1){
               if (PublicTransportation[0] == 2){
                    //Emissions based on time for "occasionally"
                    int[] timeEmissions = {246, 819, 1638, 3071, 4095};
                    for (int i = 1; i <= 5; i++){
                         if (PublicTransportation[1] ==  i){
                              total += timeEmissions[i - 1];
                         }
                    }
               } else if (PublicTransportation[0] == 3){
                    //Emissions based on time for "frequently"
                    int[] timeEmissions = {573, 1911, 3822, 7166, 9555};
                    for (int i = 1; i <= 5; i++){
                         if (PublicTransportation[1] == i){
                              total += timeEmissions[i - 1];
                         }
                    }
               } else if (PublicTransportation[0] == 4){
                    int[] timeEmissions = {1050, 2363, 4103, 9611, 13075};
                    for (int i = 1; i <= 5; i++){
                         if (PublicTransportation[1] == i){
                              total += timeEmissions[i - 1];
                         }
                    }
               }
          }
          //Air Travel Calculation
          int[] shortHaulEmissions = {0, 225, 600, 1200, 1800};
          total += shortHaulEmissions[AirTravel[0] - 1];
          int[] longHaulEmissions = {0, 825, 2200, 4400, 6600};
          total += longHaulEmissions[AirTravel[1] - 1];
          return total;
     }

     public double foodCalc(){
          double total = 0;
          if (Food[0] == 4){
               int[] beef = {2500, 1900, 1300, 0};
               for (int i = 1; i <= 4; i++){
                    if (Food[1] == i){
                         total += beef[i - 1];
                    }
               }
               int[] pork = {1450, 860, 450, 0};
               for (int i = 1; i <= 4; i++){
                    if (Food[2] == i){
                         total += pork[i - 1];
                    }
               }
               int[] chicken = {950, 600, 200, 0};
               for (int i = 1; i <= 4; i++){
                    if (Food[3] == i){
                         total += chicken[i - 1];
                    }
               }
               int[] fish = {800, 500, 150, 0};
               for (int i = 1; i <= 4; i++){
                    if (Food[4] == i){
                         total += fish[i - 1];
                    }
               }
          } else{
               double[] nonMeat = {1000, 500, 1500};
               for (int i = 1; i <= 3; i++) {
                    if (Food[1] == i) {
                         total += nonMeat[i - 1];
                    }
               }
          }
          double[] leftOvers = {0, 23.4, 70.2, 140.4};
          for (int i = 1; i <= 4; i++) {
               if (Food[5] == i) {
                    total += leftOvers[i - 1];
               }
          }
          return total;
     }

     public double housingCalc(){
          return 0;
     }

     public double consumptionCalc(){
          double total = 0; //Total consumption
          int[] clothes = {360, 120, 100, 5}; //Clothes question quantities
          for (int i = 1; i <= 4; i++) {
               if (Consumption[0] == i) {
                    total += clothes[i - 1];
               }
          }
          double[] q2 = {0.5, 0.3, 1}; //"Second-hand or eco-friendly" question quantities
          for (int i = 1; i <= 3; i++) {
               if (Consumption[1] == i) {
                    total = total * q2[i - 1];
               }
          }
          double [] electronic = {0, 300, 600, 900, 1200}; //Devices question quantities
          for (int i = 1; i <= 4; i++) {
               if (Consumption[2] == i) {
                    total += electronic[i - 1];
               }
          }

          //Recycling question calculations
          int recycleFreq = Consumption[3] - 1;
          //Monthly Buyers
          if (Consumption[0] == 1){
               double[] reduction = {0, 54, 108, 180};
               total -= reduction[recycleFreq];
          }
          //Quarterly Buyers
          else if (Consumption[0] == 2) {
               double[] reduction = {0, 28, 54, 90};
               total -= reduction[recycleFreq];
          }
          //Annually Buyers
          else if (Consumption[0] == 3){ //
               double[] reduction = {0, 15, 30, 50};
               total -= reduction[recycleFreq];

          }
          //Rarely Buyers
          else if (Consumption[0] == 4){
               double[] reduction = {0, 0.75, 1.5, 2.5};
               total -= reduction[recycleFreq];
          }

          //1 Device
          if (Consumption[2] == 2){
               double[] reduction = {0, 45, 60, 90};
               total -= reduction [recycleFreq];
          //2 Devices
          } else if (Consumption[2] == 3){
               double[] reduction = {0, 60, 120, 180};
               total -= reduction [recycleFreq];
          //3 Devices
          } else if (Consumption[2] == 4){
               double[] reduction = {0, 90, 180, 270};
               total -= reduction [recycleFreq];
          //4 Devices
          } else if (Consumption[2] == 5){
               double[] reduction = {0, 120, 240, 360};
               total -= reduction [recycleFreq];
          }
          return total;
     }

     public void addToDatabase(){
          double transTotal = transportationCalc();
          double foodTotal = foodCalc();
          double housingTotal = housingCalc();
          double consumptionTotal = consumptionCalc();
          double total = transTotal + foodTotal + housingTotal + consumptionTotal;
     }
}
