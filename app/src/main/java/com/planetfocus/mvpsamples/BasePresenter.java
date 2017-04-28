package com.planetfocus.mvpsamples;

import android.support.annotation.NonNull;

/**
 * A base class for all our presenters. It provides the basics nuts & bolts of attaching/detaching a presenter to/from a
 * view, as well as the strings resolution class.
 * <p>
 * Created by jdelpech on 4/22/17.
 */

public class BasePresenter<V>
{
	protected V currentView;
	protected StringsUtil stringsUtil;


	public void attachView(@NonNull V newView, StringsUtil stringsUtil)
	{
		currentView=newView;
		this.stringsUtil=stringsUtil;
	}


	public void detachView()
	{
		currentView=null;
		stringsUtil=null;
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
