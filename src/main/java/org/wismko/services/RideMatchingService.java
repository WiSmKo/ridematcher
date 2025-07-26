package org.wismko.services;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.wismko.dtos.MatchedRideDto;
import org.wismko.models.Driver;
import org.wismko.models.Location;
import org.wismko.models.Ride;

public class RideMatchingService {

    public ConcurrentHashMap<String, Driver> driverRegistry = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, Ride> rideRegistry = new ConcurrentHashMap<>();


    /**
     * Allows the driver to register and update their availability, along with their current location
     *
     * @param driverId ID of the driver
     * @param available boolean representation of drivers availability
     * @param location drivers location
     */
    public void registerDriverAvailability(String driverId, boolean available, Location location){
        Driver driver = driverRegistry.computeIfAbsent( driverId, id -> new Driver( driverId, available, location ) );

        synchronized ( driver ){
            driver.registerAvailability( available, location );
        }
    }

    /**
     * Allows riders to request a ride from their location, finding the nearest available driver and updating the assigned driver's status.
     *
     * @param pickUpLocation starting location of ride
     * @return Ride and driver details
     */
    public MatchedRideDto requestRide(Location pickUpLocation){

        Ride ride = new Ride( pickUpLocation );
        List<Driver> drivers = getAvailableDrivers( pickUpLocation );

        Driver assignedDriver = null;

        for ( Driver driver : drivers){
            synchronized ( driver ){
                if (driver.isAvailable()){
                    driver.registerAvailability( false, pickUpLocation );
                    assignedDriver = driver;
                    break;
                }
            }
        }

        if (assignedDriver == null){
            throw new RuntimeException("No available drivers");
        }

        ride.setAssignedDriverId( assignedDriver.getId() );
        rideRegistry.put( ride.getId(), ride );
        return new MatchedRideDto( assignedDriver, ride );
    }

    /**
     * Allows the rider to mark the ride as completed and set the driver available again.
     *
     * @param rideId the ride ID
     * @param rideEndlocation where the ride ended
     */
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

    /**
     * Returns a list of available drivers, sorted by ascending distance
     *
     * @param pickUpLocation location from which to measure the distance
     * @return List of drivers
     */
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
