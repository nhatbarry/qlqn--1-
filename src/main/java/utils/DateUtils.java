package utils;

import java.text.SimpleDateFormat;


public class DateUtils {
    static SimpleDateFormat fm = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
    SimpleDateFormat fm2 = new SimpleDateFormat("HH:mm:ss");
    public static String getTime(long milis){
        return fm.format(milis);
    }
}
