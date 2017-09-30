package com.display.view.getbingpicture.util;

import java.io.StringReader;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import com.display.view.getbingpicture.model.ContentHandler;

/**
 * Created by rookieyang on 17-9-28.
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

    public static String parseWithSax(String xmlData) {
        ContentHandler contentHandler = new ContentHandler();
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            XMLReader reader = parserFactory.newSAXParser().getXMLReader();
            reader.setContentHandler(contentHandler);
            reader.parse(new InputSource(new StringReader(xmlData)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = contentHandler.getUrl().toString().trim();
        String regex = "\\d*x\\d*.jpg";
        url = url.split(regex)[0];
        return url;
    }

}
