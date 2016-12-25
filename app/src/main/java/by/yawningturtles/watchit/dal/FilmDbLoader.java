package by.yawningturtles.watchit.dal;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by marija.savtchouk on 25.12.2016.
 */

public class FilmDbLoader {
    DBHelper dbHelper;
    public final static String dbName = "film";

    public FilmDbLoader(Context context){
        dbHelper = new DBHelper(context, dbName, 1);
    }

    List<ShortFilm> getFilmBySearch(String title, String type, int year){
        ArrayList<ShortFilm> films = new ArrayList<>();
        films.add(new ShortFilm("tt0372784", "https://images-na.ssl-images-amazon.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg", "Batman Begins", 2005, "movie"));
        films.add(new ShortFilm("tt0372784", "https://images-na.ssl-images-amazon.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg", "Batman Begins", 2005, "movie"));
        return films;
    }

    public List<Film> getWatchedFilms(){
        List<Film> films = new ArrayList<Film>();
        films.add(new Film("tt0372784", "https://images-na.ssl-images-amazon.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg", "Batman Begins", 2005, "140 min", "Action, Adventure",
                "movie", "USA, UK", "Christopher Nolan",  new ArrayList<String>(){{ add("Christian Bale"); add("Michael Caine"); add("Liam Neeson"); }},
                "After training with his mentor, Batman begins his fight to free crime-ridden Gotham City from the corruption that Scarecrow and the League of Shadows have cast upon it.", true, false, null));
        films.add(new Film("tt0372784", "https://images-na.ssl-images-amazon.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg", "Batman Begins", 2005, "140 min", "Action, Adventure",
                "movie", "USA, UK", "Christopher Nolan",  new ArrayList<String>(){{ add("Christian Bale"); add("Michael Caine"); add("Liam Neeson"); }},
                "After training with his mentor, Batman begins his fight to free crime-ridden Gotham City from the corruption that Scarecrow and the League of Shadows have cast upon it.", true, false, null));
        return films;
    }

    public List<Film> getPlannedFilms(){
        List<Film> films = new ArrayList<Film>();
        films.add(new Film("tt0372784", "https://images-na.ssl-images-amazon.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg", "Batman Begins", 2005, "140 min", "Action, Adventure",
                "movie", "USA, UK", "Christopher Nolan",  new ArrayList<String>(){{ add("Christian Bale"); add("Michael Caine"); add("Liam Neeson"); }},
                "After training with his mentor, Batman begins his fight to free crime-ridden Gotham City from the corruption that Scarecrow and the League of Shadows have cast upon it.", false, true, new GregorianCalendar(2017, Calendar.DECEMBER, 3)));
        films.add(new Film("tt0372784", "https://images-na.ssl-images-amazon.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg", "Batman Begins", 2005, "140 min", "Action, Adventure",
                "movie", "USA, UK", "Christopher Nolan",  new ArrayList<String>(){{ add("Christian Bale"); add("Michael Caine"); add("Liam Neeson"); }},
                "After training with his mentor, Batman begins his fight to free crime-ridden Gotham City from the corruption that Scarecrow and the League of Shadows have cast upon it.", false, true, new GregorianCalendar(2017, Calendar.DECEMBER, 3)));
        return films;
    }

    public void setPlannedFilm(String filmId, boolean value, Calendar date){

    }

    public void setWatchedFilm(String filmId, boolean value){

    }
}
