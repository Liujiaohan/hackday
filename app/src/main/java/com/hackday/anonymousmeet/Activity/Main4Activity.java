package com.hackday.anonymousmeet.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hackday.anonymousmeet.Database.MyDatabaseHelper;
import com.hackday.anonymousmeet.R;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Main4Activity extends Activity {

    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private Button btnCancel;
    private Button btnPublish;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        init();
    }

    private void init() {
        dbHelper=new MyDatabaseHelper(this,"LocalDB.db",null,1);
        db=dbHelper.getWritableDatabase();

        btnCancel= (Button) findViewById(R.id.cancel_btn);
        btnPublish= (Button) findViewById(R.id.publish_btn);
        editText= (EditText) findViewById(R.id.et_content);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishSayingWithOkHttp3();
            }
        });
    }

    private void publishSayingWithOkHttp3() {
        final String content=editText.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder()
                            .add("content",content).build();
                    Request request=new Request.Builder()
                            .url("http://temp.yinyu.floatflower.com.tw/api/v1/messages")
                            .post(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.i("Main4Activity", "onFailure: ");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            saveSaying(content);
                            Main4Activity.this.finish();
                            Log.i("Main4Activity", "onResponse: ");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void saveSaying(String content) {
        ContentValues values=new ContentValues();

        Random random=new Random();
        int selected=random.nextInt(10);

        values.put("content",content);
        values.put("likeamount",""+selected);

        db.insert("Saying",null,values);

        values.clear();
    }
}
