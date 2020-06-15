//*********************************************************************************
//  File:             AlarmNotificationManager.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static edu.wgu.grimes.c196pa.utilities.Constants.CHANNEL_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ALARM_KEY_ID;
import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ALARM_MESSAGE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ALARM_TITLE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_END_ALERT_CHANNEL;
import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_START_ALERT_CHANNEL;

public class AlarmNotificationManager {

    private static final String TAG = "alarm";
    private static Map<Integer, RequestCode> requestCodesByCourseId = new HashMap<>();

    private static AlarmNotificationManager instance;

    public static synchronized AlarmNotificationManager getInstance() {
        if (instance == null) {
            instance = new AlarmNotificationManager();
        }
        return instance;
    }

    public void registerAlarmNotification(Context context, Date alarmDate, int courseId,
                                          String type, String title, String message) {
        if (!requestCodesByCourseId.containsKey(courseId)) {
            RequestCode request = new RequestCode();
            request.courseId = courseId;
            requestCodesByCourseId.put(courseId, request);
        }
        RequestCode request = requestCodesByCourseId.get(courseId);
        switch (type) {
            case "start":
                request.startDateAlarmRequestCode = generateKey();
                break;
            case "end":
                request.endDateAlarmRequestCode = generateKey();
                break;
        }

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        boolean start = "start".equals(type);
        int key =  start ? request.startDateAlarmRequestCode : request.endDateAlarmRequestCode;
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra(COURSE_ALARM_TITLE_ID_KEY, title);
        intent.putExtra(COURSE_ALARM_MESSAGE_ID_KEY, message);
        intent.putExtra(COURSE_ALARM_KEY_ID, key);
        intent.putExtra(CHANNEL_ID_KEY, start ? COURSE_START_ALERT_CHANNEL : COURSE_END_ALERT_CHANNEL);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, key, intent, 0);

        Log.i(TAG, "handleAlarms: start date alarm: " + alarmDate + " on channel "
                + intent.getExtras().getString(CHANNEL_ID_KEY));

        if (alarmDate == null) {
            alarmManager.cancel(pendingIntent);
            Log.i(TAG, "registerAlarmNotification: " + type + " alarm cancelled");
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmDate.getTime(), pendingIntent);
            Log.i(TAG, "registerAlarmNotification: " + type + " alarm scheduled");
        }
    }

    private int generateKey() {
        boolean finished = false;
        int count = 0;
        int key = 0;
        Random random = new Random();
        while (!finished && count < 1000) {
            int i = random.nextInt();
            if (!requestCodesByCourseId.containsValue(i)) {
                key = i;
                finished = true;
                count++;
            }
        }
        return key;
    }

    private static class RequestCode {
        int courseId;
        int startDateAlarmRequestCode;
        int endDateAlarmRequestCode;

        @NonNull
        @Override
        public String toString() {
            return "RequestCode{" +
                    "courseId=" + courseId +
                    ", startDateAlarmRequestCode=" + startDateAlarmRequestCode +
                    ", endDateAlarmRequestCode=" + endDateAlarmRequestCode +
                    '}';
        }
    }
}
