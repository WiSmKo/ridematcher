# Ride Matching Service Readme

This application implements a ride matching service to allocate drivers to incoming ride requests, efficiently and safely under concurrent load.



## Setup and Execution

For the sake of the scope of this exercise, the service can be run triggered manually (unit test class refrenced below) with data stored in memory as per the assignment specification.
In a real production environment additional layers would be implemented, there would be a database for persisting the drivers and rides and a controller layer to
facilitate use of the service from external sources such as through an app or browser.

### Requirements

- Java version: 21
- Build tool: Maven

No environment variables or extra libraries required.

### Setup

1. Clone the repository `git clone https://github.com/WiSmKo/ridematcher`
2. Run the tests `mvn test` or run with your IDE

### Tests

A suite of unit tests for the service can be found in `test/java/services/RideMatchingServiceTest` which tests the requirements of the application.
