package com.planetfocus.mvpsamples;

import com.planetfocus.mvpsamples.networking.model.WeatherResponse;

/**
 * Created by jdelpech on 4/22/17.
 */

public interface WeatherMVPContract
{
	/**
	 * The interface by which the view is known to the presenter (i.e., the presenter has a reference to an object of
	 * that interface, typically, Fragment or Activity)
	 */
	interface WeatherMVPView
	{
		void showTemperature(Double temperature);

		void showRelativeHumidity(String humidity);

		void showWindSpeed(Double windSpeed);
	}


	/**
	 * the interface by which the presenter is known to the view (i.e., the view has a reference to an object of this
	 * interface, typically the actual presenter)
	 */
	interface WeatherMVPPresenter
	{
		void getWeatherInSanFrancisco();
	}


	/**
	 * A generic interface that is used to return data from networking client to model and from model to presenter
	 *
	 * @param <T> the type of the data to return to the caller.
	 */
	interface DataCallback<T>
	{
		void dataReady(T data);

		void dataError();
	}


	/**
	 * The interface by which the model is known to the presenter (i.e., the presenter has a reference to an object of
	 * this interface, typically, the actual model)
	 */
	interface WeatherMVPModel
	{
		void loadWeatherInSanFrancisco(DataCallback<WeatherResponse> callback);
	}
}
