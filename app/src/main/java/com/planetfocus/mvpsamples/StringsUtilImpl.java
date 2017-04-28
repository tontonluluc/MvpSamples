package com.planetfocus.mvpsamples;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * An implementation class of a StringsUtil interface that can provides access to string resolution using a "real"
 * context
 * <p>
 * Created by jdelpech on 4/27/17.
 */

public class StringsUtilImpl implements StringsUtil
{
	private Context appContext;


	public StringsUtilImpl(Context context)
	{
		this.appContext=context.getApplicationContext();
	}


	@NonNull
	@Override
	public String getString(@StringRes int resId)
	{
		return appContext.getString(resId);
	}


	@NonNull
	@Override
	public String getString(@StringRes int resId, Object... formatArgs)
	{
		return appContext.getString(resId, formatArgs);
	}
}
