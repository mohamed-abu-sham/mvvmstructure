package com.selwantech.raheeb.utils;

import android.content.Context;

import com.selwantech.raheeb.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class TimeUtils {

    public static String calculateTime(Context mContext, String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(date);
            return millisecondToString(mContext, mDate.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
        }

        return "";
    }

    public static String calculateDateTime(String date) {

        try {
            SimpleDateFormat currentFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            return dateFormat.format(currentFormat.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String millisecondToTimeAm(long time) {
        SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
        return sdfs.format(new Date(time));
    }

    private static String millisecondToString(Context mContext, long time) {
        long currentTimeMilli = Calendar.getInstance().getTimeInMillis();
        long defferens = currentTimeMilli - time;

        long dy = TimeUnit.MILLISECONDS.toDays(defferens);
        final long yr = dy / 365;
        dy %= 365;
        final long mn = dy / 30;
        dy %= 30;
        final long wk = dy / 7;
        dy %= 7;
        final long hr = TimeUnit.MILLISECONDS.toHours(defferens)
                - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(defferens));
        final long min = TimeUnit.MILLISECONDS.toMinutes(defferens)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(defferens));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(defferens)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(defferens));
        final long ms = TimeUnit.MILLISECONDS.toMillis(defferens)
                - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(defferens));

        if (yr > 0) {
            return yr + " " + mContext.getResources().getString(R.string.year);
        } else if (mn > 0) {
            return yr + " " + mContext.getResources().getString(R.string.month);
        } else if (wk > 0) {
            return wk + " " + mContext.getResources().getString(R.string.week);
        } else if (dy > 0) {
            return dy + " " + mContext.getResources().getString(R.string.day);
        } else if (hr > 0) {
            return hr + " " + mContext.getResources().getString(R.string.hour);
        } else if (min > 0) {
            return min + " " + mContext.getResources().getString(R.string.minute);
        } else if (sec > 0) {
            return sec + " " + mContext.getResources().getString(R.string.second);
        } else if (ms > 0) {
            return mContext.getResources().getString(R.string.now);
        }
        return "";
    }

    public static String millisecondToMinAndSec(long millis) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    public static String toTwoDigit(int number) {
        return String.format("%02d", number);
    }

    public static String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String formatTimeAmPm(String time) {
        try {
            SimpleDateFormat currentFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
            return dateFormat.format(currentFormat.parse(time));
        } catch (Exception e) {
            e.printStackTrace();
            return time;
        }
    }
}

