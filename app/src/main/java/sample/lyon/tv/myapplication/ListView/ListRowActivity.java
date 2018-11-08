package sample.lyon.tv.myapplication.ListView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRowView;
import android.support.v7.widget.RecyclerView;

import sample.lyon.tv.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by i_hfuhsu on 2017/3/1.
 */

public class ListRowActivity extends Activity {
    private static final int GRID_ITEM_WIDTH = 600;
    private static final int GRID_ITEM_HEIGHT = 400;
    private String[] imgText = {
            "cat", "flower", "hippo", "monkey", "mushroom", "panda", "rabbit", "raccoon"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listrow_main);
        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < imgText.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("text", imgText[i]);
            items.add(item);
        }
        HeaderItem gridItemPresenterHeader2 = new HeaderItem(1, "Lyon Test");
        ListRowView listRowView = (ListRowView)findViewById(R.id.listRowView);
        NormalRecyclerViewAdapter adapter=new NormalRecyclerViewAdapter(this,imgText){

            @Override
            public void close() {
                finish();
            }
        };

        listRowView.getGridView().setAdapter((RecyclerView.Adapter)adapter);
    }
}
