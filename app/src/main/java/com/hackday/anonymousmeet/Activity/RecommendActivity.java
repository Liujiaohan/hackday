package com.hackday.anonymousmeet.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hackday.anonymousmeet.Entity.Saying;
import com.hackday.anonymousmeet.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecommendActivity extends Activity {


    private Saying saying=null;
    private TextView content_text;
    private TextView txLikeAmount;
    private Button btnSend;
    private EditText editText;
    private ListView listView;
    private List<String> list=new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        sendRequestWithOkHttp();
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUI();
    }

    private void initUI() {
        content_text= (TextView) findViewById(R.id.content_r);
        btnSend= (Button) findViewById(R.id.send_r);
        editText= (EditText) findViewById(R.id.edit_text_r);
        listView= (ListView) findViewById(R.id.comments_r);
        txLikeAmount= (TextView) findViewById(R.id.likeamount_r);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment1="   ";
                if (editText.getText()!=null){
                    comment1=editText.getText().toString();
                }

                if (!comment1.equals("   ")) {
                    list.add(comment1);
                    adapter.notifyDataSetChanged();
                    sendPostRequestWithOkHttp3();
                }
            }
        });
    }

    private void sendPostRequestWithOkHttp3() {
        final String comment=editText.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder()
                            .add("content",comment).build();
                    String address="http://temp.yinyu.floatflower.com.tw/api/v1/messages/"
                            +saying.getId()+"/comments";
                    Log.i("TAG", "run: "+address);
                    Request request=new Request.Builder()
                            .url(address)
                            .post(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.i("Main4Activity", "onFailure: ");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.i("Main4Activity", "onResponse: ");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    editText.setText("");
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder().
                            url("http://debug.yinyu.floatflower.com.tw/api/v1/messages/recommends").build();
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

        int size=sayingList.size();
        Random random=new Random();
        int selected=random.nextInt(size);
        saying=sayingList.get(selected);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                content_text.setText(saying.getContent());
                txLikeAmount.setText(saying.getLikeAmount());
                getData();
                adapter=new ArrayAdapter<String>(RecommendActivity.this,android.R.layout.simple_list_item_1,list);
                listView.setAdapter(adapter);
            }
        });
    };

    private List<String> getData(){
        if (saying.getComment1Content()!=null){
            list.add(saying.getComment1Content());
        }
        else{
            list.add(" ");
        }

        if (saying.getComment2Content()!=null){
            list.add(saying.getComment2Content());
        }
        else{
            list.add(" ");
        }

        if (saying.getComment3Content()!=null){
            list.add(saying.getComment3Content());
        }
        else{
            list.add(" ");
        }

        return list;
    }
}
