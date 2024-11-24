package com.example.b07demosummer2024.AnnualCarbon;

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
     static int[] Consumption = new int[4];

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
               } else if (PublicTransportation[0] == 3 || PublicTransportation[0] == 4){
                    //Emissions based on time for "frequently" or "always" (they have same values)
                    int[] timeEmissions = {573, 1911, 3822, 7166, 9555};
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

          return 0;
     }

     public double housingCalc(){
          return 0;
     }

     public double consumptionCalc(){
          return 0;
     }

}
