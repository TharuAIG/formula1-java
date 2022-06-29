package com.assignment;

import java.util.Comparator;


public class Formula1DriverComparator implements Comparator<Formula1Driver> {

    private final boolean points;
    private final boolean firstWins;
    private final boolean descending;


    public Formula1DriverComparator(boolean points, boolean firstWins, boolean descending) {
        this.points = points;
        this.firstWins = firstWins;
        this.descending = descending;
    }


    @Override
    public int compare(Formula1Driver o1, Formula1Driver o2) {
        if(points && firstWins) {
            if(descending) {
                if(o1.getPoints() == o2.getPoints()) {
                    return Integer.compare(o2.getFirstWins(), o1.getFirstWins());
                } else if(o1.getPoints() > o2.getPoints()) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                if(o1.getPoints() == o2.getPoints()) {
                    return Integer.compare(o1.getFirstWins(), o2.getFirstWins());
                } else if(o1.getPoints() > o2.getPoints()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        } else if(points) {
            if(descending) {
                return Integer.compare(o2.getPoints(), o1.getPoints());
            }
            else {
                return Integer.compare(o1.getPoints(), o2.getPoints());
            }
        } else {
            if(descending) {
                return Integer.compare(o2.getFirstWins(), o1.getFirstWins());
            }
            else {
                return Integer.compare(o1.getFirstWins(), o2.getFirstWins());
            }
        }
    }
}
