package util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * Class used for formatting date and determining if date is a valid input.
 */
public class DateUtil {

  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  public static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MMM yyyy");
  public static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
  public static final SimpleDateFormat WEEK_FORMAT = new SimpleDateFormat("yyyy-'W'ww");
  public static final SimpleDateFormat QUARTER_FORMAT = new SimpleDateFormat("yyyy-'Q'");

  /**
   * Converts a given string object from input to a date object.
   *
   * @param dateString date input from user
   * @return a Date object which can be used for the map
   */
  public static Date convertDateStringToDate(String dateString) {
    Date date = null;
    try {
      date = DATE_FORMAT.parse(dateString);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return date;
  }

  /**
   * Converts a given Date of type Date to a string format.
   *
   * @param date date to be converted
   * @return returns the string of provided date.
   */
  public static String convertDateToDateString(Date date) {
    String dateStr = null;
    try {
      dateStr = DATE_FORMAT.format(date);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return dateStr;
  }

  /**
   * Convert Date type to string with specified format.
   *
   * @param date   date to be converted.
   * @param format format of conversion
   * @return formatted date as string
   */
  public static String convertDateToDateStringWithFormat(Date date, SimpleDateFormat format) {
    String dateStr = null;
    try {
      dateStr = format.format(date);

      if (format == QUARTER_FORMAT) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        dateStr += (month / 3) + 1;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return dateStr;
  }

  /**
   * Increment a date by one day.
   *
   * @param date date to be incremented
   * @return Date that has been modified
   */
  public static Date incrementOneDay(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DATE, 1);
    return calendar.getTime();
  }

  /**
   * Increment a Date by one month.
   *
   * @param date date to be modified.
   * @return modified date
   */
  public static Date incrementOneMonth(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MONTH, 1);
    return calendar.getTime();
  }

  /**
   * Increment a Date by one Quarter.
   *
   * @param date date to be modified
   * @return modified date
   */
  public static Date incrementOneQuarter(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MONTH, 3);
    return calendar.getTime();
  }

  /**
   * Increment a Date by one year.
   *
   * @param date date to be modified
   * @return modified date
   */
  public static Date incrementOneYear(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.YEAR, 1);
    return calendar.getTime();
  }

  /**
   * Increments a date by a specified amount.
   * @param date starting date
   * @param increment how many days to increment by
   * @return incremented date
   */
  public static Date addDays(Date date, int increment) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DATE, increment);
    return calendar.getTime();
  }

  /**
   * Iterate to next week.
   * @param date starting date
   * @return iterated date.
   */
  public static Date getNextFriday(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
    return calendar.getTime();
  }

  /**
   * Determines if given dates are in the same month.
   *
   * @param date1 first date for comparison
   * @param date2 second date for comparison
   * @return True if dates are in the same month
   */
  public static boolean isSameMonth(Date date1, Date date2) {
    Calendar calendar1 = Calendar.getInstance();
    calendar1.setTime(date1);
    Calendar calendar2 = Calendar.getInstance();
    calendar2.setTime(date2);

    return calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
  }

  /**
   * Determines if given dates are in the same year.
   *
   * @param date1 first date for comparison
   * @param date2 second date for comparison
   * @return True if dates are in the same year
   */
  public static boolean isSameYear(Date date1, Date date2) {
    Calendar calendar1 = Calendar.getInstance();
    calendar1.setTime(date1);
    Calendar calendar2 = Calendar.getInstance();
    calendar2.setTime(date2);

    return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
  }

  /**
   * Returns the last day of a specifided month.
   *
   * @param date Date of month
   * @return Date of last day in month
   */
  public static Date getLastDayOfMonth(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MONTH, 1);
    calendar.set(Calendar.DATE, 1);
    calendar.add(Calendar.DATE, -1);

    return calendar.getTime();
  }

  /**
   * Get last day of year.
   *
   * @param date date of year.
   * @return date of last day of the year
   */
  public static Date getLastDayOfYear(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.YEAR, 1);
    calendar.set(Calendar.MONTH, Calendar.JANUARY);
    calendar.set(Calendar.DATE, 1);
    calendar.add(Calendar.DATE, -1);

    return calendar.getTime();
  }

  /**
   * Get last day of quarter.
   *
   * @param date date of quarter.
   * @return date of last day of the quarter
   */
  public static Date getLastDayOfQuarter(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    int month = calendar.get(Calendar.MONTH) + 1;
    int increment = (3 - month % 3) % 3;
    calendar.add(Calendar.MONTH, increment);

    calendar.add(Calendar.MONTH, 1);
    calendar.set(Calendar.DATE, 1);
    calendar.add(Calendar.DATE, -1);

    return calendar.getTime();
  }

  /**
   * Determine if given date is in the future from the current day.
   *
   * @param date date to be compared
   * @return true if date is in future
   */
  public static boolean isDateFuture(Date date) {
    String dateToString = DATE_FORMAT.format(date);
    LocalDate localDate = LocalDate.now(ZoneId.systemDefault());

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate inputDate = LocalDate.parse(dateToString, dtf);

    return inputDate.isAfter(localDate);
  }

  /**
   * Determine if date is in proper format.
   *
   * @param date date to be examined
   * @return True if date is in correct format
   */
  public static boolean validateDate(String date) {
    try {
      DATE_FORMAT.parse(date);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  public static double between(Date date1, Date date2, ChronoUnit unit) {
    Instant i1 = Instant.ofEpochMilli(date1.getTime());
    Instant i2 = Instant.ofEpochMilli(date2.getTime());
    LocalDateTime startDate = LocalDateTime.ofInstant(i1, ZoneId.systemDefault());
    LocalDateTime endDate = LocalDateTime.ofInstant(i2, ZoneId.systemDefault());

    return unit.between(startDate, endDate);
  }

  public static Date increment(Date currDate, String intervalType, int interval) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(currDate);
    switch (intervalType) {
      case "Daily":
        calendar.add(Calendar.DATE, interval);
        break;
      case "Weekly":
        calendar.add(Calendar.DATE, interval * 7);
        break;
      case "Monthly":
        calendar.add(Calendar.MONTH, interval);
        break;
      case "Quarterly":
        calendar.add(Calendar.MONTH, interval * 3);
        break;
      case "Yearly":
        calendar.add(Calendar.YEAR, interval);
        break;
    }

    return calendar.getTime();
  }
}
