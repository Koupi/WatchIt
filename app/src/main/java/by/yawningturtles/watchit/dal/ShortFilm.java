package by.yawningturtles.watchit.dal;

import android.util.Log;

import java.util.Calendar;
import java.util.List;

/**
 * Created by marija.savtchouk on 25.12.2016.
 */

public class ShortFilm {
    private String filmId;
    private String posterURL;
    private String title;
    private int releaseYear;
    private String type;
    private boolean watched;
    private boolean planned;
    private Calendar planDate;
    ShortFilm(){

    }

    ShortFilm(String filmId, String posterURL, String title, int releaseYear, String type){
        this.filmId = filmId;
        this.posterURL = posterURL;
        this.title = title;
        this.releaseYear = releaseYear;
        this.type = type;
    }

    ShortFilm(String filmId, String posterURL, String title, int releaseYear, String type, boolean watched, boolean planned, Calendar planDate){
        this.filmId = filmId;
        this.posterURL = posterURL;
        this.title = title;
        this.releaseYear = releaseYear;
        this.type = type;
        this.watched = watched;
        this.planned = planned;
        this.planDate = planDate;
    }

    public String getFilmId() {
        return filmId;
    }

    void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public String getPosterURL() {
        return posterURL;
    }

    void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public boolean isWatched(){
        return watched;
    }

    void setWatched(boolean flag){
        this.watched = flag;
    }

    public boolean isPlanned(){
        return planned;
    }

    void setPlanned(boolean flag){
        this.planned = flag;
    }

    public Calendar getPlanningDate(){
        return planDate;
    }

    void setPlanningDate(Calendar planned){
        this.planDate = planned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        ShortFilm shortFilm = (ShortFilm) o;
        return filmId != null ? filmId.equals(shortFilm.filmId) : shortFilm.filmId == null;

    }

    @Override
    public int hashCode() {
        return filmId != null ? filmId.hashCode() : 0;
    }
}
