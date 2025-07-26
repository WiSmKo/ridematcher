package org.wismko.models;

import java.util.UUID;

public class Ride {

    private final String id;
    private Location pickupLocation;
    private boolean completed;


    public Ride(Location pickupLocation) {
        this.id = UUID.randomUUID().toString();
        this.pickupLocation = pickupLocation;
        this.completed = false;
    }


}
