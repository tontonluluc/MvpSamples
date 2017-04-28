package com.planetfocus.mvpsamples.networking;

import android.os.Handler;
import android.util.Log;

import com.planetfocus.mvpsamples.WeatherMVPContract;
import com.planetfocus.mvpsamples.networking.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A class that fetches weather data from a web service API and returns it to a presenter.
 * <p>
 * Created by jdelpech on 4/23/17.
 */

public class WeatherWebService
{
	private final static String TAG=WeatherWebService.class.getSimpleName();


	// all our methods are static in this class, so prevents the instantiation of an actual object of that class.
	private WeatherWebService()
	{

	}


	public static void getWeatherInSanFrancisco(final WeatherMVPContract.DataCallback<WeatherResponse> callback)
	{
		// TEST //
		final Handler handler=new Handler();
		// TEST //

		Call<WeatherResponse> call=RetroClient.getWeatherApi().getWeatherInSanFrancisco();
		call.enqueue(new Callback<WeatherResponse>()
		{
			@Override
			public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response)
			{
				if (response.isSuccessful())
				{
					final WeatherResponse weatherResponse=response.body();

					Log.d(TAG, "Temperature: "+weatherResponse.getCurrent_observation().getTemp_c());

//					handler.postDelayed(new Runnable()
//					{
//						@Override
//						public void run()
//						{
							callback.dataReady(weatherResponse);
//						}
//					}, 10000L);

					// call callback success
					// callback.dataReady(weatherResponse);
				}
				else
				{
					// call callback error
					callback.dataError();
				}
			}


			@Override
			public void onFailure(Call<WeatherResponse> call, Throwable t)
			{
				callback.dataError();
			}
		});
	}
}
