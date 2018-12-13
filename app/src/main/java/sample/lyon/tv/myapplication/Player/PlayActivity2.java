package sample.lyon.tv.myapplication.Player;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.VideoFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import sample.lyon.tv.myapplication.R;
import sample.lyon.tv.myapplication.Search.SearchActivity;

public class PlayActivity2 extends FragmentActivity {
    private String TAG = PlayActivity.class.getName();

    private RelativeLayout surfaceLayout;
    private FragmentManager fragmentManager = getFragmentManager();
    static PlayFragment fragment;
    VideoFragment videoFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player2);

        surfaceLayout = (RelativeLayout) findViewById(R.id.surfaceLayout);
        surfaceLayout.setKeepScreenOn(true);//避免進入待機模式

        fragmentManager = getFragmentManager();
        fragment = (PlayFragment) fragmentManager.findFragmentById(R.id.playback_controls_fragment);
//        fragmentManager.beginTransaction().hide(fragment);
//        fragmentManager.beginTransaction().show(fragment);

    }

    @Override
    public boolean onSearchRequested() {
        startActivity(new Intent(this, SearchActivity.class));
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e(TAG,"PlayActivity2 onKeyDown fragment isHidden ? :"+fragment.isVisibleed());
        if(!fragment.isVisibleed()) {
            fragment.isShow(true);
        }else{

        }
        mHandler.removeCallbacks(runnable);
        mHandler.postDelayed(runnable, 10*1000);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!fragment.isVisibleed())
            finish();
    }

    private Handler mHandler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            fragment.isShow(false);
            Log.d(TAG,"");
        }
    };
}

