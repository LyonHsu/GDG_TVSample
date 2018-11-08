/*
 * Copyright (c) 2017 Google Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package  sample.lyon.tv.myapplication.RecommendationOreo;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.tv.TvContract;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Bundle;
import android.support.media.tv.TvContractCompat;
import android.util.Log;


import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import sample.lyon.tv.myapplication.R;
import sample.lyon.tv.myapplication.Recommendation.RecommendationBuilder;
import sample.lyon.tv.myapplication.Recommendation.UpdateRecommendationsService;


/** Initializes channels and programs at installation time.
 *
 * adb shell am broadcast -a android.media.tv.action.INITIALIZE_PROGRAMS -n \sample.lyon.tv.myapplication/.OreoRecommendation.InitializeChannelsReceiver
 *
 * */
public class InitializeChannelsReceiver extends BroadcastReceiver {

    private static final String TAG = InitializeChannelsReceiver.class.getName();
    private static final String SCHEMA_URI_PREFIX = "tvrecommendation://app/";
    Context context;
    private InitializeChannel initializeChannel = null;
    private Drawable mDefaultCardImage;
    private NotificationManager mNotificationManager;
    private static final String[] CHANNELS_PROJECTION = {
            TvContractCompat.Channels._ID,
            TvContract.Channels.COLUMN_DISPLAY_NAME,
            TvContractCompat.Channels.COLUMN_BROWSABLE
    };
    private static final int MAX_RECOMMENDATIONS = 20;
    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d(TAG, "Oreo Notif BroadcastReceiver onReceive(): " + intent);
        this.context=context;

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent2 = new Intent(context, UpdateRecommendationsService.class);
            context.startService(intent2);
        }
    }

}
