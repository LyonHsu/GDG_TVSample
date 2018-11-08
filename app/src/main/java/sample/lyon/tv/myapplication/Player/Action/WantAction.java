package sample.lyon.tv.myapplication.Player.Action;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.PlaybackControlsRow;

import sample.lyon.tv.myapplication.Player.Drawl.Drawl;
import sample.lyon.tv.myapplication.R;

public class WantAction extends PlaybackControlsRow.MultiAction {

    int isWant;

    /**
     * Constructor
     *
     * @param id The id of the Action.
     */
    public WantAction(Context context, int id) {
        super(id);

        Drawable[] drawables = new Drawable[2];
        String s = context.getString(R.string.collect);
        drawables[0] = new Drawl(context,s).getBitmap();
        setDrawables(drawables);
//        String[] labels = new String[1];
//        labels[0] = s;
//            setLabel1(labels[0]);
    }

}




