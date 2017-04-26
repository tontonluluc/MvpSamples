package com.planetfocus.mvpsamples;

import com.planetfocus.mvpsamples.networking.WeatherWebService;
import com.planetfocus.mvpsamples.networking.model.WeatherResponse;

/**
 * A Data model (or data manager, or data repository) that serves the data to a presenter. It either gets the data from
 * a cache if it is there, or gets it from the network if the data is not in the memory cache. After retrieving data
 * from network, it populates the memory cache.
 * <p>
 * Created by jdelpech on 4/22/17.
 */

public class WeatherModel implements WeatherMVPContract.WeatherMVPModel
{
	@Override
	public void loadWeatherInSanFrancisco(final WeatherMVPContract.DataCallback<WeatherResponse> callback)
	{
		if (DataModelCache.getInstance().getWeatherResponse()!=null)
		{
			// data is here immediately, simply provide it now.
			callback.dataReady(DataModelCache.getInstance().getWeatherResponse());
		}
		else
		{
			WeatherWebService.getWeatherInSanFrancisco(new WeatherMVPContract.DataCallback<WeatherResponse>()
			{
				@Override
				public void dataReady(WeatherResponse data)
				{
					callback.dataReady(data);

					// this is where we also store the data to our memory or persistent storage
					DataModelCache.getInstance().setWeatherResponse(data);
				}


				@Override
				public void dataError()
				{
					callback.dataError();

					// TODO do we need to invalidate the data model cache?
				}
			});
		}
	}
}
