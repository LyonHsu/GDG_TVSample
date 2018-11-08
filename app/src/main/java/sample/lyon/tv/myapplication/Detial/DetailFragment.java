package sample.lyon.tv.myapplication.Detial;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.app.DetailsFragmentBackgroundController;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewSharedElementHelper;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import sample.lyon.tv.myapplication.Detial.ui.Card;
import sample.lyon.tv.myapplication.Detial.ui.DetailedCard;
import sample.lyon.tv.myapplication.Player.PlayActivity;
import sample.lyon.tv.myapplication.Player.PlayActivity2;
import sample.lyon.tv.myapplication.R;
import sample.lyon.tv.myapplication.Tool.Utils;
import sample.lyon.tv.myapplication.tvUI.GridItemPresenter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapDrawableResource;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.time.Instant;


/**
 * Created by i_hfuhsu on 2018/10/16.
 */

public class DetailFragment extends DetailsFragment {
    private static final String TAG = DetailsFragment.class.getName();

    public static final String TRANSITION_NAME = "t_for_transition";
    public static final String EXTRA_CARD = "card";

    private static final int ACTION_BUY = 1;
    private static final int WATCH_TRAILER = 2;
    private static final int WATCH_FULL = 3;

    DetailsOverviewRow detailsOverview;
    private Action mActionBuy;
    private Action mActionWatchFull;
    private Action mActionWatchPreview;
    ArrayObjectAdapter  mRowsAdapter;
    DetailedCard data;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate DetailsFragment");
        super.onCreate(savedInstanceState);
        getContentData();
        updateBackground();
        setDetail();
        updateImage();
        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootView = super.onCreateView(inflater, container, savedInstanceState);
        return mRootView;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getContentData(){
        String json = Utils
                .inputStreamToString(getResources().openRawResource(R.raw.detail_example));
        data = new Gson().fromJson(json, DetailedCard.class);
        Log.d(TAG,"data:"+data.toString());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startEntranceTransition();
            }
        }, 500);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setDetail(){
        FullWidthDetailsOverviewRowPresenter rowPresenter = new FullWidthDetailsOverviewRowPresenter(
                new DetailsDescriptionPresenter(getActivity())) {
            @Override
            protected RowPresenter.ViewHolder createRowViewHolder(ViewGroup parent) {
                // Customize Actionbar and Content by using custom colors.
                RowPresenter.ViewHolder viewHolder = super.createRowViewHolder(parent);

                View actionsView = viewHolder.view.
                        findViewById(R.id.details_overview_actions_background);
                actionsView.setBackgroundColor(getActivity().getResources().
                        getColor(R.color.detail_view_actionbar_background));

                View detailsView = viewHolder.view.findViewById(R.id.details_frame);
                detailsView.setBackgroundColor(
                        getResources().getColor(R.color.detail_view_background));
                return viewHolder;
            }
        };

//        FullWidthDetailsOverviewSharedElementHelper mHelper = new FullWidthDetailsOverviewSharedElementHelper();
//        mHelper.setSharedElementEnterTransition(getActivity(), TRANSITION_NAME);
//        rowPresenter.setListener(mHelper);
//        rowPresenter.setParticipatingEntranceTransition(false);
//        prepareEntranceTransition();

        rowPresenter.setOnActionClickedListener(new OnActionClickedListener() {
            @Override
            public void onActionClicked(Action action) {
                Log.d(TAG,"onActionClicked action:"+action.getId());
               switch ((int) action.getId()){
                   case ACTION_BUY:
                       Toast.makeText(getActivity(),"ACTION_BUY",Toast.LENGTH_SHORT).show();
                       break;
                   case WATCH_FULL:
                       Toast.makeText(getActivity(),"WATCH_FULL",Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(getActivity(),PlayActivity.class);
                       getActivity().startActivity(intent);
                       break;
                   case WATCH_TRAILER:
                       Toast.makeText(getActivity(),"WATCH_TRAILER",Toast.LENGTH_SHORT).show();
                        intent = new Intent(getActivity(),PlayActivity2.class);
                       getActivity().startActivity(intent);
                       break;
                }
            }
        });

        ListRowPresenter shadowDisabledRowPresenter = new ListRowPresenter();
        shadowDisabledRowPresenter.setShadowEnabled(false);

        // Setup PresenterSelector to distinguish between the different rows.
        ClassPresenterSelector rowPresenterSelector = new ClassPresenterSelector();
        rowPresenterSelector.addClassPresenter(DetailsOverviewRow.class, rowPresenter);
        rowPresenterSelector.addClassPresenter(CardListRow.class, shadowDisabledRowPresenter);
        rowPresenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());
        mRowsAdapter = new ArrayObjectAdapter(rowPresenterSelector);

        // Setup action and detail row.
        detailsOverview = new DetailsOverviewRow(data);


        ArrayObjectAdapter actionAdapter = new ArrayObjectAdapter();

        mActionBuy = new Action(ACTION_BUY, getResources().getString(R.string.action_buy) + data.getPrice());
        mActionWatchFull = new Action(WATCH_FULL, getResources().getString(R.string.watch_the_full_video));
        mActionWatchPreview = new Action(WATCH_TRAILER, getResources().getString(R.string.watch_the_trailer));

        actionAdapter.add(mActionBuy);
        actionAdapter.add(mActionWatchFull);
        actionAdapter.add(mActionWatchPreview);
        detailsOverview.setActionsAdapter(actionAdapter);
        mRowsAdapter.add(detailsOverview);

        // Setup related row.
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(
                new GridItemPresenter(getActivity()));
        for (Card characterCard : data.getCharacters()) listRowAdapter.add(characterCard.getTitle());
        HeaderItem header = new HeaderItem(0, getString(R.string.header_related));
        mRowsAdapter.add(new CardListRow(header, listRowAdapter, null));

        // Setup recommended row.
        listRowAdapter = new ArrayObjectAdapter(new GridItemPresenter(getActivity()));
        for (Card card : data.getRecommended()) listRowAdapter.add(card.getTitle());
        header = new HeaderItem(1, getString(R.string.header_recommended));
        mRowsAdapter.add(new ListRow(header, listRowAdapter));

        setAdapter(mRowsAdapter);
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            Log.d(TAG, "Lyon  Clicked " + row + " item=" + item);
            Log.d(TAG, "Lyon   Clicked rowViewHolder=" + rowViewHolder.getRow());
            if(item instanceof  CardListRow) {
                Toast.makeText(getActivity(), "Lyon  Clicked " + " item=" + item, Toast.LENGTH_SHORT).show();
            }

        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            Log.d(TAG, "Lyon  select " + row + " item=" + item);
            Log.d(TAG, "Lyon   select rowViewHolder=" + rowViewHolder.getRow());

        }
    }

    private void updateImage(){
        int width=(int)265;
        int height=(int)390;
        String url=data.getLocalImageResource();
        Drawable mDefaultBackground;
        DisplayMetrics mMetrics = new DisplayMetrics();
        mDefaultBackground = getResources().getDrawable(R.drawable.app_icon_your_company);
        Glide.with(getActivity())
                .load(url)
                .centerCrop()
                .error(mDefaultBackground)
                .into(new SimpleTarget<GlideDrawable>(width, height) {
                    ////这个用于监听图片是否加载完成
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        detailsOverview.setImageDrawable(resource);
                    }
                });
    }

    protected void updateBackground() {
        Log.e(TAG,"updateBackground init");
        String url="https://cdn.vox-cdn.com/thumbor/eVoUeqwkKQ7MFjDCgrPrqJP5ztc=/0x0:2040x1360/1200x800/filters:focal(860x1034:1186x1360)/cdn.vox-cdn.com/uploads/chorus_image/image/59377089/wjoel_180413_1777_android_001.1523625143.jpg";
        updateBackground(url);
    }

    private Handler handler = new Handler() {
        // 处理子线程给我们发送的消息。
        @Override
        public void handleMessage(android.os.Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            getActivity().getWindow().setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        };
    };

    protected void updateBackground(final String uri) {
        Log.e(TAG,"updateBackground init");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Log.e(TAG,"updateBackground init runOnUiThread");
                Drawable mDefaultBackground;
                mDefaultBackground = getResources().getDrawable(R.drawable.app_icon_your_company);
                try {
                    Glide.with(getActivity())
                            .load(uri)
                            .centerCrop()
                            .error(mDefaultBackground)
                            .centerCrop()
                            .into(
                                    new SimpleTarget<GlideDrawable>() {
                                        @Override
                                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                            Log.e(TAG,"updateBackground runOnUiThread onResourceReady");
                                            getActivity().getWindow().setBackgroundDrawable(resource);
                                        }
                                    }
                            );

                }catch (Exception e){
                    Log.e(TAG,"updateBackground Exception  :"+e);
                }
            }});

    }
}
