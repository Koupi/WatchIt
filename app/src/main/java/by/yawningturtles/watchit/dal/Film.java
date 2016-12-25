package by.yawningturtles.watchit.dal;

import java.util.Calendar;
import java.util.List;

/**
 * Created by marija.savtchouk on 25.12.2016.
 */

public class Film extends ShortFilm{

    private String runtime;
    private String genre;
    private String country;
    private String director;
    private String actors;
    private String plot;
    Film(){

    }

    Film(String filmId, String posterURL, String title, int releaseYear, String runtime, String genre,
         String type, String country, String director, String actors, String plot){
        super(filmId, posterURL, title, releaseYear, type);
        this.runtime = runtime;
        this.genre = genre;
        this.country = country;
        this.director = director;
        this.actors = actors;
        this.plot = plot;
    }

    Film(String filmId, String posterURL, String title, int releaseYear, String runtime, String genre,
         String type, String country, String director, String actors, String plot, boolean watched, boolean planned, Calendar planDate){
        super(filmId, posterURL, title, releaseYear, type, watched, planned, planDate);
        this.runtime = runtime;
        this.genre = genre;
        this.country = country;
        this.director = director;
        this.actors = actors;
        this.plot = plot;
    }
    public String getPlot() {
        return plot;
    }

    void setPlot(String plot) {
        this.plot = plot;
    }

    public String getActors() {
        return actors;
    }

    void setActors(String actors) {
        this.actors = actors;
    }

    public String getDirector() {
        return director;
    }

    void setDirector(String director) {
        this.director = director;
    }

    public String getCountry() {
        return country;
    }

    void setCountry(String country) {
        this.country = country;
    }

    public String getGenre() {
        return genre;
    }

    void setGenre(String genre) { this.genre = genre; }

    public String getRuntime() {
        return runtime;
    }

    void setRuntime(String runtime) {
        this.runtime = runtime;
    }
}
