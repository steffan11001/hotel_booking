package com.example.Hotel.services;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.sqrt;


@Service
public class HotelService {
    public String hello_from_service(double radius, double lat, double lng)
    {
        double hotel1_lat =  46.7646542;
        double hotel1_lng = 23.59867412;
        double distance =calculateDistance(lat,lng,hotel1_lat,hotel1_lng);
        System.out.println(distance);
        if(distance<radius)
        {
            return "hotel 1 is in range";
        }
        return "h";
    }

//    double haversine(double val) {
//        return Math.pow(Math.sin(val / 2), 2);
//    }
//    double calculateDistance(double startLat, double startLong, double endLat, double endLong) {
//        double EARTH_RADIUS = 6371.0;
//        double dLat = Math.toRadians((endLat - startLat));
//        double dLong = Math.toRadians((endLong - startLong));
//
//        startLat = Math.toRadians(startLat);
//        endLat = Math.toRadians(endLat);
//
//        double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//
//        return EARTH_RADIUS * c;
//    }
    double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        double SEMI_MAJOR_AXIS_MT = 6378137;
        double SEMI_MINOR_AXIS_MT = 6356752.314245;
        double FLATTENING = 1 / 298.257223563;
        double ERROR_TOLERANCE = 1e-12;
        double U1 = Math.atan((1 - FLATTENING) * Math.tan(Math.toRadians(latitude1)));
        double U2 = Math.atan((1 - FLATTENING) * Math.tan(Math.toRadians(latitude2)));

        double sinU1 = Math.sin(U1);
        double cosU1 = Math.cos(U1);
        double sinU2 = Math.sin(U2);
        double cosU2 = Math.cos(U2);

        double longitudeDifference = Math.toRadians(longitude2 - longitude1);
        double previousLongitudeDifference;

        double sinSigma, cosSigma, sigma, sinAlpha, cosSqAlpha, cos2SigmaM;

        do {
            sinSigma = Math.sqrt(Math.pow(cosU2 * Math.sin(longitudeDifference), 2) +
                    Math.pow(cosU1 * sinU2 - sinU1 * cosU2 * Math.cos(longitudeDifference), 2));
            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * Math.cos(longitudeDifference);
            sigma = Math.atan2(sinSigma, cosSigma);
            sinAlpha = cosU1 * cosU2 * Math.sin(longitudeDifference) / sinSigma;
            cosSqAlpha = 1 - Math.pow(sinAlpha, 2);
            cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;
            if (Double.isNaN(cos2SigmaM)) {
                cos2SigmaM = 0;
            }
            previousLongitudeDifference = longitudeDifference;
            double C = FLATTENING / 16 * cosSqAlpha * (4 + FLATTENING * (4 - 3 * cosSqAlpha));
            longitudeDifference = Math.toRadians(longitude2 - longitude1) + (1 - C) * FLATTENING * sinAlpha *
                    (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * Math.pow(cos2SigmaM, 2))));
        } while (Math.abs(longitudeDifference - previousLongitudeDifference) > ERROR_TOLERANCE);

        double uSq = cosSqAlpha * (Math.pow(SEMI_MAJOR_AXIS_MT, 2) - Math.pow(SEMI_MINOR_AXIS_MT, 2)) / Math.pow(SEMI_MINOR_AXIS_MT, 2);

        double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));

        double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * Math.pow(cos2SigmaM, 2))
                - B / 6 * cos2SigmaM * (-3 + 4 * Math.pow(sinSigma, 2)) * (-3 + 4 * Math.pow(cos2SigmaM, 2))));

        double distanceMt = SEMI_MINOR_AXIS_MT * A * (sigma - deltaSigma);
        return distanceMt / 1000;
    }
//    public double calculate_distance_between_2_points(double x1, double y1, double x2, double y2)
//    {
//        return sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
//    }
}
