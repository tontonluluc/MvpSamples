package com.planetfocus.mvpsamples;

import com.planetfocus.mvpsamples.networking.model.WeatherResponse;

/**
 * Created by jdelpech on 4/22/17.
 */

public class WeatherPresenter extends BasePresenter<WeatherMVPContract.WeatherMVPView> implements WeatherMVPContract.WeatherMVPPresenter
{
	private WeatherMVPContract.WeatherMVPModel model;


	public WeatherPresenter()
	{
		super();
		model=new WeatherModel();
	}


	@Override
	public void getWeatherInSanFrancisco()
	{
		model.loadWeatherInSanFrancisco(new WeatherMVPContract.DataCallback<WeatherResponse>()
		{
			@Override
			public void dataReady(WeatherResponse weatherResponse)
			{
				// get data from response and show on UI
				currentView.showTemperature(weatherResponse.getCurrent_observation().getTemp_c());
				currentView.showRelativeHumidity(weatherResponse.getCurrent_observation().getRelative_humidity());
				currentView.showWindSpeed(weatherResponse.getCurrent_observation().getWind_mph());
			}


			@Override
			public void dataError()
			{

			}
		});
	}
}
