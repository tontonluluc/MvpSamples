package com.planetfocus.mvpsamples.networking;

import com.planetfocus.mvpsamples.networking.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jdelpech on 4/23/17.
 */

public interface WeatherApi
{
	@GET("conditions/q/CA/San_Francisco.json")
	Call<WeatherResponse> getWeatherInSanFrancisco();


}
