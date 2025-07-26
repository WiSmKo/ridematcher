package org.wismko.services;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.wismko.dtos.MatchedRide;
import org.wismko.models.Driver;
import org.wismko.models.Location;
import org.wismko.models.Ride;

public class RideMatchingService {

    public ConcurrentHashMap<String, Driver> driverRegistry = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, Ride> rideRegistry = new ConcurrentHashMap<>();


    public void registerDriverAvailability(String driverId, boolean available, Location location){
        Driver driver = driverRegistry.computeIfAbsent( driverId, id -> new Driver( driverId, available, location ) );

        synchronized ( driver ){
            driver.registerAvailability( available, location );
        }
    }

    public MatchedRide requestRide(Ride ride){

        //todo: fetch closest driver - dummy data for compiling tests
        Driver driver = new Driver( "123", true, new Location( 123.123, 123.123 ));

        return new MatchedRide( driver, ride );
    }

    public void completeRide(String rideId, Location rideEndlocation){

        Ride ride = rideRegistry.get( rideId );
        Driver driver = this.driverRegistry.get( ride.getAssignedDriverId() );

        if(driver == null){
            throw new IllegalArgumentException("Driver not found");
        }

        synchronized ( ride ) {
            ride.markComplete();
        }
        synchronized ( driver ){
            driver.registerAvailability( true, rideEndlocation );
        }
    }

    public List<Driver> getAvailableDrivers(Location location){
        //todo: fetch closest driver - dummy data for compiling tests
        return List.of(new Driver( "123", true, new Location( 123.123, 123.123 ) ));
    }
}
