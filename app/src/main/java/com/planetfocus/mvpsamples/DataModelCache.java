package com.planetfocus.mvpsamples;

import com.planetfocus.mvpsamples.networking.model.WeatherResponse;

/**
 * Created by jdelpech on 4/25/17.
 */

public class DataModelCache
{
	private static DataModelCache dataModelCacheInstance;


	private DataModelCache()
	{

	}


	public static DataModelCache getInstance()
	{
		if (dataModelCacheInstance==null)
		{
			dataModelCacheInstance=new DataModelCache();
		}

		return dataModelCacheInstance;
	}


	private WeatherResponse weatherResponse;


	public WeatherResponse getWeatherResponse()
	{
		return weatherResponse;
	}


	public void setWeatherResponse(WeatherResponse weatherResponse)
	{
		this.weatherResponse=weatherResponse;
	}
}
