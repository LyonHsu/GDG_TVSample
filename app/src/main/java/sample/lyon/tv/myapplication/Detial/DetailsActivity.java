package sample.lyon.tv.myapplication.Detial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import sample.lyon.tv.myapplication.MainActivity;
import sample.lyon.tv.myapplication.R;
import sample.lyon.tv.myapplication.Search.SearchActivity;

/**
 * Created by i_hfuhsu on 2018/10/16.
 */

public class DetailsActivity extends FragmentActivity {
    private String TAG = DetailsActivity.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
    }

    @Override
    public boolean onSearchRequested() {
        startActivity(new Intent(this, SearchActivity.class));
        return true;
    }

}
