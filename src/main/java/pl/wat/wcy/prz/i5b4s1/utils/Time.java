package pl.wat.wcy.prz.i5b4s1.utils;

public class Time {
    private long dayofweek;
    private String dayofweekName;
    private long day;
    private long month;
    private String monthName;
    private long year;
    private long hours;
    private long minutes;
    private long seconds;
    private long millis;
    private String fulldate;
    private String timezone;
    private String status;

    public long getDayofweek() { return dayofweek; }
    public void setDayofweek(long value) { this.dayofweek = value; }

    public String getDayofweekName() { return dayofweekName; }
    public void setDayofweekName(String value) { this.dayofweekName = value; }

    public long getDay() { return day; }
    public void setDay(long value) { this.day = value; }

    public long getMonth() { return month; }
    public void setMonth(long value) { this.month = value; }

    public String getMonthName() { return monthName; }
    public void setMonthName(String value) { this.monthName = value; }

    public long getYear() { return year; }
    public void setYear(long value) { this.year = value; }

    public long getHours() { return hours; }
    public void setHours(long value) { this.hours = value; }

    public long getMinutes() { return minutes; }
    public void setMinutes(long value) { this.minutes = value; }

    public long getSeconds() { return seconds; }
    public void setSeconds(long value) { this.seconds = value; }

    public long getMillis() { return millis; }
    public void setMillis(long value) { this.millis = value; }

    public String getFulldate() { return fulldate; }
    public void setFulldate(String value) { this.fulldate = value; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String value) { this.timezone = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }
}