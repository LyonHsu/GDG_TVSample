package sample.lyon.tv.myapplication.GridView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.app.VerticalGridFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.Log;
import android.view.View;

import sample.lyon.tv.myapplication.Detial.DetailsActivity;
import sample.lyon.tv.myapplication.tvUI.GridItemPresenter;

import java.util.ArrayList;

/**
 * Created by i_hfuhsu on 2018/10/16.
 */

public class GridViewFragment extends VerticalGridFragment {
    private static final String TAG = GridViewFragment.class.getName();
    private static int NUM_COLUMNS = 6;
    private ArrayObjectAdapter moreAdapter;

    ArrayList<String> gridItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);


        gridItems = new ArrayList<>();
        for(int i = 0;i<50;i++) {
            gridItems.add("GridItem:"+i);
        }

        setupFragment();
        setOnItemViewClickedListener(getDefaultItemClickedListener());
        setOnItemViewSelectedListener(getDefaultItemViewSelectedListener());


    }

    private void setupFragment(){

        CustomVerticalGridPresenter gridPresenter = new CustomVerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);



        moreAdapter = new ArrayObjectAdapter(new GridItemPresenter(getActivity()));
        for(int i=0;i<gridItems.size();i++)
            moreAdapter.add(gridItems.get(i));


        setAdapter(moreAdapter);
    }

    private OnItemViewClickedListener getDefaultItemClickedListener() {
        return new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                getActivity().startActivity(intent);
            }
        };
    }
    private OnItemViewSelectedListener getDefaultItemViewSelectedListener() {
        return new OnItemViewSelectedListener() {
            @Override
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                Log.d(TAG,"position"+moreAdapter.indexOf(item));

            }
        };
    }

}
