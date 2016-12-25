package by.yawningturtles.watchit.dal;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marija.savtchouk on 25.12.2016.
 */

class FilmNetLoader {
    Film loadFilmById(String id){
        return  new Film("tt0372784", "https://images-na.ssl-images-amazon.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg", "Batman Begins", 2005, "140 min", "Action, Adventure",
                "movie", "USA, UK", "Christopher Nolan",  new ArrayList<String>(){{ add("Christian Bale"); add("Michael Caine"); add("Liam Neeson"); }},
                "After training with his mentor, Batman begins his fight to free crime-ridden Gotham City from the corruption that Scarecrow and the League of Shadows have cast upon it.");
    }

    List<ShortFilm> loadFilmBySearch(String title, String type, int year){
        ArrayList<ShortFilm> films = new ArrayList<>();
        films.add(new ShortFilm("tt0372784", "https://images-na.ssl-images-amazon.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg", "Batman Begins", 2005, "movie"));
        films.add(new ShortFilm("tt0372784", "https://images-na.ssl-images-amazon.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg", "Batman Begins", 2005, "movie"));
        return films;
    }

    Bitmap getImage(String url){
        return null;
    }


}
