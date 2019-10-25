package com.fredrikpedersen.eatingwithfriends_gradedassignment.util;

public class DateFormater {

    private DateFormater() {}

    public static String formatDateText (String date) {
        String[] dateSplit = date.split("-");
        return dateSplit[0] + ". " + formatMonthText(Integer.parseInt(dateSplit[1])) + " " + dateSplit[2];
    }

    private static String formatMonthText(int month) {
        String monthText;
        switch (month) {
            case 2:
                monthText = "Feb";
                break;
            case 3:
                monthText = "Mar";
                break;
            case 4:
                monthText = "Apr";
                break;
            case 5:
                monthText = "May";
                break;
            case 6:
                monthText = "Jun";
                break;
            case 7:
                monthText = "Jul";
                break;
            case 8:
                monthText = "Aug";
                break;
            case 9:
                monthText = "Sep";
                break;
            case 10:
                monthText = "Oct";
                break;
            case 11:
                monthText = "Nov";
                break;
            case 12:
                monthText = "Dec";
                break;
            default:
                monthText = "Jan";
                break;
        }
        return monthText;
    }
}
