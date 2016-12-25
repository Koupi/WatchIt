package by.yawningturtles.watchit.dal;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by marija.savtchouk on 25.12.2016.
 */

class FilmNetLoader {
    Film loadFilmById(String id) {
        String request = APIConstants.NET_ADDRESS + "?" + APIConstants.ID_PARAM + id + "&" + APIConstants.PLOT_PARAM + "&" + APIConstants.FORMAT_PARAM;
        LoadFilmNetTask task = new LoadFilmNetTask();

        return task.doInBackground(request);
    }

    List<ShortFilm> loadFilmBySearch(String title, String type, int year){
        String request = APIConstants.NET_ADDRESS + "?" + APIConstants.SEARCH_PARAM+title;
        if(type!=null){
            request+="&"+APIConstants.TYPE_PARAM+type;
        }
        if(year!=-1){
            request+="&"+APIConstants.YEAR_PARAM+year;
        }
        request+="&"+APIConstants.FORMAT_PARAM;
        SearchFilmNetTask task = new SearchFilmNetTask();
        return task.doInBackground(request);
    }

    Bitmap getImage(String url){
        return null;
    }


}
