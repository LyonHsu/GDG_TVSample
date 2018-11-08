package sample.lyon.tv.myapplication.Player.Drawl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;

public class Drawl extends View {
    private int mov_x;//聲明起點座標
    private int mov_y;
    private Paint paint;//聲明畫筆
    private Canvas canvas;//畫布
    protected Bitmap bitmap;//點陣圖
    private int blcolor;
    int wight,hight;
    View v;
    Context context;
    public Drawl(Context coNtext, String s) {
        super(coNtext);
        this.context=coNtext;
        int textMaxSize=150;
        int textMinSize=100;
        v=getRootView();
        paint=new Paint(Paint.DITHER_FLAG);//創建一個畫筆
        bitmap = Bitmap.createBitmap(320, 320, Bitmap.Config.ARGB_8888); //設置點陣圖的寬高
        canvas=new Canvas();
        canvas.setBitmap(bitmap);
        setBackgroundColor(Color.TRANSPARENT);
        paint.setStyle(Paint.Style.FILL);//設置非填充
        if(s.length()>2)
            paint.setTextSize(textMinSize);
        else
            paint.setTextSize(textMaxSize);
        paint.setColor(Color.WHITE);//設置為白筆
        paint.setAntiAlias(true);//鋸齒不顯示
        paint.setTextAlign(Paint.Align.CENTER);
        Rect r = new Rect();
        paint.getTextBounds(s, 0, s.length(), r);
        //center
        int yPos = (canvas.getWidth() / 2);
        yPos += (Math.abs(r.height()))/2;
        int xPos = (canvas.getWidth() / 2);
        canvas.drawText(s,xPos,yPos,paint);

    }


    public Drawable getBitmap(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        String s= Base64.encodeToString(appicon, Base64.DEFAULT);
        Drawable drawable =new BitmapDrawable(bitmap);
        return drawable;
    }

    //畫點陣圖
    @Override
    protected void onDraw(Canvas canvas) {
// super.onDraw(canvas);
        canvas.drawBitmap(bitmap,0,0,null);
    }

}
