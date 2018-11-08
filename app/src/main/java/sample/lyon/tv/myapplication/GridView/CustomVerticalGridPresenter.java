package sample.lyon.tv.myapplication.GridView;

import android.support.v17.leanback.widget.VerticalGridPresenter;

/**
 * Created by i_hfuhsu on 2018/10/16.
 */

public class CustomVerticalGridPresenter extends VerticalGridPresenter {
    private static final String TAG = "CustomVerticalGridPresenter";

    public CustomVerticalGridPresenter(int zoomFactorMedium, boolean b) {
        super(zoomFactorMedium,b);
    }

    public CustomVerticalGridPresenter() {
        super();
    }

    @Override
    protected void initializeGridViewHolder(ViewHolder vh) {
        super.initializeGridViewHolder(vh);
        vh.getGridView().setPadding(30,220,30,40);

    }
}