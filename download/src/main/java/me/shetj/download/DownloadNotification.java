package me.shetj.download;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;

/**
 * Helper class for showing and canceling download
 * notifications.
 * <p>
 * This class makes heavy use of the {@link NotificationCompat.Builder} helper
 * class to create notifications in a backward-compatible way.
 */
public class DownloadNotification {
	/**
	 * The unique identifier for this type of notification.
	 */
	private static final String NOTIFICATION_TAG = "Download";

	/**
	 *
	 * @see #cancel(Context)
	 */
	public static Notification notify(final Context context, final int draw, final String title,
	                          final String exampleString, final int number) {
		final Resources res = context.getResources();
		final Bitmap picture = BitmapFactory.decodeResource(res, R.drawable.icon_loading);
		final String ticker = title;
		final String text = exampleString;
		final NotificationCompat.Builder builder = new NotificationCompat.Builder(context,exampleString.hashCode()+"")
						.setDefaults(Notification.DEFAULT_ALL)
						.setSmallIcon(draw)
						.setContentTitle(title)
						.setContentText(text)
						.setPriority(NotificationCompat.PRIORITY_DEFAULT)
						.setLargeIcon(picture)
						.setTicker(ticker)
						.setChannelId(NOTIFICATION_TAG)
						.setNumber(number)
						.setContentIntent(
										PendingIntent.getActivity(
														context,
														0,
														new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")),
														PendingIntent.FLAG_UPDATE_CURRENT))
						.setAutoCancel(true);

		Notification build = builder.build();
		notify(context, build);
		return build;
	}

	@TargetApi(Build.VERSION_CODES.ECLAIR)
	private static void notify(final Context context, final Notification notification) {
		final NotificationManager nm = (NotificationManager) context
						.getSystemService(Context.NOTIFICATION_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
			nm.notify(NOTIFICATION_TAG, 0, notification);
		} else {
			nm.notify(NOTIFICATION_TAG.hashCode(), notification);
		}
	}

	/**
	 * Cancels any notifications of this type previously shown using
	 * {@link #notify(Context, int, String,String, int)}.
	 */
	@TargetApi(Build.VERSION_CODES.ECLAIR)
	public static void cancel(final Context context) {
		final NotificationManager nm = (NotificationManager) context
						.getSystemService(Context.NOTIFICATION_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
			nm.cancel(NOTIFICATION_TAG, 0);
		} else {
			nm.cancel(NOTIFICATION_TAG.hashCode());
		}
	}
}
