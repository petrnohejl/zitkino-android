package cz.petrnohejl.zitkino;

import android.app.Application;
import android.content.Context;

public class ZitkinoApplication extends Application
{
	private static ZitkinoApplication instance;

	public ZitkinoApplication()
	{
		instance = this;
	}

	public static Context getContext()
	{
		return instance;
	}
}
