package sample.lyon.tv.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRowView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import sample.lyon.tv.myapplication.Permissions.PermissionsActivity;
import sample.lyon.tv.myapplication.Permissions.PermissionsChecker;
import sample.lyon.tv.myapplication.Recommendation.BootupActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {

    private  String  TAG = MainActivity.class.getName();
    private static final int REQUEST_CODE = 2; // 请求码
    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView ver = (TextView)findViewById(R.id.ver);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            ver.setText("Ver:"+packageInfo.versionName+" "+packageInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            ver.setVisibility(View.GONE);
            throw new RuntimeException("Could not get package name: " + e);
        }catch (Exception e){
            ver.setVisibility(View.GONE);
            e.printStackTrace();
        }


        PermissionsChecker mPermissionsChecker = new PermissionsChecker(this);
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
        }

        BootupActivity.scheduleRecommendationUpdate(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                // SYSTEM_ALERT_WINDOW permission not granted...
                Toast.makeText(this, "Permission Denieddd by user.Please Check it in Settings", Toast.LENGTH_SHORT).show();
                finish();
            }
        }else if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            Toast.makeText(getBaseContext(),"I am finish!!",Toast.LENGTH_SHORT).show();
            finish();
        }else if(requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_GRANTED){
          //............
        }
    }



}
