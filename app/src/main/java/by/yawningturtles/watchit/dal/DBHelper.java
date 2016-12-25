package by.yawningturtles.watchit.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marija.savtchouk on 25.12.2016.
 */

class DBHelper extends SQLiteOpenHelper {
    DBHelper(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
