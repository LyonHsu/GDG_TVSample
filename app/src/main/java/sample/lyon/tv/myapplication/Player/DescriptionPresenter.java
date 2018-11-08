package sample.lyon.tv.myapplication.Player;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;
import android.util.Log;
import android.view.View;

public class DescriptionPresenter extends AbstractDetailsDescriptionPresenter {


    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object item) {

        viewHolder.getTitle().setText("Lyon");
        Log.d("DescriptionRowPresenter","setText");
        viewHolder.getSubtitle().setVisibility(View.GONE);
        viewHolder.getBody().setVisibility(View.VISIBLE);
    }
}
