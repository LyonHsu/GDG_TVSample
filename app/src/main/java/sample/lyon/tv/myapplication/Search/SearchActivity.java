package sample.lyon.tv.myapplication.Search;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v17.leanback.widget.SearchBar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import sample.lyon.tv.myapplication.R;

/**
 * Created by i_hfuhsu on 2018/10/16.
 */

public class SearchActivity  extends Activity {
    public String TAG = SearchActivity.class.getName();
    View view;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        view=findViewById(R.id.search_fragment);
        progressBar=(ProgressBar)findViewById(R.id.search_progressbar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onResume(){
        super.onResume();
        ConnectivityManager cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            Toast.makeText(this,"",Toast.LENGTH_SHORT).show();

        } else if (!networkInfo.isAvailable()) {
            Context context = this;
            Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSearchRequested() {
        //搜尋時 按遙控器的 語音鍵 可以直接選到 搜尋按鈕
        View search = findViewById(R.id.lb_search_bar_speech_orb);
        search.requestFocus();
        search.setSelected(true);
        search.callOnClick();

        SearchBar mSearchBar = (SearchBar) findViewById(android.support.v17.leanback.R.id.lb_search_bar);
        mSearchBar.setVisibility(View.VISIBLE);
        return true;
    }

    public ProgressBar getProgressBar(){
        return progressBar;
    }
}
