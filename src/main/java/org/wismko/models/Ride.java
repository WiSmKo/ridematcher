package org.wismko.models;

import java.util.UUID;

public class Ride {

    private final String id;
    private final String riderId;
    private final Location pickupLocation;
    private String assignedDriverId;
    private boolean completed;


    public Ride(String riderId, Location pickupLocation) {
        this.id = UUID.randomUUID().toString();
        this.riderId = riderId;
        this.pickupLocation = pickupLocation;
        this.completed = false;
    }

    public String getId() {
        return id;
    }

    public String getRiderId() {
        return riderId;
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

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setAssignedDriverId(String driverId) {
        this.assignedDriverId = driverId;
    }
}
