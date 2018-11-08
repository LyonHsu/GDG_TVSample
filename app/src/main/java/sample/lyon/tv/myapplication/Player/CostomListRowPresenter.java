package sample.lyon.tv.myapplication.Player;

import android.content.Context;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.Log;

public class CostomListRowPresenter  extends ListRowPresenter {
    String TAG = CostomListRowPresenter.class.getName();
    private int mInitialSelectedPosition;
    //20170914 detial episode set defaul
    Context context;

    public CostomListRowPresenter(Context context, int position) {
        this.mInitialSelectedPosition = position;
        //20170914 detial episode set defaul
        this.context = context;
    }

    public void setmInitialSelectedPosition(int position) {
        this.mInitialSelectedPosition = position;
        Log.d(TAG, "CostomListRowPresenter setmInitialSelectedPosition:" + mInitialSelectedPosition);
    }

    public int getmInitialSelectedPosition() {
        Log.d(TAG, "CostomListRowPresenter getmInitialSelectedPosition:" + mInitialSelectedPosition);
        return mInitialSelectedPosition;
    }

    @Override
    protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
        super.onBindRowViewHolder(holder, item);
        //20170914 detial episode set defaul
        if (item instanceof ListRow) {
            String name = ((ListRow) item).getHeaderItem().getName();
            Log.d(TAG, "name:" + name);
            ViewHolder vh = (ViewHolder) holder;
            vh.getGridView().setSelectedPosition(mInitialSelectedPosition);

        }

    }
}
