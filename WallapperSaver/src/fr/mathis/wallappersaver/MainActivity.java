package fr.mathis.wallappersaver;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_main);
		
		ImageView ivWallpaper = (ImageView) findViewById(R.id.iv_wallpaper);
		TextView tvSave = (TextView) findViewById(R.id.tv_save);

		ivWallpaper.setImageDrawable(WallpaperManager.getInstance(this).getDrawable());

		tvSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent share = new Intent(Intent.ACTION_SEND);
				share.setType("image/jpeg");
				String url = Images.Media.insertImage(MainActivity.this.getContentResolver(), drawableToBitmap(WallpaperManager.getInstance(MainActivity.this).getDrawable()), "wallpaper", null);
				if (url == null) {
					;
				} else {
					share.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
					startActivity(Intent.createChooser(share, getString(R.string.saveTitle)));
				}
			}
		});
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}

}
