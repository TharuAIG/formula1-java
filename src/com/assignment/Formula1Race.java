package com.assignment;

import java.util.Map;


public class Formula1Race {

    private final String dateOfRace;
    private Map<Driver,Integer> participatedDrivers;


    public Formula1Race(String date) {
        this.dateOfRace = date;
    }


    public Formula1Race(String dateOfRace, Map<Driver,Integer> participatedDrivers) {
        this.dateOfRace = dateOfRace;
        this.participatedDrivers = participatedDrivers;
    }

    // getters and setters
    public String getDateOfRace() {
        return dateOfRace;
    }

    public Map<Driver,Integer> getParticipatedDrivers() {
        return participatedDrivers;
    }

    public void setParticipatedDrivers(Map<Driver,Integer> participatedDrivers) {
        this.participatedDrivers = participatedDrivers;
    }
}
