package sample.lyon.tv.myapplication.Player.Action;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.PlaybackControlsRow;

import sample.lyon.tv.myapplication.Player.Drawl.Drawl;
import sample.lyon.tv.myapplication.R;

public class BookAction extends PlaybackControlsRow.MultiAction {
    boolean isBook;

    public BookAction(Context context, int id) {
        super(id);
        Drawable[] drawables = new Drawable[2];
        String s = context.getString(R.string.book);
        drawables[0] = new Drawl(context,s).getBitmap();
        setDrawables(drawables);
        String[] labels = new String[1];
        labels[0] = s;
            setLabel1(labels[0]);
    }

}
