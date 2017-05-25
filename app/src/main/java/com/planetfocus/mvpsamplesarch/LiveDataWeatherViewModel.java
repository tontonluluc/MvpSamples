package com.planetfocus.mvpsamplesarch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.planetfocus.mvpsamples.WeatherMVPContract;
import com.planetfocus.mvpsamples.WeatherModel;
import com.planetfocus.mvpsamples.networking.model.WeatherResponse;

/**
 * A ViewModel object that provides the data from the weather model and provides the business logic for the weather
 * view.
 * <p>
 * Created by jdelpech on 5/21/17.
 */
public class LiveDataWeatherViewModel extends ViewModel implements WeatherMVPContract.WeatherMVPPresenter
{
	private MutableLiveData<String> temperatureLiveData=new MutableLiveData<>();

	private WeatherMVPContract.WeatherMVPModel model;


	public LiveDataWeatherViewModel()
	{
		// the model is created by the presenter. Since the presenter is retained between view's configuration changes,
		// the view will always use the same model instance (via presenter)
		model=new WeatherModel();
	}


	/**
	 * A LiveData observable object that provides the temperature in celcius.
	 *
	 * @return a temperature {@see String} observable object.
	 */
	public LiveData<String> getTemperature()
	{
		return temperatureLiveData;
	}


	@Override
	public void getWeatherInSanFrancisco()
	{
		model.loadWeatherInSanFrancisco(new WeatherMVPContract.DataCallback<WeatherResponse>()
		{
			@Override
			public void dataReady(WeatherResponse weatherResponse)
			{
				temperatureLiveData.setValue(String.valueOf(weatherResponse.getCurrent_observation().getTemp_c()));

				//				// get data from response and show on UI
				//				currentView.showTemperature(stringsUtil.getString(R.string.weatherPresenterTemperature, weatherResponse.getCurrent_observation().getTemp_c()));
				//				currentView.showRelativeHumidity(weatherResponse.getCurrent_observation().getRelative_humidity());
				//				currentView.showWindSpeed(weatherResponse.getCurrent_observation().getWind_mph());
			}


			@Override
			public void dataError()
			{

			}
		});
	}
}
