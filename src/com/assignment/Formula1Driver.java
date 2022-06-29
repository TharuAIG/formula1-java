package com.assignment;


public class Formula1Driver extends Driver {

    private int firstWins;
    private int secondWins;
    private int thirdWins;
    private int totalRaces;
    private int points;


    public Formula1Driver(String driverName, String teamLocation, String teamName) {
        super(driverName, teamLocation, teamName);
    }



    @Override
    public void addNewRace(int placedAchieved) {
        // update first three positions count
        if(placedAchieved == 1)  {
            this.firstWins++;
        }
        else if(placedAchieved == 2) {
            this.secondWins++;
        }
        else if(placedAchieved == 3) {
            this.thirdWins++;
        }
        // if the driver achieved place between 1-10,
        // then update his/her points achieved so far
        if(placedAchieved <= 10) {
            this.points = this.points + Constants.POINTS_TABLE[placedAchieved - 1];
        }

        // increment number of races participated by one
        this.totalRaces++;
    }

    // getters and setters
    public int getFirstWins() {
        return firstWins;
    }

    public void setFirstWins(int firstWins) {
        this.firstWins = firstWins;
    }

    public int getSecondWins() {
        return secondWins;
    }

    public void setSecondWins(int secondWins) {
        this.secondWins = secondWins;
    }

    public int getThirdWins() {
        return thirdWins;
    }

    public void setThirdWins(int thirdWins) {
        this.thirdWins = thirdWins;
    }

    public int getTotalRaces() {
        return totalRaces;
    }

    public void setTotalRaces(int totalRaces) {
        this.totalRaces = totalRaces;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }



    @Override
    public String toString() {
        return "Formula1Driver{" +
                "firstWins=" + firstWins +
                ", secondWins=" + secondWins +
                ", thirdWins=" + thirdWins +
                ", totalRaces=" + totalRaces +
                ", points=" + points +
                '}';
    }
}
