package edu.uw.providerdemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class WordProvider extends ContentProvider {

    //content://authority/resource/id

    public static final String AUTHORITY = "edu.uw.providerdemo.provider";
    public static final String RESOURCE = "words";

    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+RESOURCE);

    private static final int WORD_LIST_URI = 1; //words
    private static final int WORD_SINGLE_URI = 2; //words/:id

    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        matcher.addURI(AUTHORITY, RESOURCE, WORD_LIST_URI);
        matcher.addURI(AUTHORITY, RESOURCE+"/#", WORD_SINGLE_URI);
    }

    private WordDB.DatabaseHelper helper;
    @Override
    public boolean onCreate() {
        WordDB.DatabaseHelper helper = new WordDB.DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        //Database access
        SQLiteDatabase db = helper.getReadableDatabase();

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(WordDB.Words.TABLE_NAME);

        switch(matcher.match(uri)) {
            case WORD_LIST_URI:
                break;
            case WORD_SINGLE_URI:
                builder.appendWhere(WordDB.Words._ID + "=" + uri.getLastPathSegment());
            default:
                throw new IllegalArgumentException("Unknown URI "+uri);
        }

        Cursor results = builder.query(db, projection,
                selection,selectionArgs,null,null,sortOrder
        );

        results.setNotificationUri(getContext().getContentResolver(), uri);

        return results;
    }

    public WordProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }





    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        switch(matcher.match(uri)) {
            case WORD_LIST_URI:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + RESOURCE;
            case WORD_SINGLE_URI:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + RESOURCE;

            default:
                throw new IllegalArgumentException("Unknown URI "+uri);
        }
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}

