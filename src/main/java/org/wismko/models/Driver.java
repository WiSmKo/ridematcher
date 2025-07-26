package org.wismko.models;

public class Driver {

    private final String id;
    private boolean available;
    private Location location;

    public Driver(String id, Location location) {
        this.id = id;
        this.available = true;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public boolean isAvailable() {
        return available;
    }

    public Location getLocation() {
        return location;
    }

    public void registerAvailability(final boolean available, final Location location){
        this.available = available;
        this.location = location;
    }
}
