package by.yawningturtles.watchit.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by marija.savtchouk on 25.12.2016.
 */

class DBHelper extends SQLiteOpenHelper {
    public static final String titleField = "title";
    public static final String plotField = "plot";
    public static final String filmIdField = "id";
    public static final String posterURLField = "poster";
    public static final String  releaseYearField = "release";
    public static final String typeField = "type";
    public static final String watchedField = "watched";
    public static final String plannedField = "planned";
    public static final String planDateField ="planDate";
    public static final String runtimeField="runtime";
    public static final String genreField = "genre";
    public static final String countryField = "county";
    public static final String directorField = "director";
    public static final String actorsField = "actors";
    private String dbName;
    DBHelper(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);
        this.dbName = dbName;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createString = "create table " + dbName + " (" +
                filmIdField + " text primary key, " +
                titleField + " text, " +
                plotField + " text, " +
                posterURLField + " text, "  +
                releaseYearField + " integer, " +
                typeField + " text, " +
                runtimeField + " text, " +
                genreField + " text, " +
                countryField + " text, "+
                directorField + " text, " +
                actorsField + " text, " +
                watchedField + " integer, " +
                plannedField + " integer, " +
                planDateField + " datetime);";
        sqLiteDatabase.execSQL(createString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old_version, int new_version) {

    }
}
