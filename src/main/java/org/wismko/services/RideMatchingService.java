package org.wismko.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.wismko.models.Driver;
import org.wismko.models.Location;
import org.wismko.models.Ride;

public class RideMatchingService {

    public ConcurrentHashMap<Driver, Ride> driverRideConcurrentHashMap = new ConcurrentHashMap<>();
    public List<Driver> drivers = new ArrayList<>();


    public void registerDriverAvailability(String driverId, boolean available, Location location){

    }

    public Driver requestRide(String riderId, Location location){
        return new Driver( "123", true, new Location( 123.123, 123.123 ) );
    }

    public void completeRide(String rideId){

    }

    public List<Driver> getAvailableDrivers(Location location){
        return List.of(new Driver( "123", true, new Location( 123.123, 123.123 ) ));
    }
}
