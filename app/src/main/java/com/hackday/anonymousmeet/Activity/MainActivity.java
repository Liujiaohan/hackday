package com.hackday.anonymousmeet.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.hackday.anonymousmeet.Database.MyDatabaseHelper;
import com.hackday.anonymousmeet.Entity.Animal;
import com.hackday.anonymousmeet.OnMoveUp;
import com.hackday.anonymousmeet.R;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements OnMoveUp {
    /**
     * 显示VR360度全景图片的控件
     */

    private VrPanoramaView vr_pan_view;
    private Animal majorCharacter;
    private Button btnAddSaying;
    private Button btnHome;
    private ImageView recomment_fish;


    /**
     * 打印的TAG
     */
    private final String TAG = "VrPanoramaView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        load360Image();
        initUI();
    }

    private void initUI() {
        majorCharacter= (Animal) findViewById(R.id.major_character);
        recomment_fish= (ImageView) findViewById(R.id.recommend_fish);
        recomment_fish.setImageResource(R.drawable.animation2);
        ((AnimationDrawable)recomment_fish.getDrawable()).start();
        recomment_fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RecommendActivity.class);
                startActivity(intent);
            }
        });

        majorCharacter.setOnMoveUp(this);
        btnHome= (Button) findViewById(R.id.home);
        btnAddSaying= (Button) findViewById(R.id.add_saying_btn);
        btnAddSaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplication(),Main4Activity.class);
                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplication(),HomeActivity.class);
                startActivity(intent);
            }
        });


         deleteDatabase("LocalDB.db");
    }

    /**
     * 加载360度全景图片
     */
    private void load360Image() {
        vr_pan_view = (VrPanoramaView) findViewById(R.id.vr_pan_view);
        /**获取assets文件夹下的图片**/
        InputStream open = null;
        try {
            open = getAssets().open("ocean3.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(open);
        /**设置加载VR图片的相关设置**/
        VrPanoramaView.Options options = new VrPanoramaView.Options();
        options.inputType = VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
        /**设置加载VR图片监听**/
        vr_pan_view.setEventListener(new VrPanoramaEventListener() {
            /**
             * 显示模式改变回调
             * 1.默认
             * 2.全屏模式
             * 3.VR观看模式，即横屏分屏模式
             * @param newDisplayMode 模式
             */
            @Override
            public void onDisplayModeChanged(int newDisplayMode) {
                super.onDisplayModeChanged(newDisplayMode);
                Log.d(TAG, "onDisplayModeChanged()->newDisplayMode=" + newDisplayMode);
            }

            /**
             * 加载VR图片失败回调
             * @param errorMessage
             */
            @Override
            public void onLoadError(String errorMessage) {
                super.onLoadError(errorMessage);
                Log.d(TAG, "onLoadError()->errorMessage=" + errorMessage);
            }

            /**
             * 加载VR图片成功回调
             */
            @Override
            public void onLoadSuccess() {
                super.onLoadSuccess();
                Log.d(TAG, "onLoadSuccess->图片加载成功");
            }

            /**
             * 点击VR图片回调
             */
            @Override
            public void onClick() {
                super.onClick();
                Log.d(TAG, "onClick()");
            }
        });
        /**加载VR图片**/
        vr_pan_view.loadImageFromBitmap(bitmap, options);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**关闭加载VR图片，释放内存**/
        vr_pan_view.shutdown();
    }

    @Override
    public void OnMoveEnd() {
        Intent intent=new Intent(this,Main3Activity.class);
        startActivity(intent);
    }
}
