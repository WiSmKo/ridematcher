package org.wismko.models;

import java.util.UUID;

public class Ride {

    private final String id;
    private final Location pickupLocation;
    private String assignedDriverId;
    private boolean completed;


    public Ride(Location pickupLocation) {
        this.id = UUID.randomUUID().toString();
        this.pickupLocation = pickupLocation;
        this.completed = false;
    }

    public String getId() {
        return id;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public String getAssignedDriverId() {
        return assignedDriverId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markComplete() {
        this.completed = true;
    }

    public void setAssignedDriverId(String driverId) {
        this.assignedDriverId = driverId;
    }
}
