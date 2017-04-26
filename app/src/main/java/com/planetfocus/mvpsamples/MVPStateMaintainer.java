package com.planetfocus.mvpsamples;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that can keep the state of objects through configuration changes.
 * <p>
 * The class uses a retained fragment to save data that need to survive configuration changes.
 * Created by jdelpech on 4/23/17.
 */

public class MVPStateMaintainer
{
	private final String TAG=getClass().getSimpleName();

	// the tag of the retained fragment that is used to actually store the data to survive config changes.
	private final String stateMaintainerTag;

	// the fragment manager where the retained fragment will be saved.
	private final WeakReference<FragmentManager> weakFragmentManager;

	// the retained fragment that contains the data to save during config changes.
	private StateMngFragment stateMaintainerFragment;


	/**
	 * Constructor
	 *
	 * @param fragmentManager    the fragment manager where a retained fragment will be saved to contain the data to
	 *                           survive config changes.
	 * @param stateMaintainerTAG the TAG used to insert the state maintainer fragment
	 */
	public MVPStateMaintainer(FragmentManager fragmentManager, String stateMaintainerTAG)
	{
		weakFragmentManager=new WeakReference<>(fragmentManager);
		stateMaintainerTag=stateMaintainerTAG;
	}


	/**
	 * Create the state maintainer fragment
	 *
	 * @return true: the frag was created for the first time false: recovering the object
	 */
	public boolean firstTimeIn()
	{
		// Recovering the reference
		FragmentManager fm=weakFragmentManager.get();

		if (fm!=null)
		{
			stateMaintainerFragment=(StateMngFragment)fm.findFragmentByTag(stateMaintainerTag);

			// if not already saved, create a new RetainedFragment
			if (stateMaintainerFragment==null)
			{
				Log.d(TAG, "Creating a new RetainedFragment for tag: "+stateMaintainerTag);
				stateMaintainerFragment=new StateMngFragment();
				weakFragmentManager.get().beginTransaction().add(stateMaintainerFragment, stateMaintainerTag).commit();
				return true;
			}
			else
			{
				Log.d(TAG, "Returns a existent retained fragment for tag: "+stateMaintainerTag);
				return false;
			}
		}
		else
		{
			Log.w(TAG, "Error firstTimeIn(). Unable to get a valid FragmentManager");
			return false;
		}
	}


	/**
	 * Insert Object to be preserved during configuration change
	 *
	 * @param key Object's TAG reference
	 * @param obj Object to maintain
	 */
	public void put(String key, Object obj)
	{
		stateMaintainerFragment.put(key, obj);
	}


	/**
	 * Insert Object to be preserved during configuration change
	 * Uses the Object's class name as a TAG reference
	 * Should only be used one time by type class
	 *
	 * @param obj Object to maintain
	 */
	public void put(Object obj)
	{
		put(obj.getClass().getName(), obj);
	}


	/**
	 * Recovers saved object
	 *
	 * @param key TAG reference
	 * @param <T> Class type
	 * @return Objects
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String key)
	{
		return stateMaintainerFragment.get(key);
	}


	/**
	 * Verify the object existence
	 *
	 * @param key Obj TAG
	 */
	public boolean hasKey(String key)
	{
		return stateMaintainerFragment.get(key)!=null;
	}


	/**
	 * Save and manages objects that should be preserved
	 * during configuration changes.
	 */
	public static class StateMngFragment extends Fragment
	{
		private Map<String, Object> mData=new HashMap<>();


		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);

			// Grants that the frag will be preserved
			setRetainInstance(true);
		}


		/**
		 * Insert objects
		 *
		 * @param key reference TAG
		 * @param obj Object to save
		 */
		public void put(String key, Object obj)
		{
			mData.put(key, obj);
		}


		/**
		 * Insert obj using class name as TAG
		 *
		 * @param object obj to save
		 */
		public void put(Object object)
		{
			put(object.getClass().getName(), object);
		}


		/**
		 * Recover obj
		 *
		 * @param key reference TAG
		 * @param <T> Class
		 * @return Obj saved
		 */
		@SuppressWarnings("unchecked")
		public <T> T get(String key)
		{
			return (T)mData.get(key);
		}
	}
}
