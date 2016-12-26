package by.yawningturtles.watchit.dal;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by marija.savtchouk on 25.12.2016.
 */

class FilmNetLoader {
    Film loadFilmById(String id) {
        String request = APIConstants.NET_ADDRESS + "?" + APIConstants.ID_PARAM + id + "&" + APIConstants.PLOT_PARAM + "&" + APIConstants.FORMAT_PARAM;
        LoadFilmNet task = new LoadFilmNet();

        return task.load(request);
    }

    List<ShortFilm> loadFilmBySearch(String title, String type, int year){
        title = title.replaceAll(" ", "%20");
        String request = APIConstants.NET_ADDRESS + "?" + APIConstants.SEARCH_PARAM+title;
        if(type!=null){
            request+="&"+APIConstants.TYPE_PARAM+type;
        }
        if(year!=-1){
            request+="&"+APIConstants.YEAR_PARAM+year;
        }
        request+="&"+APIConstants.FORMAT_PARAM;
        SearchFilmNet task = new SearchFilmNet();
        return task.load(request);
    }

    Bitmap getImage(String url){
        return null;
    }


}
