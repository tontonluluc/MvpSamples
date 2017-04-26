package com.planetfocus.mvpsamples.networking.model;

/**
 * POJO containing the entire response from the weather API.
 * <p>
 * Created by jdelpech on 4/23/17.
 */

public class WeatherResponse
{
	private ObservationResponse current_observation;


	public ObservationResponse getCurrent_observation()
	{
		return current_observation;
	}
}
