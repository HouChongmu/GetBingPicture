package com.display.view.getbingpicture.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.util.Printer;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.display.view.getbingpicture.util.HttpUtil.HttpCallbackListener;

/**
 * Created by yangyi on 17-9-28.
 */

public class PictureUtil {

    public static void loadPic(String picUrl, final ImageView imageView) {
        final Context context = imageView.getContext();
        final SharedPreferences preferences = context.getSharedPreferences("bing",
                Context.MODE_PRIVATE);
        String picStr = preferences.getString("pic", null);

        if (picStr == null) {
            Log.i("TAG", "loadPic: " + picUrl);
            HttpUtil.sendHttpRequest(picUrl, new HttpCallbackListener() {
                @Override
                public void finish(final String response) {
                    Editor editor = preferences.edit();
                    editor.putString("pic", response);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(context).load(response).into(imageView);
                        }
                    });
                }

                @Override
                public void error(Exception e) {

                }
            });
        } else {
            Glide.with(imageView).load(picStr);
        }
    }

    public void updateBingPic(String picUrl) {

    }

    /*private static Bitmap strToBitmap(String picStr) {
        byte[] picArray = Base64.decode(picStr, Base64.NO_PADDING);
        BitmapFactory.Options options = new Options();
        options.inJustDecodeBounds = true;
        Bitmap picBitmap = BitmapFactory.decodeByteArray(picArray,
                0, picArray.length);
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;
        String imageType = options.outMimeType;
        options.inSampleSize = 4;
        options.inJustDecodeBounds = false;
        Log.i("TAG", "strToBitmap: ");
        return picBitmap;
    }*/

}
