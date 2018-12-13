package sample.lyon.tv.myapplication.Player;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.PlaybackSupportFragment;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.app.VideoFragment;
import android.support.v17.leanback.app.VideoFragmentGlueHost;
import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.ControlButtonPresenterSelector;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.PlaybackControlsRow;
import android.support.v17.leanback.widget.PlaybackTransportRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.SeekBar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sample.lyon.tv.myapplication.Player.Action.BookAction;
import sample.lyon.tv.myapplication.Player.Action.ProfileAction;
import sample.lyon.tv.myapplication.Player.Action.SpeedAction;
import sample.lyon.tv.myapplication.Player.Action.SubtitleAction;
import sample.lyon.tv.myapplication.Player.Action.WantAction;
import sample.lyon.tv.myapplication.R;
import sample.lyon.tv.myapplication.Tool.Utils;
import sample.lyon.tv.myapplication.tvUI.GridItemPresenter;

public class PlayFragment extends VideoFragment  {
    static String TAG = PlayFragment.class.getName();
    DescriptionPresenter descriptionPresenter;
    private boolean SHOW_DETAIL = true;
//    ControlsRowPresenter playbackControlsRowPresenter;
    private static int PRIMARY_CONTROLS = 3;
    protected PlaybackControlsRow mPlaybackControlsRow;
    private ArrayObjectAdapter mRowsAdapter;
    private ArrayObjectAdapter mPrimaryActionsAdapter;
    protected ArrayObjectAdapter mSecondaryActionsAdapter;

    protected PlaybackControlsRow.PlayPauseAction mPlayPauseAction;
    public PlaybackControlsRow.FastForwardAction mFastForwardAction;
    public PlaybackControlsRow.RewindAction mRewindAction;
    public PlaybackControlsRow.SkipNextAction mSkipNextAction;
    public PlaybackControlsRow.SkipPreviousAction mSkipPreviousAction;
    private SpeedAction speedAction;
    protected SubtitleAction subtitleAction;
    private ProfileAction profileAction;
    private WantAction wantAction;
    private BookAction bookAction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);


    }
    RowsFragment mRowsFragment;
    private void initializePlayer() {
//        getView().setPadding(0,Utils.dpToPx(300, getActivity()),0,0);

        mRowsAdapter = initializeRelatedVideosRow();
        setAdapter(mRowsAdapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initializePlayer();
//        setCustomPadding();
    }

    @SuppressLint("ResourceAsColor")
    private ArrayObjectAdapter initializeRelatedVideosRow() {
       ClassPresenterSelector presenterSelector = new ClassPresenterSelector();
        AbstractDetailsDescriptionPresenter descriptionRowPresenter = new AbstractDetailsDescriptionPresenter(){
            @Override
            protected void onBindDescription(ViewHolder viewHolder, Object item) {
                viewHolder.getTitle().setText("Lyon");
                Log.d("DescriptionRowPresenter","setText");
                viewHolder.getSubtitle().setVisibility(View.GONE);
                viewHolder.getBody().setVisibility(View.VISIBLE);
                 }

            @Override
            public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item, List<Object> payloads) {
                super.onBindViewHolder(viewHolder, item, payloads);

            }
        };
        if (false) {
            ControlsRowPresenter playbackControlsRowPresenter = new ControlsRowPresenter(
                    descriptionRowPresenter){
                @Override
                protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
                    super.onBindRowViewHolder(holder, item);
                    FrameLayout frameLayout = (FrameLayout)holder.view.findViewById(android.support.v17.leanback.R.id.controls_container);
                    frameLayout.setBackgroundColor(Color.argb(255,0,155,255));
                    ProgressBar progressBar = (ProgressBar)holder.view.findViewById(android.support.v17.leanback.R.id.playback_progress);
                    progressBar.setBackgroundColor(Color.argb(255,255,0,0));
                    Drawable drawable = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        drawable = getActivity().getDrawable(R.drawable.progressbar_bg);
                    }
                    progressBar.setProgressDrawable(drawable);
                    holder.view.setBackgroundColor(getActivity().getResources().getColor(android.R.color.transparent));
                    holder.view.setPadding(Utils.dpToPx(0,getActivity()), Utils.dpToPx(300, getActivity()), Utils.dpToPx(0, getActivity()), 0);

                }
            };
            playbackControlsRowPresenter.setOnActionClickedListener(OnActionClickedListener);
            boolean HIDE_MORE_ACTIONS = false;
            playbackControlsRowPresenter.setSecondaryActionsHidden(HIDE_MORE_ACTIONS);
            playbackControlsRowPresenter.setBackgroundColor(R.color.control_background);
            //控制選單
            presenterSelector.addClassPresenter(PlaybackControlsRow.class, playbackControlsRowPresenter);
        } else {
            PlaybackTransportRowPresenter presenter = new PlaybackTransportRowPresenter(){
                @SuppressLint("RestrictedApi")
                @Override
                protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
                    super.onBindRowViewHolder(holder, item);
                    SeekBar mProgressBar = (SeekBar) holder.view.findViewById(android.support.v17.leanback.R.id.playback_progress);
                    LinearLayout controls_card = (LinearLayout) holder.view.findViewById(android.support.v17.leanback.R.id.controls_card);

                    TextView title = new TextView(getActivity());
                    title.setText("Lyon title");
                    controls_card.addView(title);

                    mProgressBar.setProgressColor(Color.argb(255,255,0,200));
                }
            };
            presenter.setDescriptionPresenter(descriptionRowPresenter);
            //控制選單
            presenterSelector.addClassPresenter(PlaybackControlsRow.class, presenter);
        }

        ArrayObjectAdapter mRowsAdapter = new ArrayObjectAdapter(presenterSelector);
        addPlaybackControlsRow(mRowsAdapter);
        //預設推薦列表選擇頻道
        int defP=2;
        CostomListRowPresenter listRowPresenter = new CostomListRowPresenter(getActivity(),defP);
        presenterSelector.addClassPresenter(ListRow.class, listRowPresenter);
        addOtherRow(mRowsAdapter);
        return mRowsAdapter;
    }

    private void addPlaybackControlsRow(ArrayObjectAdapter mRowsAdapter) {
        mPlaybackControlsRow = new PlaybackControlsRow();
        mRowsAdapter.add(mPlaybackControlsRow);
        int time = 200*1000*60;
        int p = 30;
        int playtime=p*time/100;
        mPlaybackControlsRow.setCurrentTime(playtime);
        mPlaybackControlsRow.setBufferedProgress(p);
        mPlaybackControlsRow.setDuration(time);
        mRowsAdapter.notifyArrayItemRangeChanged(0, 1);

        ControlButtonPresenterSelector presenterSelector = new ControlButtonPresenterSelector();
        mPrimaryActionsAdapter = new ArrayObjectAdapter(presenterSelector);
        mSecondaryActionsAdapter = new ArrayObjectAdapter(presenterSelector);

        // First row
        mPlaybackControlsRow.setPrimaryActionsAdapter(mPrimaryActionsAdapter);
        // Second row
        mPlaybackControlsRow.setSecondaryActionsAdapter(mSecondaryActionsAdapter);
        //播放 暫停
        if(mPlayPauseAction==null)
            mPlayPauseAction = new PlaybackControlsRow.PlayPauseAction(getActivity());

        //mPlayPauseAction.setIcon(getResources().getDrawable(R.drawable.play));
        try{
            if (PRIMARY_CONTROLS > 1) {
                speedAction = new SpeedAction(getActivity(),R.string.speed);
                subtitleAction = new SubtitleAction(getActivity(), R.string.subtitle);
                profileAction = new ProfileAction(getActivity(), R.string.profile);
                bookAction = new BookAction(getActivity(), R.string.book);
                wantAction = new WantAction(getActivity(), R.string.want);

                //下一部
                mSkipNextAction = new PlaybackControlsRow.SkipNextAction(getActivity());
                //上一部
                mSkipPreviousAction = new PlaybackControlsRow.SkipPreviousAction(getActivity());
                //快轉
                mFastForwardAction = new PlaybackControlsRow.FastForwardAction(getActivity());
                //mFastForwardAction.setIcon(getResources().getDrawable(R.drawable.fast_forward));
                //倒轉
                mRewindAction = new PlaybackControlsRow.RewindAction(getActivity());
                //mRewindAction.setIcon(getResources().getDrawable(R.drawable.rewind));

                //上一部
                if (PRIMARY_CONTROLS > 3) {
                    mPrimaryActionsAdapter.add(mSkipPreviousAction);
                }
                //倒轉
                if (PRIMARY_CONTROLS > 1) {
                    mPrimaryActionsAdapter.add(new PlaybackControlsRow.RewindAction(getActivity()));
                }

            }
            //播放 暫停
            mPrimaryActionsAdapter.add(mPlayPauseAction);

            if (PRIMARY_CONTROLS > 1) {
                //快轉
                if (PRIMARY_CONTROLS > 1) {
                    mPrimaryActionsAdapter.add(new PlaybackControlsRow.FastForwardAction(getActivity()));
                }

                //下一部
                if (PRIMARY_CONTROLS > 3) {
                    mPrimaryActionsAdapter.add(mSkipNextAction);
                }
                //收藏
                mSecondaryActionsAdapter.add(wantAction);
                //倍數
                mSecondaryActionsAdapter.add(speedAction);
                //字幕
                mSecondaryActionsAdapter.add(subtitleAction);
                //畫質
                mSecondaryActionsAdapter.add(profileAction);



            }
        }catch(IllegalStateException e){
            Log.e(TAG,"addPlaybackControlsRow IllegalStateException :"+e);
        }
    }

    private void addOtherRow(ArrayObjectAdapter mRowsAdapter){
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new GridItemPresenter(getActivity()));
        for(int i = 0;i<6;i++) {
            listRowAdapter.add("Play recommend:"+i);
        }
        HeaderItem header = new HeaderItem(getString(R.string.header_recommended));
        ListRow listRow=new ListRow(header, listRowAdapter);
        mRowsAdapter.add(listRow);
        setOnItemViewClickedListener(new ItemViewClickedListener());
         listRowAdapter = new ArrayObjectAdapter(new GridItemPresenter(getActivity()));
        for(int i = 0;i<6;i++) {
            listRowAdapter.add("Play recommend 2:"+i);
        }
         header = new HeaderItem(getString(R.string.header_recommended));
         listRow=new ListRow(header, listRowAdapter);
        mRowsAdapter.add(listRow);

        listRowAdapter = new ArrayObjectAdapter(new GridItemPresenter(getActivity()));
        for(int i = 0;i<6;i++) {
            listRowAdapter.add("Play recommend 3:"+i);
        }
        header = new HeaderItem(getString(R.string.header_recommended));
        listRow=new ListRow(header, listRowAdapter);
        mRowsAdapter.add(listRow);
        setOnItemViewClickedListener(new ItemViewClickedListener());
    }

    OnActionClickedListener OnActionClickedListener = new OnActionClickedListener(){

        @Override
        public void onActionClicked(Action action) {
            Log.d(TAG,"Action:"+action.toString());
            Toast.makeText(getActivity(),"Action:"+action.toString() ,Toast.LENGTH_SHORT).show();
        }
    };

    protected void notifyChanged(Action action) {
        ArrayObjectAdapter adapter = mPrimaryActionsAdapter;
        if (adapter.indexOf(action) >= 0) {
            adapter.notifyArrayItemRangeChanged(adapter.indexOf(action), 1);
            return;
        }
        adapter = mSecondaryActionsAdapter;
        if (adapter.indexOf(action) >= 0) {
            adapter.notifyArrayItemRangeChanged(adapter.indexOf(action), 1);
            return;
        }
    }

    public void setDurationTime(long durationTime){
        if(false){
            mPlaybackControlsRow.setTotalTimeLong(-1);
        }else {
            mPlaybackControlsRow.setTotalTimeLong(durationTime);
        }
        mRowsAdapter.notifyArrayItemRangeChanged(0, 1);

    }

    public void setCurrentTime(long currentTime){
        mPlaybackControlsRow.setCurrentTimeLong(currentTime);

        mPlaybackControlsRow.setBufferedProgressLong((currentTime+1000));
        mRowsAdapter.notifyArrayItemRangeChanged(0, 1);
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            Log.d(TAG, "ItemViewClickedListener:" + item.toString());
            if(! (item instanceof Action)) {
                Toast.makeText(getActivity(), "Play recommend:" + item.toString(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "Action:" + item.toString(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void setCustomPadding() {
        //20171025 Lyon 便當加購方案
//		getView().setPadding(Utils.dpToPx(-24,getActivity()), Utils.dpToPx(128, getActivity()), Utils.dpToPx(48, getActivity()), 0);
        getView().setPadding(Utils.dpToPx(0,getActivity()), Utils.dpToPx(300, getActivity()), Utils.dpToPx(0, getActivity()), 0);
        getView().setBackgroundColor(getActivity().getResources().getColor(android.R.color.transparent));
    }

    public boolean isVisibleed(){
        return isControlsOverlayVisible();
    }

    public void isShow(boolean b){
        hideControlsOverlay(b);
    }
}


