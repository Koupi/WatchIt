package by.yawningturtles.watchit.dal;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.CalendarContract;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by marija.savtchouk on 25.12.2016.
 */

class FilmDbLoader {
    DBHelper dbHelper;
    public final static String dbName = "film";

    public FilmDbLoader(Context context){
        dbHelper = new DBHelper(context, dbName, 1);
    }

    List<? extends ShortFilm> getFilmBySearch(String title, String type, int year){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Film> films = new ArrayList<>();

        String whereQuery = DBHelper.titleField + " LIKE ?";
        List<String> values = new ArrayList<>();
        values.add("%"+title+"%");
        if(type != null){
            whereQuery += " AND "+DBHelper.typeField +" = ?";
            values.add(type);
        }
        if(year !=-1){
            whereQuery += " AND "+DBHelper.releaseYearField + " = ?";
            values.add(Integer.toString(year));
        }
        String[] paramArr = new String[values.size()];
        paramArr = values.toArray(paramArr);
        Cursor c = db.query(dbName, null, whereQuery, paramArr, null, null, null);
        getFilmsFromCursor(c, films);
        c.close();
        dbHelper.close();
        return films;
    }

    public List<Film> getWatchedFilms(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Film> films = new ArrayList<>();
        Cursor c = db.query(dbName, null, DBHelper.watchedField + "= ?", new String[]{Integer.toString(1)}, null, null, null);
        getFilmsFromCursor(c, films);
        c.close();
        dbHelper.close();
        return films;
    }
    private void getFilmsFromCursor(Cursor c, ArrayList<Film> outFilm){
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex(DBHelper.filmIdField);
            int titleColIndex = c.getColumnIndex(DBHelper.titleField);
            int plotColIndex = c.getColumnIndex(DBHelper.plotField);
            int posterColIndex = c.getColumnIndex(DBHelper.posterURLField);
            int yearColIndex = c.getColumnIndex(DBHelper.releaseYearField);
            int watchedColIndex = c.getColumnIndex(DBHelper.watchedField);
            int plannedColIndex = c.getColumnIndex(DBHelper.plannedField);
            int planDateColIndex = c.getColumnIndex(DBHelper.planDateField);
            int runtimeColIndex = c.getColumnIndex(DBHelper.runtimeField);
            int genreColIndex = c.getColumnIndex(DBHelper.genreField);
            int countryColIndex = c.getColumnIndex(DBHelper.countryField);
            int directorColIndex = c.getColumnIndex(DBHelper.directorField);
            int actorsColIndex = c.getColumnIndex(DBHelper.actorsField);
            int typeColIndex = c.getColumnIndex(DBHelper.typeField);
            do {
                Film film = new Film();
                film.setFilmId(c.getString(idColIndex));
                film.setPosterURL(c.getString(posterColIndex));
                film.setTitle(c.getString(titleColIndex));
                film.setPlot(c.getString(plotColIndex));
                film.setReleaseYear(c.getInt(yearColIndex));
                Log.d("WP", c.getString(watchedColIndex)+" "+c.getString(plannedColIndex));
                film.setWatched(c.getInt(watchedColIndex) == 1);
                film.setPlanned(c.getInt(plannedColIndex) == 1);
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                Calendar calendar = Calendar.getInstance();;
                try {
                    calendar.setTime(sdf.parse(c.getString(planDateColIndex)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                film.setPlanningDate(calendar);
                film.setRuntime(c.getString(runtimeColIndex));
                film.setGenre(c.getString(genreColIndex));
                film.setCountry(c.getString(countryColIndex));
                film.setDirector(c.getString(directorColIndex));
                film.setActors(c.getString(actorsColIndex));
                film.setType(c.getString(typeColIndex));
                Log.d("WP", ""+film.isPlanned() +film.isWatched());
                outFilm.add(film);
            } while (c.moveToNext());
        }
    }
    public List<Film> getPlannedFilms(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Film> films = new ArrayList<>();
        Cursor c = db.query(dbName, null, DBHelper.plannedField + "= ?", new String[]{Integer.toString(1)}, null, null, null);
        getFilmsFromCursor(c, films);
        c.close();
        dbHelper.close();
        return films;
    }

    public void setPlannedFilm(Film film, boolean value, Calendar date){
        String selectQuery= "SELECT * FROM " + dbName+" WHERE "+DBHelper.filmIdField +" = ?";
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.plannedField, value);
        cv.put(DBHelper.watchedField, film.isWatched());
        Log.d("WHATCHED",  Boolean.toString(film.isWatched()));
        if(date!=null) {
            cv.put(DBHelper.planDateField, date.getTime().toString());
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{film.getFilmId()});
        String id;
        if(cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndex(DBHelper.filmIdField));
            db.update(dbName, cv, DBHelper.filmIdField+"= ?", new String[]{id});
        }
        else {
            cv.put(DBHelper.filmIdField, film.getFilmId());
            cv.put(DBHelper.titleField, film.getTitle());
            cv.put(DBHelper.plotField, film.getPlot());
            cv.put(DBHelper.posterURLField, film.getPosterURL());
            cv.put(DBHelper.releaseYearField, film.getReleaseYear());
            cv.put(DBHelper.typeField, film.getType());
            cv.put(DBHelper.runtimeField, film.getRuntime());
            cv.put(DBHelper.genreField, film.getGenre());
            cv.put(DBHelper.countryField, film.getCountry());
            cv.put(DBHelper.directorField, film.getDirector());
            cv.put(DBHelper.actorsField, film.getActors());
            db.insert(dbName, null, cv);
        }
        cursor.close();
        dbHelper.close();
    }

    public Film getFilmById(String filmId) {
        String selectQuery= "SELECT * FROM " + dbName+" WHERE "+DBHelper.filmIdField +" = ?";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{filmId});
        ArrayList<Film> films = new ArrayList<>();
        getFilmsFromCursor(cursor, films);
        cursor.close();
        if(films.isEmpty()){
            return null;
        }
        else return films.get(0);
    }

    public void setWatchedFilm(Film film, boolean value, Calendar date){
        String selectQuery= "SELECT * FROM " + dbName+" WHERE "+DBHelper.filmIdField +" = ?";
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.watchedField, value);
        Log.d("PLANNED",  Boolean.toString(film.isPlanned()));
        cv.put(DBHelper.plannedField, film.isPlanned());
        if(date!=null) {
            cv.put(DBHelper.planDateField, date.getTime().toString());
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{film.getFilmId()});
        String id;
        if(cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndex(DBHelper.filmIdField));
            db.update(dbName, cv, DBHelper.filmIdField+"= ?", new String[]{id});
        }
        else {
            cv.put(DBHelper.filmIdField, film.getFilmId());
            cv.put(DBHelper.titleField, film.getTitle());
            cv.put(DBHelper.plotField, film.getPlot());
            cv.put(DBHelper.posterURLField, film.getPosterURL());
            cv.put(DBHelper.releaseYearField, film.getReleaseYear());
            cv.put(DBHelper.typeField, film.getType());
            cv.put(DBHelper.runtimeField, film.getRuntime());
            cv.put(DBHelper.genreField, film.getGenre());
            cv.put(DBHelper.countryField, film.getCountry());
            cv.put(DBHelper.directorField, film.getDirector());
            cv.put(DBHelper.actorsField, film.getActors());
            db.insert(dbName, null, cv);
        }
        cursor.close();
        dbHelper.close();
    }
}
