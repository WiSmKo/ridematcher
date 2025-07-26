package org.wismko.dtos;

import org.wismko.models.Driver;
import org.wismko.models.Ride;

public record MatchedRideDto(Driver driver, Ride ride) {
}
