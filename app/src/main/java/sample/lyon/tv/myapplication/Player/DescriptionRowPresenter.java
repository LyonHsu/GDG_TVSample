package sample.lyon.tv.myapplication.Player;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sample.lyon.tv.myapplication.R;
import sample.lyon.tv.myapplication.tvUI.MovieCardView;

public class DescriptionRowPresenter  extends Presenter {
    String TAG = DescriptionRowPresenter.class.getName();
    static Context context;

    public DescriptionRowPresenter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        context = parent.getContext();
        DescriptionCardView descriptionCardView = new DescriptionCardView(parent.getContext());
        descriptionCardView.setFocusable(true);
        descriptionCardView.setFocusableInTouchMode(true);
        return new ViewHolder(descriptionCardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        View rootView = viewHolder.view;
        DescriptionCardView descriptionCardView = (DescriptionCardView) viewHolder.view;
        descriptionCardView.setImage(context.getResources().getDrawable(R.drawable.app_icon_your_company));
        TextView t = (TextView)rootView.findViewById(R.id.textView);
        t.setText("Lyon title");
        Log.d(TAG,"Lyon title");
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        // ...
    }
}
