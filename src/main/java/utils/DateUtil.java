package utils;

import resources.DateFormats;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String convertDateToShortFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DateFormats.DATE_FORMAT_SHORT);
        return dateFormat.format(date);
    }

    public static Date getDatePlusDays(Date date, Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_WEEK, days);
        return calendar.getTime();
    }

    public static Date getFirstWeekendDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        int i = 7;
        do {
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            if (day == Calendar.SATURDAY) {
                date = calendar.getTime();
            }
            i--;
        } while (i>0);
        return date;
    }
}
