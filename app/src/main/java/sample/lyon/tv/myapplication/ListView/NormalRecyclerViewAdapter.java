package sample.lyon.tv.myapplication.ListView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import sample.lyon.tv.myapplication.Detial.DetailsActivity;
import sample.lyon.tv.myapplication.R;

/**
 * Created by i_hfuhsu on 2017/1/26.
 */
public abstract class NormalRecyclerViewAdapter extends RecyclerView.Adapter<NormalRecyclerViewAdapter.NormalTextViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private static  Context mContext;
    private String[] mTitles;
    private static final int GRID_ITEM_WIDTH = 300;
    private static final int GRID_ITEM_HEIGHT = 200;

    private static Animation scaleSmallAnimation;
    private static Animation scaleBigAnimation;


    public NormalRecyclerViewAdapter(Context context ,String[] imgText) {
        mTitles = imgText;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
        holder.textView.setText(mTitles[position]);

    }


    @Override
    public int getItemCount() {
        return mTitles == null ? 0 : mTitles.length;
    }

    public  class NormalTextViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        NormalTextViewHolder(View view) {
            super(view);
            textView=(TextView)view.findViewById(R.id.textView);
            textView.setFocusable(false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("NormalTextViewHolder", "onClick--> position = " + getPosition());
                    Toast.makeText(mContext, ((String) textView.getText()) , Toast.LENGTH_SHORT)
                            .show();
                    zoomOut(v);

                    Intent intent = new Intent(mContext, DetailsActivity.class);
                    mContext.startActivity(intent);
                    close();

                }
            });
            view.setFocusable(true);
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
            view.setBackgroundColor(mContext.getResources().getColor(R.color.default_background));
            view.setBackground(mContext.getResources().getDrawable(R.drawable.buttoncolor));

            view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus){
                        zoomIn(v);
                    }else {
                        zoomOut(v);
                    }
                }
            });
        }
    }



    public static void zoomIn(View v) {
        if (scaleSmallAnimation == null) {
            scaleSmallAnimation = AnimationUtils.loadAnimation(mContext, R.anim.anim_scale_small);
        }
        v.startAnimation(scaleSmallAnimation);
    }

    public static void zoomOut(View v) {
        if (scaleBigAnimation == null) {
            scaleBigAnimation = AnimationUtils.loadAnimation(mContext, R.anim.anim_scale_big);
        }
        v.startAnimation(scaleBigAnimation);
    }

    public abstract void close();
}
