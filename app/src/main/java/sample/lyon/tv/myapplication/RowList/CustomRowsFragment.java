package sample.lyon.tv.myapplication.RowList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import sample.lyon.tv.myapplication.R;
import sample.lyon.tv.myapplication.tvUI.GridItemPresenter;

public class CustomRowsFragment extends RowsFragment {
    String TAG = CustomRowsFragment.class.getName();
    private final int NUM_ROWS = 5;
    private final int NUM_COLS = 15;
    View v;
    private ArrayObjectAdapter rowsAdapter;
    private static final int HEADERS_FRAGMENT_SCALE_SIZE = 300;

    private String[] imgText = {
            "cat", "flower", "hippo", "monkey", "mushroom", "panda", "rabbit", "raccoon"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "RowsFragment oncreateView :"+v);
        int marginOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HEADERS_FRAGMENT_SCALE_SIZE, getResources().getDisplayMetrics());
        try {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            params.rightMargin -= marginOffset;
            v.setLayoutParams(params);
        }catch (NullPointerException e){
            Log.e(TAG,""+e);
        }
        v.setBackgroundColor(getRandomColor());
        Log.d(TAG, "oncreateView");
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadRows();

        setOnItemViewSelectedListener(new OnItemViewSelectedListener() {
            @Override
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                Log.d(TAG, "20170804 CustomRowsFragment item = " + item);
                Log.d(TAG, "20170804 CustomRowsFragment row = " + row);
            }
        });

        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                Log.d(TAG, "20170808 CustomRowsFragment item = " + item.toString());
                Log.d(TAG, "20170808 CustomRowsFragment row = " + row);
            }
        });
    }

    String titleName[]={"Lyon Test","Lyon Test2", "Lyon Test3"};
    private void loadRows(){
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        for(int j=0;j<titleName.length;j++) {
            HeaderItem header = new HeaderItem(0, titleName[j]);
            GridItemPresenter presenter = new GridItemPresenter(getActivity());
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(presenter);
            for (int i = 1; i <= 70; i++)
                listRowAdapter.add("Lyon listRow " + i);

            rowsAdapter.add(j, new ListRow(header, listRowAdapter));
        }
        setAdapter(rowsAdapter);
    }

    private int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }




}
