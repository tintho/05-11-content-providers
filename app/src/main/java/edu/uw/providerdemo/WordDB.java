package edu.uw.providerdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by iGuest on 5/11/2016.
 */
public class WordDB {

    private WordDB() {
        //cannot be instantiated
    }

    private static final String DATABASE_NAME = "words.db";
    private static final int DATABASE_VERSION = 1;

    //represent the words table
    static class Words implements BaseColumns{
        public static final String TABLE_NAME = "words";
        public static final String COL_WORD = "word";//name of column
        public static final String COL_COUNT = "count";//name of column
    }

    static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        private static final String CREATE_WORD_TABLE =
                "CREATE TABLE " + Words.TABLE_NAME + "(" +
                    Words._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Words.COL_WORD + " TEXT, " +
                    Words.COL_COUNT + " INTEGER" +
                ")";

        private static final String DROP_WORDS_TABLE =
                "DROP TABLE IF EXISTS " + Words.TABLE_NAME;

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_WORD_TABLE);

            ContentValues sample1 = new ContentValues();
            sample1.put(Words.COL_WORD, "Embiggen");
            sample1.put(Words.COL_COUNT, "0");
            ContentValues sample2 = new ContentValues();
            sample2.put(Words.COL_WORD, "Cromulent");
            sample2.put(Words.COL_COUNT, "0");

            db.insert(Words.TABLE_NAME, null, sample1);
            db.insert(Words.TABLE_NAME, null, sample2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_WORDS_TABLE);
            onCreate(db);
        }
    }

}
