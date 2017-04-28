package com.planetfocus.mvpsamples;

import com.planetfocus.mvpsamples.networking.model.WeatherResponse;

/**
 * A presenter object that is used to control the business logic of the weather screen.
 * <p>
 * Created by jdelpech on 4/22/17.
 */

public class WeatherPresenter extends BasePresenter<WeatherMVPContract.WeatherMVPView> implements WeatherMVPContract.WeatherMVPPresenter
{
	private WeatherMVPContract.WeatherMVPModel model;


	public WeatherPresenter()
	{
		super();

		// the model is created by the presenter. Since the presenter is retained between view's configuration changes,
		// the view will always use the same model instance (via presenter)
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
				currentView.showTemperature(stringsUtil.getString(R.string.weatherPresenterTemperature, weatherResponse.getCurrent_observation().getTemp_c()));
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
