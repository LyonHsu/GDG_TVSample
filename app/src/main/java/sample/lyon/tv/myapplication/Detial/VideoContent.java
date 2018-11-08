package sample.lyon.tv.myapplication.Detial;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by i_hfuhsu on 2018/10/17.
 */

public class VideoContent {

    public String ImageUrl="";
    public int contentType=1;
    public static final int STATUS_TAG_COMING = 1;
    public static final int STATUS_TAG_FULL = 2;
    public static final int WATCH_FULL = 3;
    public static final int WATCH_TRAILER = 4;


    public int statusTag= STATUS_TAG_COMING;//
}
