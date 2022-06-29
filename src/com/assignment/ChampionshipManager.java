package com.assignment;


public interface ChampionshipManager {


    //Function to create new driver
    void addDriver();


    //Function to change the driver name existing in a team
    void changeDriver();


    // function to delete a given driver and the team the driver belongs to
    void delDriver();


    //Displays statistics for a selected driver
    void driverStatistics();


     //Adds new formula1 race with date and the participants, ranks and scores
    void addRace();


    // Displays formula1 driver table which includes statistics for each driver in a descriptive way
    void driverTable();


    // generates a new race by assigning random ranks for each driver
    void addRandomRace();


    /*
     * generates a new race
     *associated probabilities for each starting position: 1 - 40%, 2 - 30%, 3,4 - 10%, 5-9 - 2%, others - 0%
    */
    void addRandomRaceWithWeights();
}
