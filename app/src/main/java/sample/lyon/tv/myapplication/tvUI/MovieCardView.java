package sample.lyon.tv.myapplication.tvUI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sample.lyon.tv.myapplication.R;

/**
 * Created by i_hfuhsu on 2018/8/21.
 */

public class MovieCardView  extends BaseCardView {
    TextView textView;


    protected ImageView Image;


    public MovieCardView(Context context, int styleResId) {
        super(new ContextThemeWrapper(context, styleResId), null, 0);
        buildLoadingCardView(styleResId);
    }

    public MovieCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(getStyledContext(context, attrs, defStyleAttr), attrs, defStyleAttr);
        buildLoadingCardView(getImageCardViewStyle(context, attrs, defStyleAttr));
    }

    private void buildLoadingCardView(int styleResId) {
        setFocusable(true);
        setFocusableInTouchMode(true);
        setCardType(CARD_TYPE_INFO_OVER);
        setBackgroundResource(R.color.fastlane_background);
        setInfoVisibility(CARD_REGION_VISIBLE_SELECTED);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.grid_item_layout, this);

        textView = (TextView)view.findViewById(R.id.textView);
        Image = (ImageView)view.findViewById(R.id.imageView);


        TypedArray cardAttrs =
                getContext().obtainStyledAttributes(
                        styleResId, android.support.v17.leanback.R.styleable.lbImageCardView);
        cardAttrs.recycle();
    }



    public void setNameText(String string) {
        textView.setText(string);
    }

    public void setNameColoe(int color)
    {
        textView.setBackgroundColor(color);
    }


    public void setImage(int resource) {
        Image.setImageDrawable(ContextCompat.getDrawable(getContext(), resource));
    }

    public void setImage(Drawable drawabl){
        Image.setImageDrawable(drawabl);
    }

    private static Context getStyledContext(Context context, AttributeSet attrs, int defStyleAttr) {
        int style = getImageCardViewStyle(context, attrs, defStyleAttr);
        return new ContextThemeWrapper(context, style);
    }

    private static int getImageCardViewStyle(Context context, AttributeSet attrs, int defStyleAttr) {
        // Read style attribute defined in XML layout.
        int style = null == attrs ? 0 : attrs.getStyleAttribute();
        if (0 == style) {
            // Not found? Read global ImageCardView style from Theme attribute.
            TypedArray styledAttrs =
                    context.obtainStyledAttributes(
                            android.support.v17.leanback.R.styleable.LeanbackTheme);
            style = styledAttrs.getResourceId(
                    android.support.v17.leanback.R.styleable.LeanbackTheme_imageCardViewStyle, 0);
            styledAttrs.recycle();
        }
        return style;
    }

    public MovieCardView(Context context) {
        this(context, null);
    }

    public MovieCardView(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v17.leanback.R.attr.imageCardViewStyle);
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

}
