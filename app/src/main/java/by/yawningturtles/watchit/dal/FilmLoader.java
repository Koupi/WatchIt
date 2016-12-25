package by.yawningturtles.watchit.dal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import by.yawningturtles.watchit.R;

/**
 * Created by marija.savtchouk on 25.12.2016.
 */

public class FilmLoader {

    private FilmNetLoader netLoader;
    private FilmDbLoader dbLoader;
    private Context context;

    private boolean hasConnection()
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }

    public FilmLoader(Context context){
        this.netLoader = new FilmNetLoader();
        this.dbLoader = new FilmDbLoader(context);
        this.context = context;
    }

    public Film getFilmByID(String id) throws ConnectionException{
        if(!hasConnection()) {
            throw new ConnectionException();
        }
        return netLoader.loadFilmById(id);
    }

    public List<ShortFilm> getShortFilmBySearch(String titlePart, String type, int year) throws ConnectionException {
        if(!hasConnection()) {
            throw new ConnectionException();
        }
        List<ShortFilm> filmList = new ArrayList<ShortFilm>(netLoader.loadFilmBySearch(titlePart, type, year));
        //handle dups
        filmList.addAll(dbLoader.getFilmBySearch(titlePart, type,year));
        return filmList;
    }

    public List<ShortFilm> getShortFilmBySearch(String titlePart, String type) throws ConnectionException {
        return getShortFilmBySearch(titlePart, type, -1);
    }
    public List<ShortFilm> getShortFilmBySearch(String titlePart, int year) throws ConnectionException {
        return getShortFilmBySearch(titlePart, null, -1);
    }

    public List<ShortFilm> getShortFilmBySearch(String titlePart) throws ConnectionException {
        return getShortFilmBySearch(titlePart, null, -1);
    }

    public List<Film> getWatchedFilms(){
        return dbLoader.getWatchedFilms();
    }

    public List<Film> getPlannedFilms(){
        return dbLoader.getPlannedFilms();
    }

    public Film setFilmToPlanned(Film film, boolean planned){
        film.setPlanned(planned);
        dbLoader.setPlannedFilm(film.getFilmId(), planned, null);
        return film;
    }

    public Film setFilmToPlanned(Film film, boolean planned, Calendar date) {
        film.setPlanned(planned);
        film.setPlanningDate(date);
        dbLoader.setPlannedFilm(film.getFilmId(), planned, date);
        return film;
    }

    public Film setFilmToWatched(Film film, boolean watched) {
        film.setWatched(watched);
        dbLoader.setWatchedFilm(film.getFilmId(), watched);
        return film;
    }

    public Bitmap getImageByUrl(String url){
        if(!hasConnection()) {
            return BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.default_poster);
        }

        Bitmap outImg = netLoader.getImage(url);
        if(outImg == null){
            outImg =  BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.default_poster);
        }
        return outImg;
    }
}
