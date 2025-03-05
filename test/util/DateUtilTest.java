package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

/**
 * Testing the date formatting.
 */
public class DateUtilTest {

  @Test
  public void convertDateStringToDateTest() {
    Date date = DateUtil.convertDateStringToDate("2022-10-10");

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String strDate = dateFormat.format(date);

    Assert.assertEquals(strDate, "2022-10-10");
  }

  @Test
  public void convertDateToDateStringTest() {
    Date date = DateUtil.convertDateStringToDate("2021-01-10");
    String strDate = DateUtil.convertDateToDateString(date);

    Assert.assertEquals(strDate, "2021-01-10");
  }

  @Test
  public void convertDateToDateStringWithFormatTest() {
    Date date = DateUtil.convertDateStringToDate("2021-01-10");
    String strDate = DateUtil.convertDateToDateStringWithFormat(date, DateUtil.MONTH_FORMAT);

    Assert.assertEquals(strDate, "Jan 2021");
  }

  @Test
  public void isDateFutureTrueTest() {
    Date date = DateUtil.convertDateStringToDate("3022-11-10");
    Assert.assertTrue(DateUtil.isDateFuture(date));
  }

  @Test
  public void isDateFutureFalseTest() {
    Date date = DateUtil.convertDateStringToDate("2022-01-10");
    Assert.assertFalse(DateUtil.isDateFuture(date));
  }

  @Test
  public void incrementOneDayTest() {
    Date date = DateUtil.convertDateStringToDate("2022-01-10");
    Date date1 = DateUtil.incrementOneDay(date);
    String strDate = DateUtil.convertDateToDateString(date1);

    Assert.assertEquals(strDate, "2022-01-11");
  }

  @Test
  public void incrementOneMonthTest() {
    Date date = DateUtil.convertDateStringToDate("2022-01-10");
    Date date1 = DateUtil.incrementOneMonth(date);
    String strDate = DateUtil.convertDateToDateString(date1);

    Assert.assertEquals(strDate, "2022-02-10");
  }

  @Test
  public void incrementOneYearTest() {
    Date date = DateUtil.convertDateStringToDate("2022-01-10");
    Date date1 = DateUtil.incrementOneYear(date);
    String strDate = DateUtil.convertDateToDateString(date1);

    Assert.assertEquals(strDate, "2023-01-10");
  }

  @Test
  public void getNextFridayTest() {
    Date date = DateUtil.convertDateStringToDate("2022-11-16");
    Date date1 = DateUtil.getNextFriday(date);
    String strDate = DateUtil.convertDateToDateString(date1);

    Assert.assertEquals(strDate, "2022-11-18");
  }

  @Test
  public void isSameMonthTrueTest() {
    Date date1 = DateUtil.convertDateStringToDate("2022-01-10");
    Date date2 = DateUtil.convertDateStringToDate("2023-01-18");
    boolean isSameMonth = DateUtil.isSameMonth(date1, date2);

    Assert.assertTrue(isSameMonth);
  }

  @Test
  public void isSameMonthFalseTest() {
    Date date1 = DateUtil.convertDateStringToDate("2022-01-10");
    Date date2 = DateUtil.convertDateStringToDate("2023-04-18");
    boolean isSameMonth = DateUtil.isSameMonth(date1, date2);

    Assert.assertFalse(isSameMonth);
  }

  @Test
  public void isSameYearTrueTest() {
    Date date1 = DateUtil.convertDateStringToDate("2022-01-10");
    Date date2 = DateUtil.convertDateStringToDate("2022-04-18");
    boolean isSameYear = DateUtil.isSameYear(date1, date2);

    Assert.assertTrue(isSameYear);
  }

  @Test
  public void isSameYearFalseTest() {
    Date date1 = DateUtil.convertDateStringToDate("2022-01-10");
    Date date2 = DateUtil.convertDateStringToDate("2023-04-18");
    boolean isSameYear = DateUtil.isSameYear(date1, date2);

    Assert.assertFalse(isSameYear);
  }

  @Test
  public void getLastDayOfMonthTest() {
    Date date = DateUtil.convertDateStringToDate("2022-02-10");
    Date date1 = DateUtil.getLastDayOfMonth(date);
    String strDate = DateUtil.convertDateToDateString(date1);

    Assert.assertEquals(strDate, "2022-02-28");
  }

  @Test
  public void getLastDayOfYearTest() {
    Date date = DateUtil.convertDateStringToDate("2022-01-10");
    Date date1 = DateUtil.getLastDayOfYear(date);
    String strDate = DateUtil.convertDateToDateString(date1);

    Assert.assertEquals(strDate, "2022-12-31");
  }

  @Test
  public void validateDateTest() throws Exception {
    boolean validateDate = DateUtil.validateDate("2022-01-10");
    Assert.assertTrue(validateDate);
  }

}
