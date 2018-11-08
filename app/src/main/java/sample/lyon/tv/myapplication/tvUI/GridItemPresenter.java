package sample.lyon.tv.myapplication.tvUI;

import android.content.Context;
import android.graphics.Color;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.RowHeaderPresenter;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import sample.lyon.tv.myapplication.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by i_hfuhsu on 2017/1/26.
 */
public class GridItemPresenter extends Presenter {
    String TAG = GridItemPresenter.class.getName();
    static Context context;
    private static final int GRID_ITEM_WIDTH = 200;
    private static final int GRID_ITEM_HEIGHT = 200;

    private static final int GRID_ITEM_WIDTH_Change = 300;
    private static final int GRID_ITEM_HEIGHT_Change = 200;
    ArrayList<String> arrayList;

    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;

    public GridItemPresenter(Context context,ArrayList<String> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }
    public GridItemPresenter(Context context){
        this.context=context;
        this.arrayList=null;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        context = parent.getContext();
        sDefaultBackgroundColor = parent.getResources().getColor(R.color.fastlane_background);
        sSelectedBackgroundColor = parent.getResources().getColor(R.color.search_opaque);
        MovieCardView cardView = new MovieCardView(context){
            @Override
            public void setSelected(boolean selected) {
                super.setSelected(selected);
                updateCardBackgroundColor(this, selected);
//                if(selected)
//                    setLayoutParams(new BaseCardView.LayoutParams(GRID_ITEM_WIDTH_Change, GRID_ITEM_HEIGHT_Change));
//                else{
//                    setLayoutParams(new BaseCardView.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
//                }
            }

        };
//        cardView.setLayoutParams(new BaseCardView.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
        cardView.setBackgroundColor(context.getResources().getColor(R.color.default_background));
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.setBackgroundColor(sDefaultBackgroundColor);


        return new ViewHolder(cardView);
    }





    @Override
    public void onBindViewHolder(final Presenter.ViewHolder viewHolder, Object item) {
        View rootView = viewHolder.view;
        TextView t = (TextView)rootView.findViewById(R.id.textView);
        ImageView Image = (ImageView)rootView.findViewById(R.id.imageView);

         t.setText(item+"");
        Image.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.ic_launcher));
        //t.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
        t.setFocusable(false);
        t.setFocusableInTouchMode(true);
        t.setBackgroundColor(context.getResources().getColor(R.color.default_background));
        t.setTextColor(Color.WHITE);
        t.setGravity(Gravity.CENTER);
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }

    private static void updateCardBackgroundColor(MovieCardView view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        // Both background colors should be set because the view's background is temporarily visible
        // during animations.
        view.setBackgroundColor(color);
        view.setNameColoe(color);
    }
}
