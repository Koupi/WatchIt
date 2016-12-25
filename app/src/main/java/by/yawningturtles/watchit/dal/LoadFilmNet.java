package by.yawningturtles.watchit.dal;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.json.*;

/**
 * Created by marija.savtchouk on 25.12.2016.
 */

class LoadFilmNet {
    protected Film load(String... strings) {
        try {
            URL url = new URL(strings[0]);
            URLConnection conn = url.openConnection();
            Scanner stream = new Scanner(conn.getInputStream());
            String line = stream.nextLine();
            JSONObject doc = new JSONObject(line);

            String response = doc.getString(APIConstants.RESPONSE);
            boolean haveResponse = Boolean.parseBoolean(response);
            if (!haveResponse) {
                return null;
            }
            String titleText = doc.getString(APIConstants.TITLE);
            String yearText = doc.getString(APIConstants.YEAR);
            String id = doc.getString(APIConstants.ID);
            String type = doc.getString(APIConstants.TYPE);
            String poster = doc.getString(APIConstants.POSTER);
            String runtime = doc.getString(APIConstants.RUNTIME);
            String genre = doc.getString(APIConstants.GENRE);
            String country = doc.getString(APIConstants.COUNTRY);
            String director = doc.getString(APIConstants.DIRECTOR);
            String plot = doc.getString(APIConstants.PLOT);
            String actors = doc.getString(APIConstants.ACTORS);
            Film outMovie = new Film();
            outMovie.setTitle(titleText);
            outMovie.setFilmId(id);
            outMovie.setType(type);
            outMovie.setPosterURL(poster);
            outMovie.setReleaseYear(Integer.parseInt(yearText.substring(0, 4)));
            outMovie.setRuntime(runtime);
            outMovie.setGenre(genre);
            outMovie.setCountry(country);
            outMovie.setDirector(director);
            outMovie.setPlot(plot);
            outMovie.setActors(actors);
            return outMovie;
        }catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
