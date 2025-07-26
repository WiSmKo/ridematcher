package services;

import org.junit.Test;
import org.wismko.models.Driver;
import org.wismko.models.Location;
import org.wismko.services.RideMatchingService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RideMatchingServiceTest {


    private RideMatchingService rideMatchingService;



    @Test
    public void registerDriverAvailability_available(){
        String driverId = "123";
        Location location= new Location( 45.34, 67.56 );

        rideMatchingService.registerDriverAvailability(driverId, true, location);

        Driver expectedDriver = rideMatchingService.drivers.stream().filter( driver -> driver.getId().equals( driverId ) ).findFirst().get();
        assertTrue( expectedDriver.isAvailable() );
        assertEquals( expectedDriver.getLocation(), location );
    }

    @Test
    public void registerDriverAvailability_notAvailable(){
        String driverId = "123";
        Location location= new Location( 45.34, 67.56 );

        rideMatchingService.registerDriverAvailability(driverId, true, location);

        Driver expectedDriver = rideMatchingService.drivers.stream().filter( driver -> driver.getId().equals( driverId ) ).findFirst().get();
        assertFalse( expectedDriver.isAvailable() );
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
