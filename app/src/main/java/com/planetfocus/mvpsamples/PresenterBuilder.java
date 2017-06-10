package com.planetfocus.mvpsamples;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * A generic class that can either build a new, or retrieve an existing presenter for a View.
 * <p>
 * Created by jdelpech on 4/28/17.
 */
public class PresenterBuilder<V, P extends BasePresenter<V>>
{
	private final static String TAG=PresenterBuilder.class.getSimpleName();


	interface PresenterFactory<P>
	{
		@NonNull
		P buildPresenter();

		@NonNull
		String getPresenterTag();

		@NonNull
		StringsUtil buildStringsUtil();
	}


	/**
	 * Provides a presenter. The method will attempt to get the presenter from the application's cache by looking it up
	 * using the presenter factory's key. If no presenter is found there, the method will create a new one. Once a
	 * presenter is created/fetched, it is attached to the view.
	 *
	 * @param weatherApplication the application, used to retrieve or store the presenter in a map
	 * @param view               the view the presenter needs to be attached to.
	 * @param presenterFactory   the factory that is used to actually create the presenter object.
	 * @return the presenter for the view, attached and ready to use.
	 */
	public P buildOrRetrievePresenter(@NonNull WeatherApplication weatherApplication, @NonNull V view, @NonNull PresenterFactory<P> presenterFactory)
	{
		P presenter;
		if (weatherApplication.hasPresenterForKey(presenterFactory.getPresenterTag()))
		{
			presenter=reinitialize(weatherApplication, view, presenterFactory);
			Log.d(TAG, "reinitialized the presenter: "+Integer.toHexString(presenter.hashCode()));
		}
		else
		{
			presenter=initialize(weatherApplication, view, presenterFactory);
			Log.d(TAG, "initializing the presenter: "+Integer.toHexString(presenter.hashCode()));
		}

		return presenter;
	}


	public void clearPresenter(@NonNull WeatherApplication weatherApplication, @NonNull PresenterFactory<P> presenterFactory)
	{
		// using a simple object map saved in the application class
		weatherApplication.removePresenterForKey(presenterFactory.getPresenterTag());
	}


	/**
	 * Creates a new presenter for the view.
	 *
	 * @param weatherApplication the application, used to retrieve or store the presenter in a map
	 * @param view               the view the presenter needs to be attached to.
	 * @param presenterFactory   the factory that is used to actually create the presenter object.
	 * @return the presenter for the view, attached and ready to use.
	 */
	private P initialize(@NonNull WeatherApplication weatherApplication, @NonNull V view, @NonNull PresenterFactory<P> presenterFactory)
	{
//		StringsUtil stringsUtil=new StringsUtilImpl(weatherApplication.getApplicationContext());
		StringsUtil stringsUtil=presenterFactory.buildStringsUtil();

		P presenter=presenterFactory.buildPresenter();
		presenter.attachView(view, stringsUtil);

		// using a simple object map saved in the application class
		weatherApplication.addPresenterForKey(presenterFactory.getPresenterTag(), presenter);

		return presenter;
	}


	/**
	 * retrieves and re-attaches an existing presenter for the view. If the method fails to retrieve a presenter from
	 * the cache, it recreates a new presenter by calling {@link PresenterBuilder#initialize(WeatherApplication, Object,
	 * PresenterFactory)}
	 *
	 * @param weatherApplication the application, used to retrieve or store the presenter in a map
	 * @param view               the view the presenter needs to be attached to.
	 * @param presenterFactory   the factory that is used to actually create the presenter object.
	 * @return the presenter for the view, attached and ready to use.
	 */
	private P reinitialize(@NonNull WeatherApplication weatherApplication, @NonNull V view, @NonNull PresenterFactory presenterFactory)
	{
		// using a simple object map saved in the application class
		P presenter=(P)weatherApplication.getPresenterForKey(presenterFactory.getPresenterTag());

		if (presenter==null)
		{
			initialize(weatherApplication, view, presenterFactory);
		}
		else
		{
			// since we retrieved an existing presenter, we need to pass it a new StringUtils (that is linked to the new context of the new view)
			StringsUtil stringsUtil=new StringsUtilImpl(weatherApplication.getApplicationContext());
			presenter.attachView(view, stringsUtil);
		}

		return presenter;
	}
}
