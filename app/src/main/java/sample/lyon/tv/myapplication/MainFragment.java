package sample.lyon.tv.myapplication;

import android.content.Intent;
import android.os.Bundle;

import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import sample.lyon.tv.myapplication.Detial.DetailsActivity;
import sample.lyon.tv.myapplication.GridView.GridViewActivity;
import sample.lyon.tv.myapplication.RowList.CustomRowsActivity;
import sample.lyon.tv.myapplication.Search.SearchActivity;
import sample.lyon.tv.myapplication.tvUI.GridItemPresenter;
import sample.lyon.tv.myapplication.tvUI.IconHeaderItem;
import sample.lyon.tv.myapplication.tvUI.IconHeaderItemPresenter;

/**
 * Created by corochann on 2015/06/28.
 */
public class MainFragment extends BrowseFragment {
    private static final String TAG = MainFragment.class.getSimpleName();

    private ArrayObjectAdapter mRowsAdapter;
    ArrayObjectAdapter gridRowAdapter;
    String titleName[]={"GridItemPresenter","Lyon Test","Lyon Test2"};

    GridItemPresenter mGridPresenter;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        setupUIElements();

        loadRows();
    }

    private void setupUIElements() {
        setTitle("Hello Android TV!"); // Badge, when set, takes precedent

        //set Icon
        //setBadgeDrawable(getResources().getDrawable(R.drawable.app_icon_your_company));
        // over title
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        setBrandColor(getResources().getColor(R.color.fastlane_background));
        // set search icon color
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));

        try {
            setHeaderPresenterSelector(new PresenterSelector() {
                @Override
                public Presenter getPresenter(Object o) {
                    return new IconHeaderItemPresenter();
                }
            });
        }catch (Exception e){
            Log.e(TAG,"setHeaderPresenterSelector Exception:"+e);
        }

        setOnSearchClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You went Search!" , Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });


        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                Log.d(TAG, "Lyon select Clicked " + row + " item=" + item);
                Log.d(TAG, "Lyon select  Clicked rowViewHolder=" + rowViewHolder.getRow());
                Log.d(TAG, "Lyon select Clicked  itemViewHolder=" + itemViewHolder.view.getId());
                try {
                    Toast.makeText(getActivity(), item.toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }

                if(item.toString().contains(titleName[2])){
                    Intent intent = new Intent(getActivity(), CustomRowsActivity.class);
                    startActivity(intent);
                }else if(item.toString().contains(titleName[1])){
                    Intent intent = new Intent(getActivity(), GridViewActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    getActivity().startActivity(intent);
                }
                getActivity().finish();
            }
        });


        setOnItemViewSelectedListener(new OnItemViewSelectedListener() {
            @Override
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                try {
                    Log.d(TAG, "Lyon select Selected " + row + " item=" + item);
                    Log.d(TAG, "Lyon select Selected  rowViewHolder=" + rowViewHolder.getRow());
                    Log.d(TAG, "Lyon select Selected  itemViewHolder=" + itemViewHolder.view.getId());
                    HeaderItem gridItemPresenterHeader = new HeaderItem(0, "GridItemPresenter");
                    View rootView = rowViewHolder.view;
                    ImageView imageView = (ImageView)rootView.findViewById(R.id.imageView);
                }catch(NullPointerException e){
                    Log.e(TAG,"");
                }
            }
        });


    }

    private void loadRows() {
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        /* GridItemPresenter */
        IconHeaderItem gridItemPresenterHeader = new IconHeaderItem(0, titleName[0]);
        IconHeaderItem gridItemPresenterHeader2 = new IconHeaderItem(1, titleName[1], R.mipmap.ic_launcher);
        IconHeaderItem gridItemPresenterHeader3 = new IconHeaderItem(2, titleName[2]);



        //gridList item adapter
        mGridPresenter = new GridItemPresenter(getActivity());
        gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        for(int i=1;i<=70;i++)
            gridRowAdapter.add("Item " +i);

        ListRow listRow= new ListRow(gridItemPresenterHeader, gridRowAdapter);
        mRowsAdapter.add(listRow);

        gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        for(int i=1;i<=70;i++)
            gridRowAdapter.add("Lyon Test " +i);

        ListRow listRow2= new ListRow(gridItemPresenterHeader2, gridRowAdapter);
        mRowsAdapter.add(listRow2);
//

        gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        for(int i=1;i<=70;i++)
            gridRowAdapter.add("Lyon Test2 " +i);

        mRowsAdapter.add(new ListRow(gridItemPresenterHeader3, gridRowAdapter));

        /* set */
        setAdapter(mRowsAdapter);
    }




}
