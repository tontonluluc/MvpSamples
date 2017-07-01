package com.planetfocus.mvpsamples;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
	//	private final MVPStateMaintainer mvpStateMaintainer=new MVPStateMaintainer(this.getSupportFragmentManager(), TAG);

	// an object that builds our presenter. uses a presenter factory to do so.
	PresenterBuilder<WeatherMVPContract.WeatherMVPView, WeatherPresenter> presenterBuilder=new PresenterBuilder<>();

	// a factory that is used to build, and in general, to manage the presenter's construction, retrieval and destruction
	PresenterBuilder.PresenterFactory<WeatherPresenter> presenterFactory=new PresenterBuilder.PresenterFactory<WeatherPresenter>()
	{
		@Override
		@NonNull
		public WeatherPresenter buildPresenter()
		{
			return new WeatherPresenter();
		}


		@Override
		@NonNull
		public String getPresenterTag()
		{
			return WeatherPresenter.class.getSimpleName();
		}


		@NonNull
		@Override
		public StringsUtil buildStringsUtil()
		{
			return new StringsUtilImpl(getApplicationContext());
		}
	};


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
		setContentView(R.layout.activity_weather);

		presenter=presenterBuilder.buildOrRetrievePresenter((WeatherApplication)getApplication(), this, presenterFactory);

		btnGetWeather=findViewById(R.id.btnGetWeather);
		tvTemperature=findViewById(R.id.tvTemperature);
		tvHumidity=findViewById(R.id.tvHumidity);
		tvWindSpeed=findViewById(R.id.tvWindSpeed);

		btnGetWeather.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				presenter.getWeatherInSanFrancisco();
			}
		});
	}


	@Override
	protected void onDestroy()
	{
		presenter.detachView();

		if (isFinishing())
		{
			presenterBuilder.clearPresenter((WeatherApplication)getApplication(), presenterFactory);
		}

		super.onDestroy();
	}


	//	private void buildOrRetrievePresenter()
	//	{
	//		if (((WeatherApplication)getApplication()).hasPresenterForKey(WeatherPresenter.class.getSimpleName()))
	//		{
	//			Log.d(TAG, "onCreate() called more than once");
	//			reinitialize(this);
	//		}
	//		else
	//		{
	//			Log.d(TAG, "onCreate() called for the first time");
	//			initialize(this);
	//		}
	//	}


	//	/**
	//	 * Initialize relevant MVP Objects.
	//	 * Creates a Presenter instance, saves the presenter in {@link MVPStateMaintainer}
	//	 */
	//	private void initialize(WeatherMVPContract.WeatherMVPView view)
	//	{
	//		StringsUtil su=new StringsUtilImpl(this);
	//
	//		presenter=new WeatherPresenter();
	//		presenter.attachView(view, su);
	//
	//		// using a simple object map saved in the application class
	//		WeatherApplication weatherApplication=((WeatherApplication)getApplication());
	//		weatherApplication.addPresenterForKey(WeatherPresenter.class.getSimpleName(), presenter);
	//	}


	//	/**
	//	 * Recovers Presenter and informs Presenter that occurred a config change.
	//	 * If Presenter has been lost, recreates a instance
	//	 */
	//	private void reinitialize(WeatherMVPContract.WeatherMVPView view)
	//	{
	//		// using a simple object map saved in the application class
	//		WeatherApplication weatherApplication=((WeatherApplication)getApplication());
	//		presenter=(WeatherPresenter)weatherApplication.getPresenterForKey(WeatherPresenter.class.getSimpleName());
	//
	//		if (presenter==null)
	//		{
	//			Log.w(TAG, "recreating Presenter");
	//			initialize(view);
	//		}
	//		else
	//		{
	//			StringsUtil su=new StringsUtilImpl(this);
	//			presenter.attachView(view, su);
	//		}
	//	}


	@Override
	public void showTemperature(String temperature)
	{
		tvTemperature.setText(temperature);
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
