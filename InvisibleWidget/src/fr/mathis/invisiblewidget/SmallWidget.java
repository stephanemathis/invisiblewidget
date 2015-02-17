package fr.mathis.invisiblewidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;

public class SmallWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		updateBackgroundColor(context);
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);

		updateBackgroundColor(context);
	}

	private void updateBackgroundColor(Context context) {
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.small_widget);
		views.setInt(R.id.iv_widget_background, "setBackgroundColor", getColor(context));

		if (DataManager.GetMemorizedValue("alpha", context) != 0) {
			Intent iSetting = new Intent(context, ConfigurationActivity.class);
			PendingIntent piSetting = PendingIntent.getActivity(context, 0, iSetting, 0);
			views.setOnClickPendingIntent(R.id.iv_widget_background, piSetting);
		}
		else {
			views.setOnClickPendingIntent(R.id.iv_widget_background, null);
		}

		AppWidgetManager mgr = AppWidgetManager.getInstance(context);
		ComponentName me = new ComponentName(context, SmallWidget.class);
		mgr.updateAppWidget(me, views);
	}

	private static int getColor(Context c) {
		int blueColor = Color.parseColor(c.getString(R.color.blue_color));
		return Color.argb(DataManager.GetMemorizedValue("alpha", c), Color.red(blueColor), Color.green(blueColor), Color.blue(blueColor));
	}

}
