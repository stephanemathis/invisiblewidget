package fr.mathis.invisiblewidget;

import android.content.Context;
import android.content.SharedPreferences;

public class DataManager {

	public static String KEY_ALPHA = "alpha";
	
	public static void MemorizeValue(String name, Integer value, Context c) {
		SharedPreferences settings = c.getSharedPreferences("iw", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(name, value);
		editor.commit();
	}

	public static Integer GetMemorizedValue(String name, Context c) {
		SharedPreferences settings = c.getSharedPreferences("iw", 0);
		return settings.getInt(name, 0);
	}
}
