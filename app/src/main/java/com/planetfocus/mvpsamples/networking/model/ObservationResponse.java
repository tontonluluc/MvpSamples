package com.planetfocus.mvpsamples.networking.model;

/**
 * A POJO that represents the weather data from a single weather observation station.
 * <p>
 * Created by jdelpech on 4/23/17.
 */

public class ObservationResponse
{
	private Double temp_f;
	private Double temp_c;
	private String relative_humidity;
	private Double wind_mph;


	public Double getTemp_f()
	{
		return temp_f;
	}


	public Double getTemp_c()
	{
		return temp_c;
	}


	public String getRelative_humidity()
	{
		return relative_humidity;
	}


	public Double getWind_mph()
	{
		return wind_mph;
	}
}
