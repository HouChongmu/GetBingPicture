package com.display.view.getbingpicture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.display.view.getbingpicture.util.HttpUtil;
import com.display.view.getbingpicture.util.HttpUtil.HttpCallbackListener;
import com.display.view.getbingpicture.util.XMLParseUtil;
import java.io.IOException;
import java.io.StringReader;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private static String sBingUrl = "http://cn.bing.com/HPImageArchive.aspx?idx=0&n=1";
    private static final String BING = "http://cn.bing.com";
    private AppCompatSpinner mResolutionSpinner;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        Button getPicBtn = findViewById(R.id.get_picture);
        Button getPicOkBtn = findViewById(R.id.get_picture_okhttp);
        mResolutionSpinner = findViewById(R.id.resolution_spinner);
        mImageView = findViewById(R.id.bing_picture);
        getPicBtn.setOnClickListener(this);
        getPicOkBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_picture:
                HttpUtil.sendHttpRequest(sBingUrl, new HttpCallbackListener() {
                    @Override
                    public void finish(String response) {
                        final StringBuilder picUrl = new StringBuilder();
                        picUrl.append(BING);
                        picUrl.append(XMLParseUtil.parseWithPull(response));
                        picUrl.append(mResolutionSpinner.
                                getSelectedItem().toString() + ".jpg");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(MainActivity.this).
                                        load(picUrl.toString()).into(mImageView);
                            }
                        });

                    }

                    @Override
                    public void error(Exception e) {

                    }
                });
                break;
            case R.id.get_picture_okhttp:
                HttpUtil.sendOkHttpRequest(sBingUrl, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String body = response.body().string();
                        final StringBuilder picUrl = new StringBuilder();
                        picUrl.append(BING);
                        picUrl.append(XMLParseUtil.parseWithSax(body));
                        picUrl.append(mResolutionSpinner.
                                getSelectedItem().toString() + ".jpg");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(MainActivity.this)
                                        .load(picUrl.toString()).into(mImageView);
                            }
                        });
                    }
                });
                break;
        }

    }

}
