package utils;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.JTextField;

public class DateUtils {
    static SimpleDateFormat fm = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
    SimpleDateFormat fm2 = new SimpleDateFormat("HH:mm:ss");
    public static String getTime(long milis){
        return fm.format(milis);
    }
}
