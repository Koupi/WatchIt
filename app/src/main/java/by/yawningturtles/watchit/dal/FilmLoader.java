package by.yawningturtles.watchit.dal;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by marija.savtchouk on 25.12.2016.
 */

public class FilmLoader {

    private FilmNetLoader netLoader;
    private FilmDbLoader dbLoader;
    public FilmLoader(Context context){
        this.netLoader = new FilmNetLoader();
        this.dbLoader = new FilmDbLoader(context);
    }

    public Film getFilmByID(String id){
        return netLoader.loadFilmById(id);
    }

    public List<ShortFilm> getShortFilmBySearch(String titlePart, String type, int year){
        List<ShortFilm> filmList = new ArrayList<ShortFilm>(netLoader.loadFilmBySearch(titlePart, type, year));
        //handle dups
        filmList.addAll(dbLoader.getFilmBySearch(titlePart, type,year));
        return filmList;
    }

    public List<ShortFilm> getShortFilmBySearch(String titlePart, String type){
        List<ShortFilm> filmList = new ArrayList<ShortFilm>(netLoader.loadFilmBySearch(titlePart, type, -1));
        //handle dups
        filmList.addAll(dbLoader.getFilmBySearch(titlePart, type, -1));
        return filmList;
    }
    public List<ShortFilm> getShortFilmBySearch(String titlePart, int year){
        List<ShortFilm> filmList = new ArrayList<ShortFilm>(netLoader.loadFilmBySearch(titlePart, null, year));
        //handle dups
        filmList.addAll(dbLoader.getFilmBySearch(titlePart, null, year));
        return filmList;
    }

    public List<ShortFilm> getShortFilmBySearch(String titlePart){
        List<ShortFilm> filmList = new ArrayList<ShortFilm>(netLoader.loadFilmBySearch(titlePart, null, -1));
        //handle dups
        filmList.addAll(dbLoader.getFilmBySearch(titlePart, null, -1));
        return filmList;
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

    public Bitmap GetImageByUrl(String url){
        return netLoader.getImage(url); // null for now
    }
}
