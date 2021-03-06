/*
* Copyright (C) 2017 The OmniROM Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package com.android.launcher3.topwidget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.android.launcher3.R;
import com.android.launcher3.Utilities;

public class TopWidgetView extends FrameLayout {

    private static final String TAG = "Launcher3:TopWidgetView";
    private static final boolean DEBUG = false;
    private CalendarView mCalendarView;
    private CurrentWeatherView mWeatherView;
    private LayoutInflater mInflater;
    private DetailedWeatherView mDetailedWeatherView;

    public TopWidgetView(Context context) {
        this(context, null);
    }

    public TopWidgetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopWidgetView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void updateSettings() {
        if (mCalendarView != null) {
            mCalendarView.updateSettings();
        }
        if (mWeatherView != null) {
            showDetailedWeather(false);
            mWeatherView.updateSettings();
        }
    }

    public void checkPermissions() {
        if (mCalendarView != null) {
            mCalendarView.checkPermissions();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateTopWidgetVisibility(boolean visible) {
        setVisibility(visible ? View.VISIBLE : View.GONE);
        if (!visible) {
            if (mCalendarView != null) {
                LinearLayout v = (LinearLayout) findViewById(R.id.calendar_view_container);
                v.removeAllViews();
                mCalendarView = null;
            }
            if (mWeatherView != null) {
                LinearLayout v = (LinearLayout) findViewById(R.id.current_weather_view_container);
                v.removeAllViews();
                v = (LinearLayout) findViewById(R.id.detailed_weather_view_container);
                v.removeAllViews();
                mWeatherView = null;
                mDetailedWeatherView = null;
            }
        } else {
            if (mCalendarView == null) {
                mCalendarView = (CalendarView) mInflater.inflate(R.layout.calendar_view, null);
                LinearLayout v = (LinearLayout) findViewById(R.id.calendar_view_container);
                v.addView(mCalendarView, new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            }
            if (mWeatherView == null && Utilities.isShowWeather(getContext())) {
                mWeatherView = (CurrentWeatherView) mInflater.inflate(R.layout.current_weather_view, null);
                LinearLayout v = (LinearLayout) findViewById(R.id.current_weather_view_container);
                v.addView(mWeatherView, new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                mDetailedWeatherView = (DetailedWeatherView) mInflater.inflate(R.layout.detailed_weather_view, null);
                v = (LinearLayout) findViewById(R.id.detailed_weather_view_container);
                v.addView(mDetailedWeatherView, new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

                mWeatherView.setTopWidgetView(this);
                mDetailedWeatherView.setTopWidgetView(this);
                mWeatherView.setDetailedWeatherView(mDetailedWeatherView);
            } else if (mWeatherView != null) {
                LinearLayout v = (LinearLayout) findViewById(R.id.current_weather_view_container);
                v.removeAllViews();
                v = (LinearLayout) findViewById(R.id.detailed_weather_view_container);
                v.removeAllViews();
                mWeatherView = null;
                mDetailedWeatherView = null;
            }
        }
    }

    public void showDetailedWeather(boolean visible) {
        View mainContainer = findViewById(R.id.main_container);
        mainContainer.setVisibility(visible ? View.GONE : View.VISIBLE);

        View detailedView = findViewById(R.id.detailed_weather_view_container);
        detailedView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
