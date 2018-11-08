package sample.lyon.tv.myapplication.Tool;

import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import sample.lyon.tv.myapplication.R;

import java.io.IOException;
import java.io.InputStream;

import static android.content.Context.UI_MODE_SERVICE;

/**
 * Created by i_hfuhsu on 2018/10/17.
 */

public class Utils {
    public final static String TAG = Utils.class.getSimpleName();

    public static int dpToPx(int dp, Context ctx) {
        float density = ctx.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }

    public static void zoomIn(Context mContext,View v) {
        Animation scaleSmallAnimation = null;
        Animation scaleBigAnimation;
        if (scaleSmallAnimation == null) {
            scaleSmallAnimation = AnimationUtils.loadAnimation(mContext, R.anim.anim_scale_small);
        }
        v.startAnimation(scaleSmallAnimation);
    }

    public static void zoomOut(Context mContext,View v) {
        Animation scaleBigAnimation = null;
        if (scaleBigAnimation == null) {
            scaleBigAnimation = AnimationUtils.loadAnimation(mContext, R.anim.anim_scale_big);
        }
        v.startAnimation(scaleBigAnimation);
    }


    boolean checkTVDevice(Context mContext){
        boolean isTVDevice = true;
        UiModeManager uiModeManager = (UiModeManager) mContext.getSystemService(UI_MODE_SERVICE);
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
