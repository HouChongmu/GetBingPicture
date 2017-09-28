package com.display.view.getbingpicture.util;

import java.io.IOException;
import java.io.StringReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * Created by yangyi on 17-9-28.
 */

public class XMLParseUtil {

    public static String parseWithPull(String xmlData) {
        String url = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser pullParser = factory.newPullParser();
            pullParser.setInput(new StringReader(xmlData));

            int eventType = pullParser.getEventType();
            while (url == null) {
                String nodeName = pullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (nodeName.equals("url")) {
                            url = pullParser.nextText();
                        }
                    break;
                }
                eventType = pullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String regex = "\\d*x\\d*.jpg";
        url = url.split(regex)[0];
        return url;
    }

}
