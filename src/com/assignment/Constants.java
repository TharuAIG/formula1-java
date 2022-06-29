package com.assignment;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;


public class Constants {

    // points awarded for each driver represents in the following scheme.
    // index + 1 : score is format follows and index + 1 is the rank
    // and value belongs to the index is the score achieved by the rank - (index + 1) driver
    public static final int[] POINTS_TABLE = new int[] {25,18,15,12,10,8,6,4,2,1};
    // database directory
    public final static String DATA_DIRECTORY_PATH = String.format("%s%s%s%s", Paths.get("").toAbsolutePath()
            , File.separator, "store",File.separator);
    // table alignment format
    public final static String FORMULA1_TABLE_DISPLAY_FORMAT = "| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |" +
            " %-15s | %-15s |%n";
    // date and time formatter
    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public final static DefaultTableModel DEFAULT_TABLE_MODEL = new DefaultTableModel(
            new Object [][] {},
            new String [] {
                    "TEAM NAME", "DRIVER NAME", "LOCATION", "FIRST WINS", "SECOND WINS", "THIRD WINS", "TOTAL RACES",
                    "POINTS"
            }) {
        final Class[] types = new Class [] {
                String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class,
                Integer.class
        };
        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }
    };
}
