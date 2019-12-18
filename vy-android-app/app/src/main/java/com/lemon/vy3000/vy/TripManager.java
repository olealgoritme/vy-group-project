package com.lemon.vy3000.vy;

import java.time.Instant;

public class TripManager {

    private static TripManager instance;
    private static Trip currentTrip;
    private TripManager() {}

    public static TripManager getInstance() {
        if (instance == null) {
            instance = new TripManager();
            currentTrip = new Trip();
        }
        return instance;
    }

    public void startTripAt(Instant departureTime) {
        if (!currentTrip.isEnded()) new Exception("Can't start a trip because last trip has not ended yet");
        currentTrip.setStarted(true);
        currentTrip.setDepartureTime(departureTime);
    }

    public Trip getCurrentTrip() {
        return currentTrip;
    }

    public void resetTrip() {
        currentTrip.setEnded(false);
        currentTrip.setStarted(false);
        currentTrip.setDepartureTime(null);
        currentTrip.setArrivalTime(null);
    }

    public void endTripAt(Instant arrivalTime) {
        if (!currentTrip.isEnded()) new Exception("Can't end a trip that has already ended");
        currentTrip.setEnded(true);
        currentTrip.setArrivalTime(arrivalTime);
    }

}
