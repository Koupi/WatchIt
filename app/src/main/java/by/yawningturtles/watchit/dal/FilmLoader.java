package by.yawningturtles.watchit.dal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
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
        Film film =  dbLoader.getFilmById(id);
        if(!hasConnection() && film == null) {
            throw new ConnectionException();
        }
        if(film == null){
            return netLoader.loadFilmById(id);
        }
        return film;
    }

    public List<ShortFilm> getShortFilmBySearch(String titlePart, String type, int year) throws ConnectionException {
        if(!hasConnection()) {
            throw new ConnectionException();
        }
        List<ShortFilm> filmList = new ArrayList<ShortFilm>(dbLoader.getFilmBySearch(titlePart, type, year));
        List<ShortFilm> otherFilms = netLoader.loadFilmBySearch(titlePart, type, year);

        for (ShortFilm film : otherFilms) {
            if(!filmList.contains(film)) {
                filmList.add(film);
            }
        }
        return filmList;
    }

    public List<ShortFilm> getShortFilmBySearch(String titlePart, String type) throws ConnectionException {
        return getShortFilmBySearch(titlePart, type, -1);
    }
    public List<ShortFilm> getShortFilmBySearch(String titlePart, int year) throws ConnectionException {
        return getShortFilmBySearch(titlePart, null, year);
    }

    public List<ShortFilm> getShortFilmBySearch(String titlePart) throws ConnectionException {
        return getShortFilmBySearch(titlePart, null, -1);
    }

    private void sortByCalendar(List<Film> films){
        Collections.sort(films, new Comparator<Film>() {
            public int compare(Film m1, Film m2) {
                Calendar c1 = m1.getPlanningDate();
                Calendar c2 = m2.getPlanningDate();
                if(c1==null||c2==null){
                    return c1==null&&c2==null?0:((c1==null)?1:-1);
                }
                return c2.compareTo(c1);
            }
        });
    }

    public List<Film> getWatchedFilms(){
        List<Film> films =  dbLoader.getWatchedFilms();
        sortByCalendar(films);
        return films;
    }

    public List<Film> getPlannedFilms(){
        List<Film> films = dbLoader.getPlannedFilms();
        sortByCalendar(films);
        return films;
    }

    public Film setFilmToWatched(Film film, boolean watched){
        setFilmToWatched(film, watched, new GregorianCalendar());
        return film;
    }

    public Film setFilmToPlanned(Film film, boolean planned, Calendar date) {
        film.setPlanned(planned);
        film.setPlanningDate(date);
        if(planned){
            film.setWatched(false);
        }
        dbLoader.setPlannedFilm(film, planned, date);
        return film;
    }

    public Film setFilmToWatched(Film film, boolean watched, Calendar date) {
        film.setWatched(watched);
        film.setPlanningDate(date);
        if(watched) {
            film.setPlanned(false);
        }
        dbLoader.setWatchedFilm(film, watched, date);
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
