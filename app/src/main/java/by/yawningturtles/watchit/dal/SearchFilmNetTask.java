package by.yawningturtles.watchit.dal;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Created by marija.savtchouk on 25.12.2016.
 */

class SearchFilmNetTask extends AsyncTask<String, String, List<ShortFilm>> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<ShortFilm> doInBackground(String... strings) {
        List<ShortFilm> list = new ArrayList<ShortFilm>();
        try {
            URL url = new URL(strings[0]);
            URLConnection conn = url.openConnection();
            Scanner stream = new Scanner(conn.getInputStream());
            String line = stream.nextLine();
            Log.d("FROM SERVICE", line);
            JSONObject doc = new JSONObject(line);
            String response = doc.getString(APIConstants.RESPONSE);
            boolean haveResponse = Boolean.parseBoolean(response);
            if(!haveResponse){
                return list;
            }
            JSONArray movies = doc.getJSONArray(APIConstants.SEARCH);
            for (int j = 0; j < movies.length(); ++j) {
                JSONObject movie =  movies.getJSONObject(j);
                String titleText = movie.getString(APIConstants.TITLE);
                String yearText =  movie.getString(APIConstants.YEAR);
                String id =  movie.getString(APIConstants.ID);
                String type = movie.getString(APIConstants.TYPE);
                String poster = movie.getString(APIConstants.POSTER);
                ShortFilm outMovie = new ShortFilm();
                outMovie.setTitle(titleText);
                outMovie.setFilmId(id);
                outMovie.setType(type);
                outMovie.setPosterURL(poster);
                outMovie.setReleaseYear(Integer.parseInt(yearText.substring(0, 4)));
                list.add(outMovie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(List<ShortFilm> result) {
        super.onPostExecute(result);
    }
}
