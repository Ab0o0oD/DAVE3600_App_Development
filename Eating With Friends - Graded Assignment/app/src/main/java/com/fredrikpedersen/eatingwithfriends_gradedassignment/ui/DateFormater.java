package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui;

public class DateFormater {

    private DateFormater() {}

    public static String formatDateText (String date) {
        String[] dateSplit = date.split("-");
        return dateSplit[0] + ". " + formatMonthText(Integer.parseInt(dateSplit[1])) + " " + dateSplit[2];
    }

    private static String formatMonthText(int month) {
        String monthText = "";
        switch (month) {
            case 0:
                monthText = "Jan";
                break;
            case 1:
                monthText = "Feb";
                break;
            case 2:
                monthText = "Mar";
                break;
            case 3:
                monthText = "Apr";
                break;
            case 4:
                monthText = "May";
                break;
            case 5:
                monthText = "Jun";
                break;
            case 6:
                monthText = "Jul";
                break;
            case 7:
                monthText = "Aug";
                break;
            case 8:
                monthText = "Sep";
                break;
            case 9:
                monthText = "Oct";
                break;
            case 10:
                monthText = "Nov";
                break;
            case 11:
                monthText = "Dec";
                break;
            default:
                break;
        }
        return monthText;
    }
}
