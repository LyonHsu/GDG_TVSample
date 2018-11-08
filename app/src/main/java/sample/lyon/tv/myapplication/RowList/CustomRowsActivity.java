package sample.lyon.tv.myapplication.RowList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import sample.lyon.tv.myapplication.GridView.GridViewActivity;
import sample.lyon.tv.myapplication.R;
import sample.lyon.tv.myapplication.Tool.Utils;

public class CustomRowsActivity extends Activity {
    private static final String TAG = GridViewActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rows_layout);
        final Context mContext=this;
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Utils.zoomOut(mContext,v);
                }else{
                    Utils.zoomIn(mContext,v);
                }
            }
        });
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Utils.zoomOut(mContext,v);
                }else{
                    Utils.zoomIn(mContext,v);
                }
            }
        });
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Utils.zoomOut(mContext,v);
                }else{
                    Utils.zoomIn(mContext,v);
                }
            }
        });
        Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Utils.zoomOut(mContext,v);
                }else{
                    Utils.zoomIn(mContext,v);
                }
            }
        });

    }
}
