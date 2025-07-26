package org.wismko.dtos;

import org.wismko.models.Driver;
import org.wismko.models.Ride;

public record MatchedRide(Driver driver, Ride ride) {
}
