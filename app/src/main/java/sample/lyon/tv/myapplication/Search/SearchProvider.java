package sample.lyon.tv.myapplication.Search;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class SearchProvider extends ContentProvider {
    final static private String TAG = SearchProvider.class.getSimpleName();

    Context context;
    private int hasDataNum = 0;
    private static final int CHANNEL_LIST_ID = 3;
    String traditionalChinese;
    String simpleCinese;
    boolean isTorS=true;


    @Override
    public boolean onCreate() {
        context = getContext();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        isTorS = true;
        String query = uri.getLastPathSegment();
        Log.d(TAG,"20180515 query:"+query);
        synchronized (query) {
            Log.d(TAG, "20180515 Uri= " + uri + "\n query= " + query
                    + "\n UTF-8 = " + toUtf8(query)
                    + "\n projection =" + projection
                    + "\n selection =" + selection
                    + "\n selectionArgs =" + selectionArgs
                    + "\n sortOrder =" + sortOrder
            );
        }


        MatrixCursor mc = new MatrixCursor(new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_RESULT_CARD_IMAGE, SearchManager.SUGGEST_COLUMN_VIDEO_WIDTH, SearchManager.SUGGEST_COLUMN_VIDEO_HEIGHT, SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA});

        for(int i=0;i<20;i++){
            mc.addRow(new Object[]{"Lyon Test "+i, "https://cdn0-techbang.pixfs.net/system/images/137296/original/dd52251f294d33e754858287bbcd3651.png", "", "", null});
        }


        Log.e(TAG, "return search cursor");
        cursor = mc;
        return cursor;
    }

    public static String toUtf8(String str) {
        byte [] utf8Decode = new byte[0];
        try {
            utf8Decode = str.getBytes("utf-8");
            String string = new String(utf8Decode, Charset.forName("UTF-8"));
            Log.d(TAG,"string ="+string +"\n");
            System.out.println("utf8:");
            String utf8="";
            for (byte b: utf8Decode) {
                System.out.print(Integer.toHexString(b & 0xFF));
                System.out.print(" ");
                utf8+=" /x"+ Integer.toHexString(b & 0xFF)+" ";
            }
            return utf8;
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG,"UnsupportedEncodingException:"+e);
        }
        return "error";
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String query = uri.getLastPathSegment();
        Log.d(TAG,"insert:"+uri.toString()+" ContentValues:"+values+" query:"+query);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String query = uri.getLastPathSegment();
        Log.d(TAG,"insert:"+uri.toString()+" selection:"+selection+" query:"+query);
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
