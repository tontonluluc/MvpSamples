package com.planetfocus.mvpsamplesarch;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.planetfocus.mvpsamples.R;

public class WeatherActivity extends LifecycleActivity
{
	private final static String TAG=WeatherActivity.class.getSimpleName();

	private LiveDataWeatherViewModel liveDataWeatherViewModel;


	//
	// Views
	//
	private Button btnGetWeather;
	private Button btnUnsubscribe;
	private TextView tvTemperature;
	private TextView tvHumidity;
	private TextView tvWindSpeed;


	//
	// observers
	//
	private Observer<String> temperatureObserver=new Observer<String>()
	{
		@Override
		public void onChanged(@Nullable String temperature)
		{
			tvTemperature.setText(temperature);
			Log.d(TAG, "Updating weather");
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);

		// get the ViewModel object, either a new one or the existing one if activity is recreated after config change.
		liveDataWeatherViewModel=ViewModelProviders.of(this).get(LiveDataWeatherViewModel.class);

		initUserInterface();

		subscribe();
	}


	private void initUserInterface()
	{
		btnGetWeather=(Button)findViewById(R.id.btnGetWeather);
		btnUnsubscribe=(Button)findViewById(R.id.btnUnsubscribe);

		tvTemperature=(TextView)findViewById(R.id.tvTemperature);
		tvHumidity=(TextView)findViewById(R.id.tvHumidity);
		tvWindSpeed=(TextView)findViewById(R.id.tvWindSpeed);

		btnGetWeather.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				liveDataWeatherViewModel.getWeatherInSanFrancisco();
			}
		});

		btnUnsubscribe.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				liveDataWeatherViewModel.getTemperature().removeObserver(temperatureObserver);
			}
		});
	}


	private void subscribe()
	{
		liveDataWeatherViewModel.getTemperature().observe(this, temperatureObserver);
	}
}
