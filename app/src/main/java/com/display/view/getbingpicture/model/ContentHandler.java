package com.display.view.getbingpicture.model;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by yangyi on 17-9-30.
 */

public class ContentHandler extends DefaultHandler {

    private StringBuilder mUrl;
    private String mNodeName;

    @Override
    public void startDocument() throws SAXException {
        mUrl = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        mNodeName = localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (mNodeName.equals("url")) {
            mUrl.append(ch, start, length);
        }
    }

    public StringBuilder getUrl() {
        return mUrl;
    }
}
