package com.a10h3y.paywhat.Tools;

import android.util.Log;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by guandongchen on 2016/10/10.
 */
public class DateTools {

    private static Calendar nowCalendar;

    private static Calendar afterCalendar;

    private static DateTools ourInstance = new DateTools();

    public static DateTools getInstance() {

        return ourInstance;
    }

    private DateTools() {

        nowCalendar = Calendar.getInstance(Locale.getDefault());

    }

    /**
     * 获取当前时间距离目标时间的距离
     * @param day
     * @return
     */
    public static int getDistanceDays(int day){

        afterCalendar = Calendar.getInstance(Locale.getDefault());

        if (nowCalendar.get(Calendar.DATE)>day){

            afterCalendar.add(Calendar.MONTH,1);

            afterCalendar.set(Calendar.DAY_OF_MONTH,day);

            afterCalendar.set(Calendar.YEAR,nowCalendar.get(Calendar.YEAR));

            return  (int) ((afterCalendar.getTime().getTime()-nowCalendar.getTime().getTime())/(24*60*60*1000));

        }else if ((nowCalendar.get(Calendar.DATE)==day)) {

            return 0;

        }else{

            Log.i("TAG", "getDistanceDays: "+nowCalendar.get(Calendar.DATE));

            return day-nowCalendar.get(Calendar.DATE);
        }

    }


}
