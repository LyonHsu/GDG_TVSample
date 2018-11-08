package sample.lyon.tv.myapplication.Player;

import android.graphics.Color;
import android.support.v17.leanback.widget.PlaybackControlsRow;
import android.support.v17.leanback.widget.PlaybackControlsRowPresenter;
import android.support.v17.leanback.widget.PlaybackTransportRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ControlsRowPresenter extends PlaybackControlsRowPresenter {

    boolean GONEOrVISIBLE=false;
    static ProgressBar progressBar;
    int progressBarTop;
    int progressBarButton;
    int progressBarLeft=300;
    int progressBarRight;

    public ControlsRowPresenter(Presenter descriptionPresenter){
        super(descriptionPresenter);
    }

    public ControlsRowPresenter(){
        super();
    }




    @Override
    protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
        super.onBindRowViewHolder(holder, item);
        Log.d("ControlsRowPresenter","item:"+item);
        ViewHolder vh = (ViewHolder) holder;
        PlaybackControlsRow row = (PlaybackControlsRow) vh.getRow();

//            int wh=Utils.dpToPx(100);
//            if(FullOrTrailer==MovieDetailsActivity.WATCH_LIVE_CHANNEL)  //直播
//            {
//                vh.view.setPadding(wh, Utils.dpToPx(230), wh, 0);
//                setSecondaryActionsHidden(true);
//
//            }
//            else if(FullOrTrailer==MovieDetailsActivity.WATCH_FULL)
//                vh.view.setPadding(wh, Utils.dpToPx(250), wh, 0);
//            else
//                vh.view.setPadding(wh, Utils.dpToPx(190), wh, 0);

        vh.mDescriptionViewHolder.view.setBackgroundColor(Color.TRANSPARENT);
        //
        if(GONEOrVISIBLE){
            TextView mCurrentTime = (TextView) vh.view.findViewById(android.support.v17.leanback.R.id.current_time);
            if(mCurrentTime!=null)
                mCurrentTime.setTextColor(Color.TRANSPARENT);
        }
        progressBarTop = vh.view.getPaddingTop();
        progressBarButton = vh.view.getPaddingBottom();
        progressBarLeft = vh.view.getPaddingLeft();
        progressBarRight = vh.view.getPaddingRight();
        progressBar = (ProgressBar)vh.view.findViewById(android.support.v17.leanback.R.id.playback_progress);
    }
}
