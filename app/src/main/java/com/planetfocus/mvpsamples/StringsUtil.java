package com.planetfocus.mvpsamples;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * An interface that represents an object used to provide strings to the components involved in the MVP pattern.
 * <p>
 * Created by jdelpech on 4/27/17.
 */

public interface StringsUtil
{
	@NonNull
	String getString(@StringRes int resId);

	@NonNull
	String getString(@StringRes int resId, Object... formatArgs);
}
