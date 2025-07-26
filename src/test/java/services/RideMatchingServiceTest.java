package services;


import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.wismko.models.Driver;
import org.wismko.models.Location;
import org.wismko.services.RideMatchingService;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RideMatchingServiceTest {


    private RideMatchingService rideMatchingService;

    @BeforeEach
    void setUp(){
        this.rideMatchingService = new RideMatchingService();

        rideMatchingService.drivers = List.of(
            new Driver( "driver1", true, new Location( 1.0, 2.0 ) ),
            new Driver(  "driver2", false, new Location( 3.0, 4.0 ))
        );
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void registerDriverAvailability_newDriver( boolean available ){
        String driverId = "newDriver";
        Location location= new Location( 45.34, 67.56 );

        rideMatchingService.registerDriverAvailability(driverId, available, location);

        Driver expectedDriver = rideMatchingService.drivers.stream().filter( driver -> driver.getId().equals( driverId ) ).findFirst().get();
        assertEquals( expectedDriver.isAvailable(), available );
        assertEquals( expectedDriver.getLocation(), location );
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void registerDriverAvailability_existingDriver( boolean available ){
        String driverId = "driver1";
        Location location= new Location( 45.34, 67.56 );

        rideMatchingService.registerDriverAvailability(driverId, available, location);

        Driver expectedDriver = rideMatchingService.drivers.stream().filter( driver -> driver.getId().equals( driverId ) ).findFirst().get();
        assertEquals( expectedDriver.isAvailable(), available );
        assertEquals( expectedDriver.getLocation(), location );
    }

    @Test
    public void requestRide(){

    }

    @Test
    public void completeRide(){

    }

    @Test
    public void getAvailableDrivers(){

    }
}
