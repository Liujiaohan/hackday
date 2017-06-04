package com.hackday.anonymousmeet.Activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hackday.anonymousmeet.Database.MyDatabaseHelper;
import com.hackday.anonymousmeet.Entity.ItemSaying;
import com.hackday.anonymousmeet.Entity.Saying;
import com.hackday.anonymousmeet.R;
import com.hackday.anonymousmeet.adapter.ItemSayingAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {

    private ViewPager pager = null;
    private PagerTabStrip tabStrip = null;
    private ArrayList<View> viewContainter = new ArrayList<View>();
    private ArrayList<String> titleContainer = new ArrayList<String>();

    private List<ItemSaying> sayings=new ArrayList<ItemSaying>();
    private List<ItemSaying> traces=new ArrayList<ItemSaying>();

    public String TAG = "tag";

    private ListView lv_saying;
    private ListView lv_traces;

    private ItemSayingAdapter adapter1;
    private ItemSayingAdapter adapter2;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pager = (ViewPager) this.findViewById(R.id.viewpager);
        tabStrip = (PagerTabStrip) this.findViewById(R.id.tabstrip);
        //取消tab下面的长横线
        tabStrip.setDrawFullUnderline(false);
        //设置tab的背景色
        tabStrip.setBackgroundColor(this.getResources().getColor(R.color.colorTab));
        //设置当前tab页签的下划线颜色
        tabStrip.setTabIndicatorColor(this.getResources().getColor(R.color.colorTab));
        tabStrip.setTextSpacing(200);

        View view1 = LayoutInflater.from(this).inflate(R.layout.tab1, null);
        lv_saying= (ListView) view1.findViewById(R.id.mySayingList);
        View view2 = LayoutInflater.from(this).inflate(R.layout.tab2, null);
        lv_traces= (ListView) view2.findViewById(R.id.MyTraceList);
        //viewpager开始添加view
        viewContainter.add(view1);
        viewContainter.add(view2);
        //页签项
        titleContainer.add("动态");
        titleContainer.add("寻迹");

        initMySaying();
        initMyTraces();

        pager.setAdapter(new PagerAdapter() {

            //viewpager中的组件数量
            @Override
            public int getCount() {
                return viewContainter.size();
            }
            //滑动切换的时候销毁当前的组件
            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                ((ViewPager) container).removeView(viewContainter.get(position));
            }
            //每次滑动的时候生成的组件
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(viewContainter.get(position));
                return viewContainter.get(position);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titleContainer.get(position);
            }
        });

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {
                Log.d(TAG, "--------changed:" + arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
//                Log.d(TAG, "-------scrolled arg0:" + arg0);
//                Log.d(TAG, "-------scrolled arg1:" + arg1);
//                Log.d(TAG, "-------scrolled arg2:" + arg2);
            }

            @Override
            public void onPageSelected(int arg0) {
                Log.d(TAG, "------selected:" + arg0);

                if (arg0==0){
                    adapter1.notifyDataSetChanged();
                }
                else if (arg0==1){
                    adapter2.notifyDataSetChanged();
                }
            }
        });

    }

    private void initMySaying() {
        adapter1=new ItemSayingAdapter(HomeActivity.this,
                R.layout.item,sayings);
        lv_saying.setAdapter(adapter1);

        adapter2=new ItemSayingAdapter(HomeActivity.this,
                R.layout.item2,traces);
        lv_traces.setAdapter(adapter2);

        MyDatabaseHelper dbHelper=new MyDatabaseHelper(this,"LocalDB.db",null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query("Saying",null,null,null,null,null,null);

        if (cursor.moveToFirst()){
            do{
                String content=cursor.getString(cursor.getColumnIndex("content"));
                String likeAmount=cursor.getString(cursor.getColumnIndex("likeamount"));

                ItemSaying itemSaying=new ItemSaying(content,likeAmount);

                sayings.add(itemSaying);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void initMyTraces() {

        sendRequestWithOkHttp();
    }


    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder().
                            url("http://debug.yinyu.floatflower.com.tw/api/v1/messages").build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    parseJSONWithGSON(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData){
        Gson gson=new Gson();
        final List<Saying> sayingList=gson.fromJson(jsonData,new TypeToken<List<Saying>>(){}.getType());
        for (Saying saying:sayingList){
            String content=saying.getContent();
            String likeAmount=saying.getLikeAmount();
            traces.add(new ItemSaying(content,likeAmount));
        }
        Log.i(TAG, "parseJSONWithGSON: ");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter2.notifyDataSetChanged();
            }
        });
    };

}
