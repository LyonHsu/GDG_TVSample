package sample.lyon.tv.myapplication.GridView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import sample.lyon.tv.myapplication.R;

/**
 * Created by i_hfuhsu on 2018/10/16.
 */

public class GridViewActivity extends FragmentActivity {
    private static final String TAG = GridViewActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview);

    }
}
