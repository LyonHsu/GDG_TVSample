package sample.lyon.tv.myapplication.Recommendation;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by i_hfuhsu on 2018/10/18.
 */

public class RecommendationBuilder {
    private static final String TAG = RecommendationBuilder.class.getSimpleName();

    private static int mId;
    private static int mPriority;
    private static int mSmallIcon;
    private static Context mContext;
    private static String mBackgroundUri;
    private static String mImageUri;
    private static String mTitle;
    private static String mDescription;
    private String mSort;
    private static PendingIntent mIntent;
    private static Bitmap mBitmap;
    private String mGroupKey;


    public RecommendationBuilder setContext(Context context) {
        mContext = context;
        return this;
    }

    public RecommendationBuilder setSmallIcon(int resourceId) {
        mSmallIcon = resourceId;
        return this;
    }

    public RecommendationBuilder setBackground(String uri) {
        mBackgroundUri = uri;
        return this;
    }

    public RecommendationBuilder setId(int id) {
        mId = id;
        return this;
    }

    public RecommendationBuilder setPriority(int priority) {
        mPriority = priority;
        return this;
    }

    public RecommendationBuilder setTitle(String title) {
        mTitle = title;
        return this;
    }

    public RecommendationBuilder setDescription(String description) {
        mDescription = description;
        return this;
    }

    public RecommendationBuilder setIntent(PendingIntent intent) {
        mIntent = intent;
        return this;
    }

    public RecommendationBuilder setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        return this;
    }

    public RecommendationBuilder setImage(String uri) {
        mImageUri = uri;
        return this;
    }

    public Notification build() {

        Bundle extras = new Bundle();
        File bitmapFile = getNotificationBackground(mContext, mId);

        if (mBackgroundUri != null) {
            extras.putString(Notification.EXTRA_BACKGROUND_IMAGE_URI, mBackgroundUri);
        }


        // save bitmap into files for content provider to serve later
        try {
            bitmapFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(bitmapFile);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException ioe) {
            Log.d(TAG, "Exception caught writing bitmap to file!"+ioe);
        } catch (Exception e){
            Log.d(TAG, "Exception  bitmap to file!"+e);
        }

        Notification notification = new NotificationCompat.BigPictureStyle(
                new NotificationCompat.Builder(mContext)
                        .setContentTitle(mTitle)
                        .setContentText(mDescription)
                        .setPriority(mPriority)
                        .setLargeIcon(mBitmap)
                        .setLocalOnly(true)
                        .setOngoing(true)
                        .setColor(Color.blue(155))
                        .setCategory(Notification.CATEGORY_RECOMMENDATION)
                        .setSmallIcon(mSmallIcon)
                        .setContentIntent(mIntent)
//                        .setExtras(extras)
        )
                .build();


        Log.d(TAG, "Building notification - " + this.toString());

        return notification;
    }

    private static File getNotificationBackground(Context context, int notificationId) {
        return new File(context.getCacheDir(), "tmp" + Integer.toString(notificationId) + ".png");
    }
}
