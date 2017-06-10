package com.planetfocus.mvpsamples;

import android.app.Application;
import android.support.annotation.Nullable;

import com.facebook.stetho.Stetho;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jdelpech on 4/24/17.
 */

public class WeatherApplication extends Application
{
	private Map<String, Object> presenterMap;


	@Override
	public void onCreate()
	{
		super.onCreate();

		// init Stetho
		if (BuildConfig.DEBUG)
		{
			Stetho.initializeWithDefaults(this);
		}

		initPresenterMap();
	}


	void initPresenterMap()
	{
		presenterMap=new HashMap<String, Object>();
	}


	public void addPresenterForKey(String presenterKey, Object presenter)
	{
		presenterMap.put(presenterKey, presenter);
	}


	@Nullable
	public Object getPresenterForKey(String presenterKey)
	{
		return presenterMap.get(presenterKey);
	}


	public boolean hasPresenterForKey(String presenterKey)
	{
		return presenterMap.containsKey(presenterKey);
	}


	public void removePresenterForKey(String presenterKey)
	{
		presenterMap.remove(presenterKey);
	}
}
