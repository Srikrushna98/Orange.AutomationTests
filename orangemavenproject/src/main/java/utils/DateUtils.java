package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * The type Date utils.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    private static final String CALENDAR_DATE_PATTERN = "EEEE, MMMM d, yyyy";
    private static final String VALUE_DATE_PATTERN = "MM/dd/yyyy";
    private static final String HOURS_MINUTES_PATTERN = "hh:mm a";
    private static final String DATE_HOURS_MINUTES_PATTERN = "MM_dd_yyyy HH_mm";

    /**
     * Gets today calendar.
     *
     * @return the today calendar pattern
     */
    public static String getTodayCalendar() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CALENDAR_DATE_PATTERN, Locale.ENGLISH);
        return simpleDateFormat.format(new Date());
    }

    /**
     * Gets today value.
     *
     * @return the today value pattern
     */
    public static String getTodayValue() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(VALUE_DATE_PATTERN, Locale.ENGLISH);
        return simpleDateFormat.format(new Date());
    }

    /**
     * Gets today time.
     *
     * @return the today time
     */
    public static String getTodayTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HOURS_MINUTES_PATTERN, Locale.ENGLISH);
        return simpleDateFormat.format(new Date());
    }

    /**
     * Gets plus minus hours value.
     *
     * @param hours the hours
     * @return the plus minus hours value
     */
    public static String getPlusMinusHoursValue(int hours) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HOURS_MINUTES_PATTERN, Locale.ENGLISH);
        Date hour = getDatePlusMinusHours(hours);
        return simpleDateFormat.format(hour);
    }

    private static Date getDatePlusMinusHours(int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    /**
     * Gets yesterday calendar.
     *
     * @return the yesterday calendar pattern
     */
    public static String getYesterdayCalendar() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CALENDAR_DATE_PATTERN, Locale.ENGLISH);
        Date yesterday = getDatePlusMinusDays(-1);
        return simpleDateFormat.format(yesterday);
    }

    /**
     * Gets yesterday value.
     *
     * @return the yesterday value pattern
     */
    public static String getYesterdayValue() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(VALUE_DATE_PATTERN, Locale.ENGLISH);
        Date yesterday = getDatePlusMinusDays(-1);
        return simpleDateFormat.format(yesterday);
    }

    /**
     * Gets tomorrow calendar.
     *
     * @return the tomorrow calendar pattern
     */
    public static String getTomorrowCalendar() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CALENDAR_DATE_PATTERN, Locale.ENGLISH);
        Date tomorrow = getDatePlusMinusDays(1);
        return simpleDateFormat.format(tomorrow);
    }

    /**
     * Gets tomorrow calendar.
     *
     * @return the tomorrow calendar pattern
     */
    public static String getTomorrowValue() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(VALUE_DATE_PATTERN, Locale.ENGLISH);
        Date tomorrow = getDatePlusMinusDays(1);
        return simpleDateFormat.format(tomorrow);
    }

    /**
     * Gets plus days value.
     *
     * @param days the days
     * @return the plus days value pattern
     */
    public static String getPlusDaysValue(int days) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(VALUE_DATE_PATTERN, Locale.ENGLISH);
        Date day = getDatePlusMinusDays(days);
        return simpleDateFormat.format(day);
    }

    private static Date getDatePlusMinusDays(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    /**
     * Gets Random day calendar.
     *
     * @return the Random day calendar pattern
     */
    public static String getRandomDayCalendar(int day) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CALENDAR_DATE_PATTERN, Locale.ENGLISH);
        Date randomDayCalender = getDatePlusMinusDays(day);
        return simpleDateFormat.format(randomDayCalender);
    }

    /**
     * Gets Random day value.
     *
     * @return the Random day value pattern
     */
    public static String getRandomDayValue(int day) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(VALUE_DATE_PATTERN, Locale.ENGLISH);
        Date randomDayValue = getDatePlusMinusDays(day);
        return simpleDateFormat.format(randomDayValue);
    }

    /**
     * Input date javascript.
     *
     * @param driver       the driver
     * @param elementId    the element id
     * @param index        the index
     * @param elementValue the element value
     */
    public static void inputDateJavascript(WebDriver driver, String elementId, String index, String elementValue) {
        ((JavascriptExecutor) driver).executeScript(
                String.format("document.getElementsByName('%s')[%s].value = '%s'", elementId, index, elementValue));
    }

    /**
     * Gets random hours minutes.
     *
     * @return the random hours minutes
     */
    public static String getRandomHoursMinutes() {
        final Random random = new Random();
        return String.valueOf(LocalTime.of(random.nextInt(24), random.nextInt(1)));
    }

    /**
     * Generate random twelve hours time format
     * For example, 11:00 PM or 05:00 AM
     *
     * @return String random hours minutes twelve hours format
     */
    public static String getRandomHoursMinutesTwelveHoursFormat() {
        final Random random = new Random();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(HOURS_MINUTES_PATTERN, Locale.ENGLISH);
        LocalTime time = LocalTime.parse(String.valueOf(LocalTime.of(random.nextInt(24), random.nextInt(1))));
        return time.format(dateFormatter);
    }

    /**
     * Gets lastWeek calendar.
     *
     * @return the lastWeek calendar pattern
     */
    public static String getLastWeekDayCalendar() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CALENDAR_DATE_PATTERN, Locale.ENGLISH);
        Date lastWeek = getDatePlusMinusDays(-7);
        return simpleDateFormat.format(lastWeek);
    }

    /**
     * Gets lastweek value.
     *
     * @return the lastweek value pattern
     */
    public static String getLastWeekValue() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(VALUE_DATE_PATTERN, Locale.ENGLISH);
        Date lastWeekValue = getDatePlusMinusDays(-7);
        return simpleDateFormat.format(lastWeekValue);
    }

    /**
     * Gets lastWeek calendar.
     *
     * @return the last two days calendar pattern
     */
    public static String getLastTwoDaysCalendar() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CALENDAR_DATE_PATTERN, Locale.ENGLISH);
        Date lastWeek = getDatePlusMinusDays(-2);
        return simpleDateFormat.format(lastWeek);
    }

    public static String getTodayDateTimeInUTC() {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_HOURS_MINUTES_PATTERN);

        LocalDate ldt = LocalDate.now(ZoneId.of("UTC"));

        String formattedDate = ldt.format(dateTimeFormatter);
        System.out.println(formattedDate);
        return formattedDate;

    }

    /**
     * Gets Beforelastweek value.
     *
     * @return the Beforelastweek value pattern
     */
    public static String getBeforeLastWeekValue() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(VALUE_DATE_PATTERN, Locale.ENGLISH);
        Date lastWeekValue = getDatePlusMinusDays(-14);
        return simpleDateFormat.format(lastWeekValue);
    }

    /*
     * Gets previouslastweek value.
     *
     * @return the previouslastweek value pattern
     */
    public static String getPreviousLastWeekValue() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(VALUE_DATE_PATTERN, Locale.ENGLISH);
        Date lastWeekValue = getDatePlusMinusDays(-14);
        return simpleDateFormat.format(lastWeekValue);
    }

    /**
     * Gets previous 8th value.
     *
     * @return the lastweek value pattern
     */
    public static String get6thDayValue() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(VALUE_DATE_PATTERN, Locale.ENGLISH);
        Date lastWeekValue = getDatePlusMinusDays(-6);
        return simpleDateFormat.format(lastWeekValue);
    }

    /**
     * Gets previous 9th value.
     *
     * @return the lastweek value pattern
     */
    public static String get12thDayValue() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(VALUE_DATE_PATTERN, Locale.ENGLISH);
        Date lastWeekValue = getDatePlusMinusDays(-12);
        return simpleDateFormat.format(lastWeekValue);
    }

    /**
     * Gets previous 7th value.
     *
     * @return the lastweek value pattern
     */
    public static String get5thDayValue() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(VALUE_DATE_PATTERN, Locale.ENGLISH);
        Date lastWeekValue = getDatePlusMinusDays(-5);
        return simpleDateFormat.format(lastWeekValue);
    }

        public static String randomDate() {
        Random rnd = new Random();
        long ms = -946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));
        Date date = new Date(ms);
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");  
        return dateFormat.format(date); 
    }

}