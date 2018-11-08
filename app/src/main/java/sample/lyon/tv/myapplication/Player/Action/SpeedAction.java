package sample.lyon.tv.myapplication.Player.Action;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.PlaybackControlsRow;

import sample.lyon.tv.myapplication.Player.Drawl.Drawl;
import sample.lyon.tv.myapplication.R;

public class SpeedAction extends PlaybackControlsRow.MultiAction {

    int speed;
    Context context;
    /**
     * Constructor
     *
     * @param id The id of the Action.
     */
    public SpeedAction(Context context, int id) {
        super(id);
        this.context=context;
        Drawable[] drawables = new Drawable[2];
        String s = context.getString(R.string.speed);
        drawables[0] = new Drawl(context,s).getBitmap();
        setDrawables(drawables);
        String[] labels = new String[1];
        labels[0] = s;
        setLabel1(labels[0]);

    }

    public void setDrawable(Drawable drawable){
        Drawable[] drawables = new Drawable[2];
        drawables[0] = drawable;
        setDrawables(drawables);
    }

}
