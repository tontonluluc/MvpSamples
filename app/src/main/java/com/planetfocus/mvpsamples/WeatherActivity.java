package com.planetfocus.mvpsamples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity implements WeatherMVPContract.WeatherMVPView
{
	// used for logging and to uniquely identify our presenter object in the MVPStateMaintainer
	// NOTE: this is not static (so this could go in a base class, along with all the logic that is used to retrieve/build
	// a MVPStateMaintainer).
	private final String TAG=getClass().getSimpleName();


	// the presenter for this view (activity)
	private WeatherPresenter presenter;

	// the state maintainer object, used to retrieve the presenter in case of configuration change
	private final MVPStateMaintainer mvpStateMaintainer=new MVPStateMaintainer(this.getSupportFragmentManager(), TAG);


	//
	// Views
	//
	private Button btnGetWeather;
	private TextView tvTemperature;
	private TextView tvHumidity;
	private TextView tvWindSpeed;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction_list);

		buildOrRetrievePresenter();

		btnGetWeather=(Button)findViewById(R.id.btnGetWeather);
		tvTemperature=(TextView)findViewById(R.id.tvTemperature);
		tvHumidity=(TextView)findViewById(R.id.tvHumidity);
		tvWindSpeed=(TextView)findViewById(R.id.tvWindSpeed);

		btnGetWeather.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				presenter.getWeatherInSanFrancisco();
			}
		});
	}


	private void buildOrRetrievePresenter()
	{
		if (mvpStateMaintainer.firstTimeIn())
		{
			Log.d(TAG, "onCreate() called for the first time");
			initialize(this);
		}
		else
		{
			Log.d(TAG, "onCreate() called more than once");
			reinitialize(this);
		}
	}


	/**
	 * Initialize relevant MVP Objects.
	 * Creates a Presenter instance, saves the presenter in {@link MVPStateMaintainer}
	 */
	private void initialize(WeatherMVPContract.WeatherMVPView view)
	{
		presenter=new WeatherPresenter();
		presenter.attachView(view);

		mvpStateMaintainer.put(WeatherPresenter.class.getSimpleName(), presenter);
	}


	/**
	 * Recovers Presenter and informs Presenter that occurred a config change.
	 * If Presenter has been lost, recreates a instance
	 */
	private void reinitialize(WeatherMVPContract.WeatherMVPView view)
	{
		presenter=mvpStateMaintainer.get(WeatherPresenter.class.getSimpleName());

		if (presenter==null)
		{
			Log.w(TAG, "recreating Presenter");
			initialize(view);
		}
		else
		{
			presenter.attachView(view);
		}
	}


	@Override
	protected void onDestroy()
	{
		presenter.detachView();

		super.onDestroy();
	}


	@Override
	public void showTemperature(Double temperature)
	{
		tvTemperature.setText(""+temperature);
	}


	@Override
	public void showRelativeHumidity(String humidity)
	{
		tvHumidity.setText(humidity);
	}


	@Override
	public void showWindSpeed(Double windSpeed)
	{
		tvWindSpeed.setText(""+windSpeed);
	}
}
