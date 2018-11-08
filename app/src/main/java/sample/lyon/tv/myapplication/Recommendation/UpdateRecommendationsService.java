package sample.lyon.tv.myapplication.Recommendation;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import sample.lyon.tv.myapplication.MainActivity;
import sample.lyon.tv.myapplication.R;
import sample.lyon.tv.myapplication.RecommendationOreo.InitializeChannel;
import sample.lyon.tv.myapplication.Tool.Utils;
import sample.lyon.tv.myapplication.tvUI.Poster;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by i_hfuhsu on 2018/10/18.
 */

public class UpdateRecommendationsService extends IntentService {
    private static final String TAG = UpdateRecommendationsService.class.getName();
    private Bitmap bigIcon;

    public UpdateRecommendationsService() {
        super(TAG);
    }

    private Drawable mDefaultCardImage;
    private NotificationManager mNotificationManager;

    ArrayList<String> channelTitle;
    InitializeChannel initializeChannel;
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "BootupActivity onHandleIntent intent:" + intent);
        final RecommendationBuilder builder = new RecommendationBuilder().setContext(getApplicationContext()).setSmallIcon(R.mipmap.ic_launcher);

        if (mNotificationManager == null)
            mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&  checkTVDevice()){
            channelTitle = new ArrayList<>();
            channelTitle.add("Lyon Title 1");
            channelTitle.add("Lyon Title 2");
            channelTitle.add("Lyon Title 3");
            initializeChannel = new InitializeChannel(getApplicationContext(), channelTitle.get(0));
            getOreoChannel(channelTitle.get(0));
            for(int i=1;i<channelTitle.size();i++) {
                initializeChannel.addNewChannel(channelTitle.get(i));
                getOreoChannel(channelTitle.get(i));
            }


        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int MAX_RECOMMENDATIONS = 5;
                    for (int i = 0; i < MAX_RECOMMENDATIONS; i++) {
                        final RecommendationBuilder notificationBuilder = builder
                                .setId(i + 1)
                                .setPriority(MAX_RECOMMENDATIONS - i - 1)
                                .setTitle("Lyon test " + i)
                                .setBackground("https://upload.wikimedia.org/wikipedia/zh/7/7d/Wolf_of_wall_street.jpg")
                                .setIntent(buildIPendingIntent());
                        Bitmap bitmap = null;
                        try {
                            bitmap = Picasso.with(getApplicationContext()).load("https://upload.wikimedia.org/wikipedia/zh/7/7d/Wolf_of_wall_street.jpg").resize(Utils.dpToPx(259, getApplicationContext()), Utils.dpToPx(388, getApplicationContext())).error(mDefaultCardImage).centerCrop().get();
                        } catch (Exception e) {
                            Log.d(TAG, "Exception" + e);
                        }
                        bigIcon = bitmap;
                        if (null == bigIcon) {
                            bigIcon = BitmapFactory.decodeResource(getResources(), R.drawable.app_icon_your_company);
                        }
                        notificationBuilder.setBitmap(bigIcon);
                        Notification notification = notificationBuilder.build();
                        mNotificationManager.notify(i + 1, notification);
                    }
                }
            }).start();
        }
    }

    private void getOreoChannel(final String channelTitle){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int MAX_RECOMMENDATIONS = 10;
                for (int i = 0; i < MAX_RECOMMENDATIONS; i++) {
                    Poster poster = new Poster();
                    if(initializeChannel!=null) {
                        initializeChannel.createProgram(
                                initializeChannel.getChannelId(channelTitle),
                                poster);
                    }
                }
            }
        }).start();
    }

    private PendingIntent buildIPendingIntent() {
        Intent detailsIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(detailsIntent);
        PendingIntent intent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        return intent;
    }


    private boolean checkTVDevice(){
        boolean isTVDevice = true;
        UiModeManager uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
        if (uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION) {
            Log.d(TAG, "checkTVDevice Running on a TV Device");
            isTVDevice = true;
        } else {
            Log.d(TAG, "checkTVDevice Running on a non-TV Device");
            isTVDevice = false;
        }
        return isTVDevice;
    }
}