package com.display.view.getbingpicture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import com.display.view.getbingpicture.util.HttpUtil;
import com.display.view.getbingpicture.util.HttpUtil.HttpCallbackListener;
import com.display.view.getbingpicture.util.PictureUtil;
import com.display.view.getbingpicture.util.XMLParseUtil;

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
        mResolutionSpinner = findViewById(R.id.resolution_spinner);
        mImageView = findViewById(R.id.bing_picture);
        getPicBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_picture:
                HttpUtil.sendHttpRequest(sBingUrl, new HttpCallbackListener() {
                    @Override
                    public void finish(String response) {
                        StringBuilder picUrl = new StringBuilder();
                        picUrl.append(BING);
                        picUrl.append(XMLParseUtil.parseWithPull(response));
                        picUrl.append(mResolutionSpinner.
                                getSelectedItem().toString() + ".jpg");
                        Log.i("TAG", "finish: " + picUrl.toString());
                        PictureUtil.loadPic(picUrl.toString(), mImageView);
                    }

                    @Override
                    public void error(Exception e) {

                    }
                });
                break;
            case R.id.get_picture_okhttp:

                break;
        }

    }

}
