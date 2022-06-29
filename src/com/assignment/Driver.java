package com.assignment;

import java.util.Objects;


public abstract class Driver {

    private String driverName;
    private final String teamLocation;
    private final String teamName;

    public Driver(String driverName, String teamLocation, String teamName) {
        this.driverName = driverName;
        this.teamLocation = teamLocation;
        this.teamName = teamName;
    }

    /*
     * This function takes rank as an argument and then update,
     *  the number of first positions, second positions, number of third positions,number of races participated,
     *  and the number of points achieved so far
     */
    public abstract void addNewRace(int placedAchieved);

    // getters and setters
    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getTeamLocation() {
        return teamLocation;
    }

    public String getTeamName() {
        return teamName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(driverName, driver.driverName) && Objects.equals(teamName, driver.teamName);
    }


    @Override
    public int hashCode() {
        return Objects.hash(driverName, teamName);
    }


    @Override
    public String toString() {
        return "Driver{" +
                "driverName='" + driverName + '\'' +
                ", teamLocation='" + teamLocation + '\'' +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
