package sample.lyon.tv.myapplication.RecommendationOreo;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.tv.TvContract;
import android.net.Uri;
import android.os.Build;
import android.support.media.tv.Channel;
import android.support.media.tv.ChannelLogoUtils;
import android.support.media.tv.PreviewProgram;
import android.support.media.tv.TvContractCompat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import sample.lyon.tv.myapplication.MainActivity;
import sample.lyon.tv.myapplication.R;
import sample.lyon.tv.myapplication.Tool.Utils;
import sample.lyon.tv.myapplication.tvUI.Poster;

/**
 * Created by i_hfuhsu on 2018/9/17.
 */

public class InitializeChannel {
    String TAG = InitializeChannel.class.getName();
    private static final String SCHEMA_URI_PREFIX = "tvrecommendation://app/";
    Context context;
    static long channelId;
    public static String RECOMMENDMOVICE="RECOMMEND MOVICE";

    private static final String[] CHANNELS_PROJECTION = {
            TvContractCompat.Channels._ID,
            TvContract.Channels.COLUMN_DISPLAY_NAME,
            TvContractCompat.Channels.COLUMN_BROWSABLE,
            TvContractCompat.Channels.COLUMN_DESCRIPTION
    };

    public InitializeChannel(Context context, String channelNAme){
        this.context=context;
        Cursor cursor =
                context.getContentResolver()
                        .query(
                                TvContractCompat.Channels.CONTENT_URI,
                                CHANNELS_PROJECTION,
                                null,
                                null,
                                null);
        if (cursor != null && cursor.moveToFirst()) {
            deleteAllProgram();
            do {
                Channel channel = Channel.fromCursor(cursor);
                if (channelNAme.equals(channel.getDisplayName())) {
                    Channel.Builder channelbuild= createChannel(channelNAme);
                    long channelId =channel.getId();
                    updataChannel(channelbuild,channelId);
                    this.channelId=channelId;
                }
            } while (cursor.moveToNext());
        }else{
            Channel.Builder channelbuild= createChannel(channelNAme);
            Uri channelUri = insertChannel(channelbuild);
            long channelId = ContentUris.parseId(channelUri);

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            ChannelLogoUtils.storeChannelLogo(context, channelId, bitmap);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                TvContractCompat.requestChannelBrowsable(context, channelId);
            }
            this.channelId=channelId;
        }
    }

    public void addNewChannel(String channelNAme){
        Cursor cursor =
                context.getContentResolver()
                        .query(
                                TvContractCompat.Channels.CONTENT_URI,
                                CHANNELS_PROJECTION,
                                null,
                                null,
                                null);
        Log.d(TAG, "addNewChannel cursor size :" + cursor.getColumnCount() + " from TV Provider:"+cursor.getColumnNames());
        if (cursor != null && cursor.moveToFirst()) {
            boolean channelExit=true;
            do {
                Channel channel = Channel.fromCursor(cursor);
                Log.d(TAG,"addNewChannel channel:"+channel.getDisplayName());
                if (channelNAme.equals(channel.getDisplayName())) {
                    Log.d(
                            TAG,
                            "addNewChannel getId:"
                                    + channel.getId() +" channelNAme:"+channelNAme+ ", channel.getDisplayName()"+channel.getDisplayName()
                                    + ", from TV Provider.");

                    Channel.Builder channelbuild= createChannel(channelNAme);
                    long channelId =channel.getId();
                    updataChannel(channelbuild,channelId);
                    channelExit=false;
                }
            } while (cursor.moveToNext());
            if(channelExit){
                Channel.Builder channelbuild= createChannel(channelNAme);
                Log.d(TAG,"addNewChannel channel:"+channelNAme);
                Uri channelUri = insertChannel(channelbuild);
                long channelId = ContentUris.parseId(channelUri);

                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
                ChannelLogoUtils.storeChannelLogo(context, channelId, bitmap);
                this.channelId=channelId;
            }
        }
    }

    public long getChannelId(String channelNAme){
        Cursor cursor =
                context.getContentResolver()
                        .query(
                                TvContractCompat.Channels.CONTENT_URI,
                                CHANNELS_PROJECTION,
                                null,
                                null,
                                null);
        Log.d(TAG, "getChannelId cursor size :" + cursor.getColumnCount() + " from TV Provider:"+cursor.getColumnNames());
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Channel channel = Channel.fromCursor(cursor);
                Log.d(TAG,"getChannelId channel:"+channel.getDisplayName());
                if (channel.getDisplayName().equals(channelNAme)) {
                    long channelId =channel.getId();
                    Log.d(TAG,"getChannelId channelNAme:"+channelNAme+",channel.getDisplayName():"+channel.getDisplayName()+",channelId:"+channelId);
                    return channelId;
                }
            } while (cursor.moveToNext());
        }
        return channelId;
    }


    private Channel.Builder createChannel(String channelNAme){
        Intent intent = new Intent(context, MainActivity.class);
        //製造頻道
        Channel.Builder builder = new Channel.Builder();
        builder.setType(TvContractCompat.Channels.TYPE_PREVIEW)
                .setDisplayName(channelNAme)
                .setAppLinkIntent(intent);


        Log.d(TAG, "Oreo Notif Creating channel: " + channelNAme);

        return builder;
    }

    private Uri insertChannel(Channel.Builder channelbuild){
        Uri channelUri = context.getContentResolver().insert(
                TvContractCompat.Channels.CONTENT_URI, channelbuild.build().toContentValues());
        return channelUri;
    }

    private void updataChannel(Channel.Builder channelbuild, long channelId){
        context.getContentResolver().update(
                TvContractCompat.buildChannelUri(channelId), channelbuild.build().toContentValues(),null, null);
    }



    public void deleteChannel( long channelId){
        int size=context.getContentResolver().delete(TvContractCompat.buildChannelUri(channelId) , null , null);
        Log.d(TAG,"Oreo Notif channelId :"+channelId+" ,cursor size deleteChannel:"+size);
    }

    //製造節目
    @SuppressLint("RestrictedApi")
    public void createProgram(long channelId,Poster poster){
        //節目預告片
        Uri previewPlayUri = Uri.parse("https://storage.googleapis.com/android-tv/Sample videos/Google+/Google+_ Sharing but like real life.mp4");
        PreviewProgram program = new PreviewProgram.Builder()
                .setChannelId(channelId)
                .setTitle(poster.chineseName)
                .setDescription(poster.englishName)
                .setType(TvContractCompat.PreviewPrograms.TYPE_MOVIE)
                .setPosterArtAspectRatio(TvContractCompat.PreviewPrograms.ASPECT_RATIO_MOVIE_POSTER)//設置海報藝術長寬比
                .setPosterArtUri(Uri.parse(poster.imageUrl))//節目畫面
                .setPreviewVideoUri(previewPlayUri)//previewVideo
                .setIntent(buildIPendingIntent(poster))
                .build();
        Log.d(TAG,"Oreo Notif createProgram program:"+program.toContentValues());
        //插入節目到頻道中
        Uri programUri = context.getContentResolver().insert(TvContractCompat.PreviewPrograms.CONTENT_URI,
                program.toContentValues());
        long programId = ContentUris.parseId(programUri);
        //刷新
        context.getContentResolver().update(TvContractCompat.buildPreviewProgramUri(programId),
                program.toContentValues(), null, null);
    }



    private Intent buildIPendingIntent(Poster poster) {
        if(poster==null) {
            Log.e(TAG,"Oreo Notif buildIPendingIntent poster==null");
            return null;

        }
        Log.e(TAG,"Oreo Notif buildIPendingIntent poster="+poster.toString());
        Intent intent = new Intent(context, MainActivity.class);


        intent.putExtra(RECOMMENDMOVICE, (Poster)poster);//Parcelable 經由外部app傳輸 可能會 Null
//        Log.d(TAG,"Oreo Notif buildIPendingIntent  hasExtra:"+intent.hasExtra(MovieDetailsActivity.MOVIE));
        Log.d(TAG,"Oreo Notif buildIPendingIntent  MOVIE:"+intent.getParcelableExtra(RECOMMENDMOVICE));
        return intent;
    }


    public void deleteProgram(Context context, long programId) {
        Log.d(TAG, "Oreo Notif deleteProgram delete program: " + programId);
        int rowsDeleted = context.getContentResolver().delete(
                TvContractCompat.buildPreviewProgramUri(programId), null, null);
        if (rowsDeleted < 1) {
            Log.e(TAG, "Oreo Notif Delete program failed");
        }
    }

    @SuppressLint("RestrictedApi")
    public void deleteAllProgram(){
        try {
            Cursor cursorProgram =
                    context.getContentResolver()
                            .query(
                                    TvContractCompat.PreviewPrograms.CONTENT_URI,
                                    null,
                                    null,
                                    null,
                                    null);
            Log.d(TAG, "Oreo Notif deleteAllProgram cursorProgram size :" + cursorProgram.getCount() + " from TV Program:" + cursorProgram.toString());
            cursorProgram.moveToFirst();
            if (cursorProgram != null && cursorProgram.moveToFirst()) {
                do {
                    PreviewProgram program = PreviewProgram.fromCursor(cursorProgram);
                    try {
                        Log.d(
                                TAG,
                                "Oreo Notif deleteAllProgram cursorProgram program already exists. Returning program "
                                        + program.getId() + " Title:" + program.getTitle());
                        deleteProgram(context, program.getId());
                    }catch (Exception e){
                        Log.e(TAG,"Oreo Notif deleteAllProgram deleteProgram Exception:"+e);
                    }
                } while (cursorProgram.moveToNext());
            }
        }catch (NullPointerException e){
            Log.e(TAG,"Oreo Notif deleteAllProgram NullPointerException:"+e);
        }
    }
}


