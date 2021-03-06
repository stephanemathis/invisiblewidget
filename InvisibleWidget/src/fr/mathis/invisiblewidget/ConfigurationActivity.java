package fr.mathis.invisiblewidget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class ConfigurationActivity extends Activity {

	int alphaValue = 0;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_configuration);
		getActionBar().setDisplayShowHomeEnabled(false);
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			getWindow().setStatusBarColor(getResources().getColor(R.color.blue_color_dark));
		}

		SeekBar sb_alpha = (SeekBar) findViewById(R.id.sb_alpha);
		ImageView iv_wallpaper = (ImageView) findViewById(R.id.iv_background);
		final View v_overlay = findViewById(R.id.v_overlay);

		alphaValue = DataManager.GetMemorizedValue(DataManager.KEY_ALPHA, this);

		sb_alpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@SuppressWarnings("deprecation")
			@SuppressLint("NewApi")
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				alphaValue = progress;
				Drawable d = new ColorDrawable(Color.parseColor(getString(R.color.blue_color)));
				d.setAlpha(alphaValue);

				if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN)
					v_overlay.setBackgroundDrawable(d);
				else
					v_overlay.setBackground(d);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});

		sb_alpha.setProgress(alphaValue);
		iv_wallpaper.setImageDrawable(WallpaperManager.getInstance(this).getDrawable());

		if (findViewById(R.id.marginTop) != null) {
			LinearLayout marginTop = (LinearLayout) findViewById(R.id.marginTop);
			marginTop.setPadding(0, getStatusBarHeight(), 0, 0);
		}
	}

	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	@Override
	protected void onPause() {
		super.onPause();
		DataManager.MemorizeValue(DataManager.KEY_ALPHA, alphaValue, this);

		Intent intent = new Intent(this, SmallWidget.class);
		intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
		int[] ids = { R.xml.small_widget_meta };
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
		sendBroadcast(intent);
	}

}
