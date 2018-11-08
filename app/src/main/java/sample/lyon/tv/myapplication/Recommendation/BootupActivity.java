package sample.lyon.tv.myapplication.Recommendation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by i_hfuhsu on 2018/10/18.
 */

public class BootupActivity extends BroadcastReceiver {
    private static final String TAG = BootupActivity.class.getSimpleName();

    private static final long INITIAL_DELAY = 5000;

    private static final String ChannelAction="android.media.tv.action.INITIALIZE_PROGRAMS";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "BootupActivity initiated");
        if (intent.getAction().endsWith(Intent.ACTION_BOOT_COMPLETED)) {
            scheduleRecommendationUpdate(context);
        }
    }

    public static void scheduleRecommendationUpdate(Context context) {
        Log.d(TAG, "Scheduling recommendations update");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent recommendationIntent = new Intent(context, UpdateRecommendationsService.class);
        PendingIntent alarmIntent = PendingIntent.getService(context, 0, recommendationIntent, 0);

        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                INITIAL_DELAY,
                AlarmManager.INTERVAL_DAY,
                alarmIntent);

        Intent intent = new Intent(ChannelAction);
        context.sendBroadcast(intent);



    }
}
