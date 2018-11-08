package sample.lyon.tv.myapplication.Search;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.SearchBar;
import android.support.v17.leanback.widget.SearchEditText;
import android.support.v17.leanback.widget.SpeechRecognitionCallback;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import sample.lyon.tv.myapplication.Permissions.PermissionsActivity;
import sample.lyon.tv.myapplication.R;
import sample.lyon.tv.myapplication.tvUI.GridItemPresenter;

import java.util.ArrayList;

/**
 * Created by i_hfuhsu on 2018/10/16.
 */

public class SearchFragment  extends android.support.v17.leanback.app.SearchFragment implements android.support.v17.leanback.app.SearchFragment.SearchResultProvider{
    private final String TAG = SearchFragment.class.getName();
    private static final int SEARCH_DELAY_MS = 1500;
    private static ProgressBar progressbar;
    private ArrayObjectAdapter mRowsAdapter;
    private Handler mHandler = new Handler();
    SearchBar mSearchBar;
    SearchEditText mSearchTextEditor;
    private String keyword;
    private SearchRunnable mDelayedLoad;
    ArrayList<String> searcgItems;

    private class SearchRunnable implements Runnable {
        public void setSearchQuery(String query) {
            Log.d(TAG,"keyword:"+query);
            keyword = query;
        }
        @Override
        public void run() {
            mRowsAdapter.clear();
            progressbar.setVisibility(View.VISIBLE);

            getSearchTag();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }

    @Override
    public ObjectAdapter getResultsAdapter() {
        return mRowsAdapter;
    }

    @Override
    public boolean onQueryTextChange(String newQuery) {
        Log.d(TAG,"SearchFragment onQueryTextChange:"+newQuery);
        mRowsAdapter.clear();
        if (!TextUtils.isEmpty(newQuery)) {
            mDelayedLoad.setSearchQuery(newQuery);
            mHandler.removeCallbacks(mDelayedLoad);
            mHandler.postDelayed(mDelayedLoad, SEARCH_DELAY_MS);
            if (progressbar == null)
                progressbar = ((SearchActivity) getActivity()).getProgressBar();
            progressbar.setVisibility(View.VISIBLE);
        } else {
            mHandler.removeCallbacks(mDelayedLoad);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG,"TVSearchFragment onQueryTextChange:"+query);
        mRowsAdapter.clear();
        if (!TextUtils.isEmpty(query)) {
            mDelayedLoad.setSearchQuery(query);
            mHandler.removeCallbacks(mDelayedLoad);
            mHandler.postDelayed(mDelayedLoad, SEARCH_DELAY_MS);
            if (progressbar == null)
                progressbar = ((SearchActivity) getActivity()).getProgressBar();
            progressbar.setVisibility(View.VISIBLE);
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        setSearchResultProvider(this);

        //如果沒有支援語音搜尋
        if(!SpeechRecognizer.isRecognitionAvailable(getActivity())){
            Log.e(TAG,"isRecognitionAvailable=="+SpeechRecognizer.isRecognitionAvailable(getActivity()));
            SpeechRecognitionCallback mSpeechRecognitionCallback = new SpeechRecognitionCallback() {
                @Override
                public void recognizeSpeech() {
                    try {
                        mSearchTextEditor.requestFocus();
                        startActivityForResult(getRecognizerIntent(), 0/*REQUEST_SPEECH*/);
                    } catch (ActivityNotFoundException e) {
                        Log.e(TAG,"SearchFragment ActivityNotFoundException SpeechRecognitionCallback :"+e);
                    }
                }
            };
            setSpeechRecognitionCallback(mSpeechRecognitionCallback);
        }
        //如果支援語音搜尋 就詢問是否要開啟麥克風 或是直接開啟麥克風
        else{
            requestPermission();
        }

        setOnItemViewClickedListener(getDefaultItemClickedListener());
        setOnItemViewSelectedListener(selectedListener());
        mDelayedLoad = new SearchRunnable();
        keyword = "";
    }

    private void requestPermission()
    {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO);

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO},
                        PermissionsActivity.PERMISSION_REQUEST_CODE);
                return;
            }
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchBar = (SearchBar) view.findViewById(android.support.v17.leanback.R.id.lb_search_bar);
        mSearchTextEditor =(SearchEditText)mSearchBar.findViewById(R.id.lb_search_text_editor);
    }

    private OnItemViewClickedListener getDefaultItemClickedListener() {
        return new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {

            }
        };
    }

    private OnItemViewSelectedListener selectedListener() {
        return new OnItemViewSelectedListener() {
            @Override
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {

            }
        };
    }

    private void getSearchTag(){
        //get search data .......
        Toast.makeText(getActivity(),"You will search "+keyword +" !!",Toast.LENGTH_SHORT).show();
        ArrayObjectAdapter searchAdapter = new ArrayObjectAdapter(new GridItemPresenter(getActivity()));
        searcgItems = new ArrayList<>();
        for(int i=0;i<20;i++) {
            searcgItems.add("Search Tag:"+keyword+"["+i+"]");
            searchAdapter.add(searcgItems.get(i));
        }
        HeaderItem header = new HeaderItem(0,  "Search Word : " + keyword + "\n");
        mRowsAdapter.add(new ListRow(header, searchAdapter));
        progressbar.setVisibility(View.GONE);
    }

}
