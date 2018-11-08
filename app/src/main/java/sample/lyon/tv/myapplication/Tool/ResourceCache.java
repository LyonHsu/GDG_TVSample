package sample.lyon.tv.myapplication.Tool;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by i_hfuhsu on 2018/10/17.
 */

public class ResourceCache {
    private final SparseArray<View> mCachedViews = new SparseArray<View>();

    public <ViewType extends View> ViewType getViewById(View view, int resId) {
        View child = mCachedViews.get(resId, null);
        if (child == null) {
            child = view.findViewById(resId);
            mCachedViews.put(resId, child);
        }
        return (ViewType) child;
    }
}
