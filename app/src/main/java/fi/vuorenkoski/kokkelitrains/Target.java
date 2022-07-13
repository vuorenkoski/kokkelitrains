package fi.vuorenkoski.kokkelitrains;

import com.google.android.gms.maps.model.LatLng;

public class Target {
    private LatLng location;
    private String speed;

    public Target(LatLng location, String speed) {
        this.location = location;
        this.speed = speed;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getSpeed() {
        return speed;
    }
}
