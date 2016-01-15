package com.kyo.hw5_5630213025;

/**
 * Created by Kyo on 5/1/2559.
 */
public class Weather {
    public double temp = 0;
    public String date = "";
    public String humidity = "";
    public String local = "";
    public String country = "";
    public String speed = "";
    public String wind = "";
    public int deg = 0;


    public void DegWind(int deg) {
        if (deg == 0) {
            wind = "North";
        } else if (deg == 90) {
            wind = "East";
        } else if (deg == 180) {
            wind = "South";
        } else if (deg == 270) {
            wind = "West";
        } else if (deg > 0 && deg < 90) {
            wind = "NorthEast";
        } else if (deg > 90 && deg < 180) {
            wind = "SouthEast";
        } else if (deg > 180 && deg < 270) {
            wind = "SouthWest";
        } else if (deg > 270 && deg <= 359) {
            wind = "NorthWest";
        }
    }

    @Override
    public String toString() {
        float temp1 = (float) (temp - 273.15);

        return "  "+temp1 + " °C ";
    }

    public String getLocal() {
        return " "+local + " ," + country;
    }

    public String getDate() {
        return "    "+date;
    }

    public String getWind() {
        DegWind(deg);
        return "    Wind : " + speed + " m/s  (" + wind + ") \n     Humidity : " + humidity + "%";
    }

}
