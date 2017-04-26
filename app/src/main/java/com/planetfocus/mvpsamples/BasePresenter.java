package com.planetfocus.mvpsamples;

import android.support.annotation.NonNull;

/**
 * Created by jdelpech on 4/22/17.
 */

public class BasePresenter<V>
{
	protected V currentView;


	public void attachView(@NonNull V newView)
	{
		currentView=newView;
	}


	public void detachView()
	{
		currentView=null;
	}


	/**
	 * Check if the view is attached.
	 * This checking is only necessary when returning from an asynchronous call
	 *
	 * @return true if a view is attached to this presenter. false otherwise.
	 */
	protected final boolean isViewAttached()
	{
		return currentView!=null;
	}
}
