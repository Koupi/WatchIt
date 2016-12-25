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
    private static final String filmIdField = "id";
    private static final String posterURLField = "poster";
    private static final String  releaseYearField = "release";
    private static final String typeField = "type";
    private static final String watchedField = "watched";
    private static final String plannedField = "planned";
    private static final String planDateField ="planDate";
    private static String runtimeField="runtime";
    private String genreField = "genre";
    private String countryField = "county";
    private String directorField = "director";
    private String actorsField = "actors";
    DBHelper(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createString = "create table " + sqLiteDatabase + " (" +
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
