package by.yawningturtles.watchit.dal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by marija.savtchouk on 25.12.2016.
 */

class LoadImgTask {
    public Bitmap doInBackground(String... strings) {
        try {
            InputStream in = new URL(strings[0]).openStream();
            return BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
