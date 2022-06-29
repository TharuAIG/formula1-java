package com.assignment;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.assignment.Constants.*;


public class Formula1ChampionshipManager implements ChampionshipManager {

    // scanner for read data from console
    private static final Scanner in = new Scanner(System.in);
    // keep the races
    private static final List<Formula1Race> ALL_FORMULA_1_RACES = new ArrayList<>();
    // keep the drivers and their teams
    private static final List<Formula1Driver> driversList = new ArrayList<>();

    private static JFrame formula1ChampionshipTable, formula1ChampionshipRaceHistory;

    private JTextArea jTextHistoryArea;
    private JTextField jSearchTextField;


    public static void main(String[] args) throws IOException {

        formula1ChampionshipTable = new JFrame();
        formula1ChampionshipRaceHistory= new JFrame();

        Formula1ChampionshipManager manager = new Formula1ChampionshipManager();
        manager.load();
        manager.formula1ChampionshipTable();
        manager.updateFormula1DriversGUI(driversList);
        manager.formula1ChampionshipRaceHistory();
        manager.updateRacesHistory("");
        char option;
        String input;
        while (true) {
            manager.menu();
            try {
                input = in.nextLine();
                option = input.charAt(0);

                switch (option) {
                    case '1':
                        manager.addDriver();
                        break;
                    case '2':
                        manager.changeDriver();
                        break;
                    case '3':
                        manager.delDriver();
                        break;
                    case '4':
                        manager.driverStatistics();
                        break;
                    case '5':
                        manager.driverTable();
                        break;
                    case '6':
                        manager.addRace();
                        break;
                    case '7':
                        EventQueue.invokeLater(() -> formula1ChampionshipTable.setVisible(true));
                        EventQueue.invokeLater(() -> formula1ChampionshipRaceHistory.setVisible(true));
                        break;
                    case '8':
                        manager.save();
                        break;
                    case '9': {
                        in.close();
                        System.out.println("**** Thank You ****");
                        System.exit(1);
                        break;
                    }
                    default:
                        System.out.println("Option must in between 1-9.\n");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("No any input given ...\n");
                System.out.println("Please enter your option.\n");
            }
        }
    }

    // Championship Manager Functions

    /***
     *
     */
    @Override
    public void addDriver() {
        String teamName;

        while (true) {
            System.out.print("Enter team name - ");
            teamName = in.nextLine().trim();
            if (teamName.isEmpty()) {
                System.out.println("Team name required.");
            } else {
                if(isDriverExists(teamName)) {
                    System.out.println("This team name already exists. Try again.");
                } else {
                    break;
                }
            }
        }

        String driverName;
        while (true) {
            System.out.print("Enter driver name - ");
            driverName = in.nextLine().trim();
            if (driverName.isEmpty()) {
                System.out.println("Driver name required.");
            } else {
                if(isDriverExists(driverName)) {
                    System.out.println("This team name already exists. Try again.");
                } else {
                    break;
                }
            }
        }

        System.out.print("Enter Location - ");
        String location = in.nextLine();
        Formula1Driver driver = new Formula1Driver(driverName,location,teamName);
        driversList.add(driver);
        // updateDriversStatGUITable(driversList);

        System.out.println("Added new driver successfully.");
    }

    /***
     *
     */
    @Override
    public void changeDriver() {
        String teamName, driverName;
        System.out.print("Enter team name - ");
        teamName = in.nextLine().trim();
        if(isTeamExists(teamName)) {
            while (true) {
                System.out.print("Enter driver name - ");
                driverName = in.nextLine().trim();
                if (isDriverExists(driverName)) {
                    System.out.println("This driver already exists an another team.");
                } else {
                    for(int i = 0; i< driversList.size(); i++) {
                        if(driversList.get(i).getTeamName().equalsIgnoreCase(teamName)) {
                            Formula1Driver formula1Driver = driversList.get(i);
                            formula1Driver.setDriverName(driverName);
                            driversList.set(i, formula1Driver);
                            // updateDriversStatGUITable(driversList);
                            break;
                        }
                    }
                    System.out.println("New driver has been assigned successfully");
                    break;
                }
            }
        } else {
            System.out.println("This team not exists in the system.");
        }
    }


    @Override
    public void delDriver() {
        String teamName;
        System.out.print("Enter team name - ");
        teamName = in.nextLine().trim();
        if(isTeamExists(teamName)) {
            for(int i = 0; i< driversList.size(); i++) {
                if(driversList.get(i).getTeamName().equalsIgnoreCase(teamName)) {
                    if(null != driversList.remove(i)) {
                        updateFormula1DriversGUI(driversList);
                    }
                }
            }
        } else {
            System.out.println("This team name not exists in the system.");
        }
    }



    @Override
    public void driverStatistics() {
        System.out.print("Enter driver name - ");
        String driverName = in.nextLine().trim();
        boolean flag = true;
        for(Formula1Driver formula1Driver : driversList) {
            if(formula1Driver.getDriverName().equalsIgnoreCase(driverName)) {
                System.out.printf("\n**** %s's Statistics ****\n", driverName);
                System.out.printf("Team name - %s\n", formula1Driver.getTeamName());
                System.out.printf("Driver name - %s\n", formula1Driver.getDriverName());
                System.out.printf("Location - %s\n", formula1Driver.getTeamLocation());
                System.out.printf("First Place Wins - %d\n", formula1Driver.getFirstWins());
                System.out.printf("Second Place Wins - %d\n", formula1Driver.getSecondWins());
                System.out.printf("Third Place Wins - %d\n", formula1Driver.getThirdWins());
                System.out.printf("Total Races - %d\n", formula1Driver.getTotalRaces());
                System.out.printf("Points - %d\n", formula1Driver.getPoints());
                System.out.println("**** **** **** ****\n");
                flag = false;
                break;
            }
        }

        if(flag) {
            System.out.println("This driver not exists in the system.");
        }
    }



    @Override
    public void addRace() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String formattedDate = localDateTime.format(DATE_TIME_FORMATTER);
        System.out.printf("Current Date and Time - %s\n", localDateTime);
        System.out.println("Enter each driver's place achieved ...");
        Map<Driver,Integer> participatedDrivers = new HashMap<>();
        for(int i = 0; i< driversList.size(); i++) {
            Formula1Driver formula1Driver = driversList.get(i);
            System.out.printf("Team Name : %s , Driver Name : %s , Location : %s\n", formula1Driver.getTeamName(),
                    formula1Driver.getDriverName(), formula1Driver.getTeamLocation());
            while (true) {
                try {
                    System.out.print("Enter place achieved - ");
                    int placedAchieved = Integer.parseInt(in.nextLine().trim());
                    if(placedAchieved > 0) {
                        participatedDrivers.put(new Formula1Driver(formula1Driver.getDriverName(),
                                        formula1Driver.getTeamLocation(), formula1Driver.getTeamName()),
                                placedAchieved);
                        formula1Driver.addNewRace(placedAchieved);
                        driversList.set(i, formula1Driver);
                        break;
                    } else {
                        System.out.println("Place cannot be zero or negative.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input must be an integer.");
                }
            }
        }
        ALL_FORMULA_1_RACES.add(new Formula1Race(formattedDate,participatedDrivers));
        updateRacesHistory(jSearchTextField.getText().trim().toLowerCase());
        updateFormula1DriversGUI(driversList);
        System.out.println("Added a new race successfully\n\n");
    }




    @Override
    public void driverTable() {
        List<Formula1Driver> formula1Drivers = new ArrayList<>(driversList);
        Formula1DriverComparator comparator = new Formula1DriverComparator(true,true,true);
        formula1Drivers.sort(comparator);
        System.out.format("\n************************************************************* FORMULA1 DRIVER TABLE *************************************************************\n");
        System.out.format("+*****************+*****************+*****************+*****************+*****************+*****************+*****************+*****************+%n");
        System.out.format("| Team  Name      | Location        | Driver Name     | First Wins      | Second Wins     | Third Wins      | No. of Races   | No. of Points    |%n");
        System.out.format("+*****************+*****************+*****************+*****************+*****************+*****************+*****************+*****************+%n");
        for(Formula1Driver driver : formula1Drivers) {
            System.out.format(FORMULA1_TABLE_DISPLAY_FORMAT,driver.getTeamName(),driver.getTeamLocation(),
                    driver.getDriverName(), driver.getFirstWins(), driver.getSecondWins(), driver.getThirdWins(),
                    driver.getTotalRaces(), driver.getPoints());
            System.out.format("+*****************+*****************+*****************+*****************+*****************+*****************+*****************+*****************+%n");
        }
        System.out.print("\n");
    }




    @Override
    public void addRandomRace() {
        int numberOfParticipants = driversList.size();
        int[] initialPlaces = new int[numberOfParticipants];
        for(int i = 1; i <= numberOfParticipants; i++) {
            initialPlaces[i - 1] = i;
        }
        addParticipants(shuffleArray(initialPlaces));
    }

    /*
     * probability for each starting position to win :
     *  1 - 40%, 2 - 30%, 3,4 - 10%, 5-9 - 2%, others - 0%
     */

    @Override
    public void addRandomRaceWithWeights() {
        int numberOfParticipants = driversList.size();
        int[] initialPlaces = new int[numberOfParticipants];
        int[] placesAchieved = shuffleArray(initialPlaces);
        double totalW = 0.0;
        int winnerIdx = 0, switchedIdx = -1;
        int threshold = Math.min(numberOfParticipants, 10);
        for(int i = 1; i <= numberOfParticipants; i++) {
            initialPlaces[i - 1] = i;
        }
        for(int i = 0; i < threshold; i++) {
            if(i+1 == 1) {
                totalW += 40.0;
            }
            else if(i+1 == 2) {
                totalW += 30.0;
            }
            else if(i+1 <= 4) {
                totalW += 10.0;
            }
            else if(i+1 <= 9) {
                totalW += 2.0;
            }
        }

        for (double rank = Math.random() * totalW; winnerIdx < threshold - 1; ++winnerIdx) {
            if(winnerIdx + 1 == 1) {
                rank -= 40.0;
            }
            else if(winnerIdx + 1 == 2) {
                rank -= 30.0;
            }
            else if(winnerIdx + 1 <= 4) {
                rank -= 10.0;
            }
            else {
                rank -= 2.0;
            }

            if (rank <= 0.0) {
                break;
            }
        }

        for(int i=0;i<placesAchieved.length;i++) {
            if(placesAchieved[i] == 1) {
                switchedIdx = i;
            }
        }

        int temp = placesAchieved[winnerIdx];
        placesAchieved[winnerIdx] = 1;
        placesAchieved[switchedIdx] = temp;
        addParticipants(placesAchieved);
    }


    // Helper Functions



    private void menu() {
        System.out.println("----------------- MAIN MENU -----------------");
        System.out.println("1. Add driver");
        System.out.println("2. Change Driver");
        System.out.println("3. Delete Driver");
        System.out.println("4. Driver Statistics");
        System.out.println("5. Formula1 Championship Driver Table");
        System.out.println("6. Add Race");
        System.out.println("7. GUI - Formula1 Championship");
        System.out.println("8. Save");
        System.out.println("9. Exit");
        System.out.println("---------------------------------------------");
        System.out.print("\nPlease Enter your option : ");
    }


     //throwing IO Exception
    private void load() throws IOException {

        // read teams
        try {
            File teamsFile = new File(String.format("%s%s", DATA_DIRECTORY_PATH,"teams_data.txt"));
            Scanner teamsReader = new Scanner(teamsFile);
            while (teamsReader.hasNextLine()) {
                String teamsData = teamsReader.nextLine();
                if(!teamsData.trim().isEmpty()) {
                    String[] teamsDataArr = teamsData.split(",");
                    if(teamsDataArr.length == 8) {
                        try {
                            Formula1Driver formula1Driver = new Formula1Driver(teamsDataArr[1].trim(),
                                    teamsDataArr[2].trim(),teamsDataArr[0].trim());
                            formula1Driver.setFirstWins(Integer.parseInt(teamsDataArr[3].trim()));
                            formula1Driver.setSecondWins(Integer.parseInt(teamsDataArr[4].trim()));
                            formula1Driver.setThirdWins(Integer.parseInt(teamsDataArr[5].trim()));
                            formula1Driver.setTotalRaces(Integer.parseInt(teamsDataArr[6].trim()));
                            formula1Driver.setPoints(Integer.parseInt(teamsDataArr[7].trim()));
                            driversList.add(formula1Driver);
                        } catch (NumberFormatException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
            teamsReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // read all races
        File allRacesDirectory = new File(String.format("%s%s", DATA_DIRECTORY_PATH,"races_data"));
        FilenameFilter textFilter = (dir, name) -> name.toLowerCase().endsWith(".txt");
        File[] files = allRacesDirectory.listFiles(textFilter);
        assert files != null;
        for (File file : files) {
            if (!file.isDirectory()) {
                try {
                    Formula1Race formula1Race = new Formula1Race(file.getName().replace(".txt","").trim());
                    File raceFile = new File(file.getCanonicalPath());
                    Scanner raceFileReader = new Scanner(raceFile);
                    Map<Driver,Integer> drivers = new HashMap<>();
                    while (raceFileReader.hasNextLine()) {
                        String racesDataLine = raceFileReader.nextLine();
                        if (!racesDataLine.trim().isEmpty()) {
                            String[] racesDataArr = racesDataLine.split(",");
                            if (racesDataArr.length == 4) {
                                drivers.put(new Formula1Driver(racesDataArr[1].trim(),racesDataArr[2].trim(),racesDataArr[3].trim()),
                                        Integer.parseInt(racesDataArr[0].trim()));
                            }
                        }
                    }
                    raceFileReader.close();
                    formula1Race.setParticipatedDrivers(drivers);
                    ALL_FORMULA_1_RACES.add(formula1Race);
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }




    private void save() {
        // delete the races in races directory first
        Arrays.stream(Objects.requireNonNull(new File(String.format("%s%s%s", DATA_DIRECTORY_PATH, "races_data",
                File.separator)).listFiles())).forEach(File::delete);

        // save drivers into drivers.txt
        try {
            FileWriter teamsFileWriter = new FileWriter(String.format("%s%s", DATA_DIRECTORY_PATH,"teams_data.txt"));
            for(Formula1Driver formula1Driver : driversList) {
                teamsFileWriter.append(String.format("%s,%s,%s,%d,%d,%d,%d,%d\n",formula1Driver.getTeamName()
                        ,formula1Driver.getDriverName(),formula1Driver.getTeamLocation()
                        ,formula1Driver.getFirstWins(),formula1Driver.getSecondWins()
                        ,formula1Driver.getThirdWins(),formula1Driver.getTotalRaces()
                        ,formula1Driver.getPoints()));
            }
            teamsFileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // save all races
        for(Formula1Race formula1Race : ALL_FORMULA_1_RACES) {
            try {
                FileWriter raceFileWriter = new FileWriter(String.format("%s%s%s%s.txt", DATA_DIRECTORY_PATH,"races_data"
                        ,File.separator, formula1Race.getDateOfRace().trim().replaceAll(":","_")));
                Map<Driver,Integer> drivers = formula1Race.getParticipatedDrivers();
                for(Driver driver : drivers.keySet()) {
                    raceFileWriter.append(String.format("%d,%s,%s,%s\n",drivers.get(driver), driver.getDriverName(),
                            driver.getTeamLocation(),driver.getTeamName()));
                }
                raceFileWriter.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


     //Shuffles the given integer array randomly

    private int[] shuffleArray(int[] array) {
        Random random = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
        return array;
    }



    private void addParticipants(int[] placesAchieved) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String date = localDateTime.format(DATE_TIME_FORMATTER);
        Map<Driver,Integer> participatedDrivers = new HashMap<>();
        for(int i = 0; i< driversList.size(); i++) {
            Formula1Driver driver = driversList.get(i);
            driver.addNewRace(placesAchieved[i]);
            participatedDrivers.put(new Formula1Driver(driver.getDriverName(),driver.getTeamLocation(),driver.getTeamName())
                    ,placesAchieved[i]);
        }
        Formula1Race formula1Race = new Formula1Race(date,participatedDrivers);
        ALL_FORMULA_1_RACES.add(formula1Race);
        updateRacesHistory(jSearchTextField.getText().trim().toLowerCase());
        updateFormula1DriversGUI(driversList);
    }



    private boolean isDriverExists(String driverName) {
        for(Formula1Driver formula1Driver : driversList) {
            if(formula1Driver.getDriverName().equalsIgnoreCase(driverName)) {
                return true;
            }
        }
        return false;
    }



    private boolean isTeamExists(String teamName) {
        for(Formula1Driver formula1Driver : driversList) {
            if(formula1Driver.getTeamName().equalsIgnoreCase(teamName)) {
                return true;
            }
        }
        return false;
    }

    // GUI


    private void formula1ChampionshipTable() {
        JLabel labelTitle = new JLabel("Formula1 Championship Table");
        labelTitle.setFont(new Font("Lucida Grande", Font.BOLD, 14));
        labelTitle.setForeground(Color.RED);

        JLabel labelPoints = new JLabel("Points");
        labelPoints.setForeground(Color.RED);

        JLabel labelFirstWins = new JLabel("First Wins");
        labelFirstWins.setForeground(Color.RED);

        JButton btnDesc = new JButton("DESC");
        JButton btnAsc = new JButton("ASC");
        JButton btnDesc1 = new JButton("DESC");

        JTable jTable = new JTable();
        jTable.setModel(DEFAULT_TABLE_MODEL);
        jTable.setPreferredSize(new Dimension(750,300));

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(jTable);
        jScrollPane.setPreferredSize(new Dimension(755,305));

        // grid layout ordering
        GroupLayout layout = new GroupLayout(formula1ChampionshipTable.getContentPane());
        formula1ChampionshipTable.getContentPane().setLayout(layout);
        formula1ChampionshipTable.getContentPane().setPreferredSize(new Dimension(800,400));
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(labelPoints)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(labelFirstWins)
                                                .addGap(15, 15, 15))))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(btnDesc)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAsc)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnDesc1)
                                .addGap(15, 15, 15))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(15, 15, 15)
                                                .addComponent(labelTitle))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(15, 15, 15)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(jScrollPane))))
                        .addContainerGap(15, Short.MAX_VALUE)));

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(labelTitle)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelPoints)
                                        .addComponent(labelFirstWins))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnDesc1)
                                        .addComponent(btnAsc)
                                        .addComponent(btnDesc))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane, GroupLayout.PREFERRED_SIZE, 200,
                                        GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        // sorting by first place wins
        // desc button
        btnDesc1.addActionListener(e -> {
            List<Formula1Driver> formula1Drivers = new ArrayList<>(driversList);
            formula1Drivers.sort(new Formula1DriverComparator(false,true,true));
            updateFormula1DriversGUI(formula1Drivers);
        });

        // sorting by points
        // desc button
        btnDesc.addActionListener(e -> {
            List<Formula1Driver> formula1Drivers = new ArrayList<>(driversList);
            formula1Drivers.sort(new Formula1DriverComparator(true,false,true));
            updateFormula1DriversGUI(formula1Drivers);
        });

        // asc button
        btnAsc.addActionListener(e -> {
            List<Formula1Driver> formula1Drivers = new ArrayList<>(driversList);
            formula1Drivers.sort(new Formula1DriverComparator(true,false,false));
            updateFormula1DriversGUI(formula1Drivers);
        });

        formula1ChampionshipTable.pack();
    }



    private void formula1ChampionshipRaceHistory() {

        JLabel labelRaceHistory = new JLabel("Formula1 Championship Table");
        labelRaceHistory.setFont(new Font("Lucida Grande", Font.BOLD, 14));
        labelRaceHistory.setForeground(Color.RED);

        JButton btnRandomRace1 = new JButton("Random Race (1)");
        btnRandomRace1.addActionListener(e -> addRandomRaceWithWeights());

        JButton btnRandomRace2 = new JButton("Random Race (2)");
        btnRandomRace2.addActionListener(e -> addRandomRace());

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setPreferredSize(new Dimension(700,400));
        jTextHistoryArea = new JTextArea();
        jTextHistoryArea.setColumns(25);
        jTextHistoryArea.setRows(30);
        jScrollPane.setViewportView(jTextHistoryArea);
        jSearchTextField = new JTextField();


        // grid layout ordering
        GroupLayout layout = new GroupLayout(formula1ChampionshipRaceHistory.getContentPane());
        formula1ChampionshipRaceHistory.getContentPane().setLayout(layout);
        formula1ChampionshipRaceHistory.getContentPane().setPreferredSize(new Dimension(800,500));
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(15, 15, 15)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(jScrollPane)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment
                                                                                .LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(15, 15, 15)
                                                                                .addComponent(labelRaceHistory))
                                                                        .addComponent(jSearchTextField, GroupLayout.PREFERRED_SIZE,
                                                                                200, GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                                                        15, Short.MAX_VALUE)
                                                                .addComponent(btnRandomRace2, GroupLayout.PREFERRED_SIZE, 200,
                                                                        GroupLayout.PREFERRED_SIZE)
                                                                .addGap(15, 30, 45)
                                                                .addComponent(btnRandomRace1)))))
                                .addContainerGap(30, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(15, 30, 45)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelRaceHistory)
                                .addGap(15, 30, 45)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnRandomRace1)
                                        .addComponent(btnRandomRace2)
                                        .addComponent(jSearchTextField, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane, GroupLayout.PREFERRED_SIZE, 200,
                                        GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        jSearchTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                searching();
            }
            public void removeUpdate(DocumentEvent e) {
                searching();
            }
            public void insertUpdate(DocumentEvent e) {
                searching();
            }

            public void searching () {
                updateRacesHistory(jSearchTextField.getText().trim().toLowerCase());
            }
        });

        formula1ChampionshipRaceHistory.pack();
    }

    //updates automatically given drivers list in the table

    private void updateFormula1DriversGUI(List<Formula1Driver> formula1DriverList) {
        DEFAULT_TABLE_MODEL.setRowCount(0);
        for(Formula1Driver formula1Driver : formula1DriverList) {
            DEFAULT_TABLE_MODEL.addRow(new Object[] {formula1Driver.getTeamName(),formula1Driver.getDriverName(),
                    formula1Driver.getTeamLocation(),formula1Driver.getFirstWins(), formula1Driver.getSecondWins(),
                    formula1Driver.getThirdWins(),formula1Driver.getTotalRaces(), formula1Driver.getPoints()});
        }
    }


     //updates textarea with all races

    private void updateRacesHistory(String keyword) {
        jTextHistoryArea.setText("");
        boolean flag;
        for(Formula1Race formula1Race : Formula1ChampionshipManager.ALL_FORMULA_1_RACES) {
            flag = false;
            StringBuilder builder = new StringBuilder();
            builder.append("*******************************************************").append("\n").append("\n");
            builder.append(String.format("\nDate of Race - %s \n",formula1Race.getDateOfRace()));
            Map<Driver,Integer> participatedDrivers = formula1Race.getParticipatedDrivers();
            for (Map.Entry<Driver, Integer> driverEntry : participatedDrivers.entrySet()) {
                Driver driver = driverEntry.getKey();
                Integer place = driverEntry.getValue();
                if(driver.getDriverName().toLowerCase().contains(keyword)) {
                    flag = true;
                }
                builder.append(String.format("placedAchieved : %d | Driver Name : %s\n", place, driver.getDriverName()));
            }
            builder.append("*******************************************************").append("\n").append("\n");
            if(keyword.isEmpty() || flag) {
                jTextHistoryArea.append(builder.toString());
            }
        }
    }
}
