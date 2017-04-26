package com.planetfocus.mvpsamples;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by jdelpech on 4/24/17.
 */

public class WeatherApplication extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();

		// init Stetho
		if (BuildConfig.DEBUG)
		{
			Stetho.initializeWithDefaults(this);
		}
	}
}
