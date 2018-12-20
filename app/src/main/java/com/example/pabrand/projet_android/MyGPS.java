package com.example.pabrand.projet_android;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class MyGPS implements LocationListener {


    private final Context context;

    boolean dispoGPS = false;
    boolean dispoReseau = false;
    boolean dispoLoc = false;

    Location location;
    double latitude;
    double longitude;


    protected LocationManager locationManager;

    public MyGPS(Context context) {
        this.context = context;
        getLocation();
    }

    /**
     * Retourne la localisation actuelle
     *
     * @return
     */
    public Location getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            dispoGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            dispoReseau = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);



            if (dispoGPS == false && dispoReseau == false) {
                // pas de reseau
            } else {
                this.dispoLoc = true;

                // Si le reseau est dispo
                if (dispoReseau) {
                    location = null;
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

                // Si le GPS est activé
                if (dispoGPS) {
                    location = null;
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 0, this);

                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return location;
    }
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public double getLatitude() {
        return (location != null) ? latitude = location.getLatitude() : latitude;
    }

    public double getLongitude() {
        return (location != null) ? longitude = location.getLongitude() : longitude;
    }

    public boolean canLocate() {
        return dispoLoc;
    }

    public boolean isDispoGPS() {
        return dispoGPS;
    }

    public boolean isDispoLoc() {
        return dispoLoc;
    }
}
