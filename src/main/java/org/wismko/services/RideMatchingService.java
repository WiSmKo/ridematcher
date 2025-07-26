package org.wismko.services;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

    public List<Driver> getAvailableDrivers(Location pickUpLocation){
        return driverRegistry.values().stream().filter( Driver::isAvailable )
            .sorted( Comparator.comparing( driver -> distance( driver.getLocation(), pickUpLocation ) ) )
            .toList();
    }

    /**
     * Calculates straight line Euclidean distance between two points
     * @param a point a
     * @param b point b
     * @return distance
     */
    private double distance(Location a, Location b){
        double dLat = a.latitude() - b.latitude();
        double dLon = a.longitude() - b.longitude();
        return Math.sqrt( dLat * dLat + dLon * dLon );
    }
}
