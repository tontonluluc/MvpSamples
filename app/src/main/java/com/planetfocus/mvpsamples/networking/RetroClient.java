package com.planetfocus.mvpsamples.networking;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.planetfocus.mvpsamples.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * A retrofit client that can send http online requests.
 * <p>
 * Created by jdelpech on 4/23/17.
 */

public class RetroClient
{
	// references to the classes needed for the web service
	private static Retrofit retrofit;

	// the url variables used to switch between production and debug environments
	private static String defaultBaseUrl=null;

	// timeout constants
	private static final int kProdTimeout=40000;

	// the http client that actually makes the web requests.
	private static OkHttpClient okHttpClient;


	private RetroClient()
	{
		// This class contains only static methods
	}


	/**
	 * This method guarantees the retrofit client is never null. All access to Retrofit for API creation should be
	 * through this method.
	 *
	 * @return The single retrofit instance for the new web service
	 */
	synchronized private static Retrofit getRetrofit()
	{
		if (retrofit==null)
		{
			// the client gets packaged with Retrofit so we don't care about it.
			retrofit=initRetrofit();
		}

		return retrofit;
	}


	/**
	 * Creates a new retrofit object for the RetroClient class and returns the okhttpclient it was initialized with.
	 *
	 * @return the okhttpclient that Retrofit will use
	 */
	private static Retrofit initRetrofit()
	{
		okhttp3.OkHttpClient okHttpClient=buildOkHttpClient();

		retrofit=new Retrofit.Builder()
				.baseUrl(getDefaultBaseUrl()+"/")
				.client(okHttpClient)
				.addConverterFactory(ScalarsConverterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		return retrofit;
	}


	private static OkHttpClient buildOkHttpClient()
	{
		if (okHttpClient==null)
		{
			OkHttpClient.Builder builder=initOkHttpClient();

			// add service headers
			//			addRequestInterceptor(builder);

			okHttpClient=builder.build();
		}

		return okHttpClient;
	}


	private static OkHttpClient.Builder initOkHttpClient()
	{
		OkHttpClient.Builder builder=new OkHttpClient.Builder();

		// set the timeouts
		int timeout=kProdTimeout;
		builder.connectTimeout(timeout, TimeUnit.MILLISECONDS);
		builder.readTimeout(timeout, TimeUnit.MILLISECONDS);
		builder.writeTimeout(timeout, TimeUnit.MILLISECONDS);

		addStethoInterceptor(builder);
		addLoggingInterceptor(builder);

		return builder;
	}


	private static void addStethoInterceptor(OkHttpClient.Builder builder)
	{
		if (BuildConfig.DEBUG)
		{
			// NOTE: Stetho only works if the code is NOT obfuscated. So, if/when adding stetho to a releasedev build type,
			// make sure that build type has "minifyEnabled false" in the app/build.gradle file.
			builder.addNetworkInterceptor(new StethoInterceptor());
		}
	}


	private static void addLoggingInterceptor(OkHttpClient.Builder builder)
	{

	}


	private static String getDefaultBaseUrl()
	{
		if (defaultBaseUrl==null)
		{
			defaultBaseUrl="http://api.wunderground.com/api/397314f611f089e6"; // /conditions/q/CA/San_Francisco.json";
		}

		return defaultBaseUrl;
	}


	public static WeatherApi getWeatherApi()
	{
		return getRetrofit().create(WeatherApi.class);
	}

}
