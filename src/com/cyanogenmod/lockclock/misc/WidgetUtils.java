/*
 * Copyright (C) 2012 The Android Open Source Project
 * Portions Copyright (C) 2012 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cyanogenmod.lockclock.misc;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.RemoteViews;

import com.cyanogenmod.lockclock.R;

import net.margaritov.preference.colorpicker.ColorPickerPreference;

public class WidgetUtils {
    static final String TAG = "WidgetUtils";

    // Decide whether to show the Weather panel
    public static boolean canFitWeather(Context context, int id, boolean digitalClock) {
        Bundle options = AppWidgetManager.getInstance(context).getAppWidgetOptions(id);
        if (options == null) {
            // no data to make the calculation, show the list anyway
            return true;
        }
        Resources resources = context.getResources();
        int minHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int minHeightPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, minHeight,
                resources.getDisplayMetrics());
        int neededSize = (int) resources.getDimension(
                digitalClock ? R.dimen.min_digital_weather_height : R.dimen.min_analog_weather_height);

        // Check to see if the widget size is big enough, if it is return true.
        return (minHeightPx > neededSize);
    }

    // Decide whether to show the Calendar panel
    public static boolean canFitCalendar(Context context, int id, boolean digitalClock) {
        Bundle options = AppWidgetManager.getInstance(context).getAppWidgetOptions(id);
        if (options == null) {
            // no data to make the calculation, show the list anyway
            return true;
        }
        Resources resources = context.getResources();
        int minHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int minHeightPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, minHeight,
                resources.getDisplayMetrics());
        int neededSize = (int) resources.getDimension(
                digitalClock ? R.dimen.min_digital_calendar_height : R.dimen.min_analog_calendar_height);

        // Check to see if the widget size is big enough, if it is return true.
        return (minHeightPx > neededSize);
    }

    // Calculate the scale factor of the fonts in the widget
    public static float getScaleRatio(Context context, int id) {
        Bundle options = AppWidgetManager.getInstance(context).getAppWidgetOptions(id);
        if (options != null) {
            int minWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
            if (minWidth == 0) {
                // No data , do no scaling
                return 1f;
            }
            Resources res = context.getResources();
            float ratio = minWidth / res.getDimension(R.dimen.def_digital_widget_width);
            return (ratio > 1) ? 1f : ratio;
        }
        return 1f;
    }

    public static void setClockColor(Context context, RemoteViews clockViews, SharedPreferences sp) {
        int colorTime = sp.getInt(Constants.CLOCK_TIME_COLOR,
                context.getResources().getColor(R.color.clock_white));
        int colorDate = sp.getInt(Constants.CLOCK_DATE_COLOR,
                context.getResources().getColor(R.color.clock_white));
        int colorAlarm = sp.getInt(Constants.CLOCK_ALARM_COLOR,
                context.getResources().getColor(R.color.clock_white));
        int colorWeather = sp.getInt(Constants.WEATHER_COLOR,
                context.getResources().getColor(R.color.clock_white));
        int colorCalendar = sp.getInt(Constants.CALENDAR_COLOR,
                context.getResources().getColor(R.color.clock_white));

        clockViews.setTextColor(R.id.clock1_bold, colorTime);
        clockViews.setTextColor(R.id.clock1_regular, colorTime);
        clockViews.setTextColor(R.id.clock2_bold, colorTime);
        clockViews.setTextColor(R.id.clock2_regular, colorTime);
        clockViews.setTextColor(R.id.date_bold, colorDate);
        clockViews.setTextColor(R.id.date_regular, colorDate);
        clockViews.setTextColor(R.id.nextAlarm_bold, colorAlarm);
        clockViews.setTextColor(R.id.nextAlarm_regular, colorAlarm);
        clockViews.setTextColor(R.id.weather_city, colorWeather);
        clockViews.setTextColor(R.id.weather_condition, colorWeather);
        clockViews.setTextColor(R.id.update_time, colorWeather);
        clockViews.setTextColor(R.id.weather_temp, colorWeather);
        clockViews.setTextColor(R.id.weather_low_high, colorWeather);
        clockViews.setTextColor(R.id.calendar_event_title, colorCalendar);
        clockViews.setTextColor(R.id.calendar_event_details, colorCalendar);
    }
}
