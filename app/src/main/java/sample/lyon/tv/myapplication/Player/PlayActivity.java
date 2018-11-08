package sample.lyon.tv.myapplication.Player;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v17.leanback.app.VideoFragment;
import android.support.v17.leanback.app.VideoFragmentGlueHost;
import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ControlButtonPresenterSelector;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.PlaybackControlsRow;
import android.support.v17.leanback.widget.PlaybackControlsRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import sample.lyon.tv.myapplication.Detial.DetailsActivity;
import sample.lyon.tv.myapplication.Player.Action.BookAction;
import sample.lyon.tv.myapplication.Player.Action.ProfileAction;
import sample.lyon.tv.myapplication.Player.Action.SpeedAction;
import sample.lyon.tv.myapplication.Player.Action.SubtitleAction;
import sample.lyon.tv.myapplication.Player.Action.WantAction;
import sample.lyon.tv.myapplication.R;
import sample.lyon.tv.myapplication.Search.SearchActivity;
import sample.lyon.tv.myapplication.tvUI.GridItemPresenter;

public class PlayActivity extends FragmentActivity implements  SurfaceHolder.Callback, OnActionClickedListener {
    private String TAG = PlayActivity.class.getName();

    private RelativeLayout surfaceLayout;
    private FragmentManager fragmentManager = getFragmentManager();
    static PlayFragment fragment;
    VideoFragment videoFragment;
    SurfaceHolder  mSurfaceHolder;
    private VideoFragmentGlueHost mGlue;
    private PlaybackControlsRow mControlsRow;
    private PlaybackControlsRow.PlayPauseAction mPlayPauseAction;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        surfaceLayout = (RelativeLayout) findViewById(R.id.surfaceLayout);
        surfaceLayout.setKeepScreenOn(true);//避免進入待機模式

        fragmentManager = getFragmentManager();
        videoFragment = (VideoFragment) fragmentManager.findFragmentById(R.id.playback_controls_fragment);
        VideoFragmentGlueHost glueHost = new VideoFragmentGlueHost(videoFragment);
        setupUI();

    }


    @Override
    public boolean onSearchRequested() {
        startActivity(new Intent(this, SearchActivity.class));
        return true;
    }

    @Override
    public void onActionClicked(Action action) {
        Log.d(TAG,"Action:"+action.toString());
        Toast.makeText(this,"Action:"+action.toString() ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Will not be called on TV devices
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mSurfaceHolder = null;
    }


    private void setupUI(){
        mGlue = new VideoFragmentGlueHost(videoFragment);
        mGlue.setSurfaceHolderCallback(this);
        mGlue.setOnActionClickedListener(this);

        mControlsRow = new PlaybackControlsRow();
        int time = 200*1000*60;
        int p = 30;
        int playtime=p*time/100;
        mControlsRow.setCurrentTime(playtime);
        mControlsRow.setBufferedProgress(p);
        mControlsRow.setDuration(time);

        ArrayObjectAdapter adapter = new ArrayObjectAdapter(new ControlButtonPresenterSelector());
        mPlayPauseAction = new PlaybackControlsRow.PlayPauseAction(this);
        mPlayPauseAction.nextIndex(); // set to play
        adapter.add(new PlaybackControlsRow.RewindAction(this));
        adapter.add(mPlayPauseAction);
        adapter.add(new PlaybackControlsRow.FastForwardAction(this));


//        addOtherRow(adapter);

        mControlsRow.setPrimaryActionsAdapter(adapter);

        ArrayObjectAdapter mSecondaryActionsAdapter = new ArrayObjectAdapter(new ControlButtonPresenterSelector());
        SpeedAction speedAction = new SpeedAction(this,R.string.speed);
        SubtitleAction subtitleAction = new SubtitleAction(this, R.string.subtitle);
        ProfileAction profileAction = new ProfileAction(this, R.string.profile);
        WantAction wantAction = new WantAction(this, R.string.want);
        //收藏
        mSecondaryActionsAdapter.add(wantAction);
        //倍數
        mSecondaryActionsAdapter.add(speedAction);
        //字幕
        mSecondaryActionsAdapter.add(subtitleAction);
        //畫質
        mSecondaryActionsAdapter.add(profileAction);
        mControlsRow.setSecondaryActionsAdapter(mSecondaryActionsAdapter);

        PlaybackControlsRowPresenter presenter = new PlaybackControlsRowPresenter(new AbstractDetailsDescriptionPresenter() {
            @Override
            protected void onBindDescription(ViewHolder vh, Object item) {
                vh.getTitle().setText("Lyon title");
                vh.getSubtitle().setText("lyon Subtitle");
                vh.getBody().setText("Lyon body");
            }
        }){
            @Override
            protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
                super.onBindRowViewHolder(holder, item);
                FrameLayout frameLayout = (FrameLayout)holder.view.findViewById(android.support.v17.leanback.R.id.controls_container);
                frameLayout.setBackgroundColor(Color.argb(255,0,155,255));
                ProgressBar progressBar = (ProgressBar)holder.view.findViewById(android.support.v17.leanback.R.id.playback_progress);
                progressBar.setBackgroundColor(Color.argb(255,255,0,0));
                Drawable drawable = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getDrawable(R.drawable.progressbar_bg);
                }
                progressBar.setProgressDrawable(drawable);
            }
        };

        mGlue.setPlaybackRow(mControlsRow);
        mGlue.setPlaybackRowPresenter(presenter);

    }

    private void addOtherRow(ArrayObjectAdapter mRowsAdapter){
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new GridItemPresenter(this));
        for(int i = 0;i<6;i++) {
            listRowAdapter.add("Play recommend:"+i);
        }
        HeaderItem header = new HeaderItem(getString(R.string.header_recommended));
        ListRow listRow=new ListRow(header, listRowAdapter);
        mRowsAdapter.add(listRow);
        videoFragment.setOnItemViewClickedListener(new ItemViewClickedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            Log.d(TAG, "ItemViewClickedListener:" + item.toString());
            if(! (item instanceof Action)) {
                Toast.makeText(PlayActivity.this, "Play recommend:" + item.toString(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(PlayActivity.this, "Action:" + item.toString(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
