package services;


import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.wismko.dtos.MatchedRide;
import org.wismko.models.Driver;
import org.wismko.models.Location;
import org.wismko.models.Ride;
import org.wismko.services.RideMatchingService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RideMatchingServiceTest {

    private RideMatchingService rideMatchingService;

    private Ride testRide;

    @BeforeEach
    void setUp(){
        this.rideMatchingService = new RideMatchingService();

        testRide = new Ride( "riderX", new Location( 7.0, 8.0 ));
        testRide.setAssignedDriverId( "driverY" );

        Driver driverX = new Driver( "driverX", true, new Location( 10.0, 10.0 ) );
        Driver driverY = new Driver(  "driverY", false, new Location( 20.0, 20.0 ) );
        Driver driverZ = new Driver( "driverZ", true, new Location( 30.5, 30.5 ) );

        rideMatchingService.driverRegistry.put( driverX.getId(), driverX );
        rideMatchingService.driverRegistry.put( driverY.getId(), driverY );
        rideMatchingService.driverRegistry.put( driverZ.getId(), driverZ );

        rideMatchingService.rideRegistry.put( testRide.getId(), testRide );
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void registerDriverAvailability_newDriver( boolean available ){
        String driverId = "driver1";
        Location location= new Location( 45.34, 67.56 );

        rideMatchingService.registerDriverAvailability(driverId, available, location);

        Driver expectedDriver = rideMatchingService.driverRegistry.get(driverId);
        assertEquals( expectedDriver.isAvailable(), available );
        assertEquals( expectedDriver.getLocation(), location );
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void registerDriverAvailability_existingDriver( boolean available ){
        String driverId = "driverX";
        Location location= new Location( 45.34, 67.56 );

        rideMatchingService.registerDriverAvailability(driverId, available, location);

        Driver expectedDriver = rideMatchingService.driverRegistry.get(driverId);
        assertEquals( expectedDriver.isAvailable(), available );
        assertEquals( expectedDriver.getLocation(), location );
    }

    @Test
    public void requestRide(){
        Location pickupLocation = new Location( 5.0, 6.0 );

        MatchedRide result = rideMatchingService.requestRide( new Ride( "rider1", pickupLocation ));

        assertEquals( "driverZ", result.driver().getId() );
        assertFalse( result.driver().isAvailable() );
        assertEquals( "rider1", result.ride().getRiderId() );
        assertEquals( pickupLocation, result.ride().getPickupLocation() );
        assertEquals( rideMatchingService.rideRegistry.get( result.ride().getId() ).getAssignedDriverId(), result.driver().getId() );
    }

    @Test
    public void completeRide(){
        String rideId = testRide.getId();

        rideMatchingService.completeRide( rideId, new Location( 123.45, 234.56 ) );

        Ride ride = rideMatchingService.rideRegistry.get( rideId );

        assertTrue( ride.isCompleted() );
        assertTrue( rideMatchingService.driverRegistry.get( ride.getAssignedDriverId() ).isAvailable() );
    }

    @Test
    public void getAvailableDrivers(){
        Location location = new Location( 19.0, 19.5 );

        List<Driver> drivers = rideMatchingService.getAvailableDrivers( location );

        assertEquals( "driverX", drivers.getFirst().getId() );
        assertEquals( "driverZ", drivers.get(1).getId() );
    }
}
