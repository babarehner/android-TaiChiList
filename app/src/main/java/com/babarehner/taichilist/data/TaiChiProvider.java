package com.babarehner.taichilist.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.babarehner.taichilist.data.TaiChiListContract.ChiExercises.CHI_EXERCISES_ITEM_TYPE;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiExercises.CHI_EXERCISES_LIST_TYPE;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiExercises.CHI_EXERCISES_TABLE;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiExercises._IDX;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiHeadings.CHI_HEADINGS_ITEM_TYPE;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiHeadings.CHI_HEADINGS_LIST_TYPE;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiHeadings.CHI_HEADINGS_TABLE;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiHeadings._IDH;
import static com.babarehner.taichilist.data.TaiChiListContract.PATH_CHI_EXERCISES_TABLE_NAME;
import static com.babarehner.taichilist.data.TaiChiListContract.PATH_CHI_HEADINGS_TABLE_NAME;
import static com.babarehner.taichilist.data.TaiChiListContract.TAI_CHI_LIST_AUTHORITY;


/**
 * Project Name: Tai Chi List
 * <p>
 * Copyright 1/11/20 by Mike Rehner
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class TaiChiProvider extends ContentProvider {

    public static final String LOG_TAG = TaiChiProvider.class.getSimpleName();

    private static final int PROVIDER_HEADINGS = 100;
    private static final int PROVIDER_HEADINGS_ID = 101;
    private static final int PROVIDER_EXERCISES = 200;
    private static final int PROVIDER_EXERCISES_ID = 201;

    private TaiChiDBHelper mDBHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(TAI_CHI_LIST_AUTHORITY, PATH_CHI_HEADINGS_TABLE_NAME, PROVIDER_HEADINGS);
        sUriMatcher.addURI(TAI_CHI_LIST_AUTHORITY, PATH_CHI_HEADINGS_TABLE_NAME + "/#", PROVIDER_HEADINGS_ID);
        sUriMatcher.addURI(TAI_CHI_LIST_AUTHORITY, PATH_CHI_EXERCISES_TABLE_NAME, PROVIDER_EXERCISES);
        sUriMatcher.addURI(TAI_CHI_LIST_AUTHORITY, PATH_CHI_EXERCISES_TABLE_NAME + "/#", PROVIDER_EXERCISES_ID);
    }


    @Override
    public boolean onCreate() {
        mDBHelper = new TaiChiDBHelper(getContext());
        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        // Create or open a database to write to it
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Log.e(LOG_TAG, "Create or Open DB after getReadable");
        Cursor c;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case PROVIDER_HEADINGS:
                c = db.query(CHI_HEADINGS_TABLE, projection, selection, selectionArgs, null,
                        null, sortOrder);
                break;
            case PROVIDER_HEADINGS_ID:
                selection = _IDH + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                c = db.query(CHI_HEADINGS_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PROVIDER_EXERCISES:
                c = db.query(CHI_EXERCISES_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PROVIDER_EXERCISES_ID:
                selection = _IDX + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                c = db.query(CHI_EXERCISES_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI: " + uri);
        }

        // notify if the data at this URI changes, Then we need to update the cursor listener
        // attached is automatically notified with uri
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PROVIDER_HEADINGS:
                return CHI_HEADINGS_LIST_TYPE;
            case PROVIDER_HEADINGS_ID:
                return CHI_HEADINGS_ITEM_TYPE;
            case PROVIDER_EXERCISES:
                return CHI_EXERCISES_LIST_TYPE;
            case PROVIDER_EXERCISES_ID:
                return CHI_EXERCISES_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown Uri: " + uri + "with match: " + match);
        }
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final int match = sUriMatcher.match((uri));
        switch (match) {
            case PROVIDER_HEADINGS:
                return insertHeading(uri, values);
            case PROVIDER_EXERCISES:
                return insertExercise(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for: " + uri);
        }
    }


    // Insert a record into the Equipment Types table with the given content values. Return the new content uri
    // for that specific row in the database
    public Uri insertHeading(Uri uri, ContentValues values) {

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        long id = db.insert(CHI_HEADINGS_TABLE, null, values);
        Log.v(LOG_TAG, "Record not entered");
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // notify all listeners that the data has changed for the TSTUFF table
        getContext().getContentResolver().notifyChange(uri, null);
        // return the new Uri with the ID of the newly inserted row appended to the db
        return ContentUris.withAppendedId(uri, id);
    }


    // Insert a record into the records table with the given content values. Return the new content uri
    // for that specific row in the database
    public Uri insertExercise(Uri uri, ContentValues values) {

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        long id = db.insert(CHI_EXERCISES_TABLE, null, values);
        Log.v(LOG_TAG, "Record not entered");
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // notify all listeners that the data has changed for the TSTUFF table
        getContext().getContentResolver().notifyChange(uri, null);
        // return the new Uri with the ID of the newly inserted row appended to the db
        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // TODO verify cascade delete
        int rowsDeleted;
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case PROVIDER_HEADINGS_ID:
                selection = _IDH + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(CHI_HEADINGS_TABLE, selection, selectionArgs);
                break;
            case PROVIDER_EXERCISES_ID:
                selection = _IDX + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(CHI_EXERCISES_TABLE, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for: " + uri);
        }

        if (rowsDeleted != 0) {
            // Notify all listeners that the db has changed
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PROVIDER_HEADINGS:
                return updateHeadings(uri, values, selection, selectionArgs);
            case PROVIDER_HEADINGS_ID:
                selection = _IDH + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateHeadings(uri, values, selection, selectionArgs);
            case PROVIDER_EXERCISES:
                return updateExercises(uri, values, selection, selectionArgs);
            case PROVIDER_EXERCISES_ID:
                selection = _IDX + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateExercises(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for: " + uri);
        }
    }


    private int updateHeadings(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // if there are no values quit
        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int rows_updated = db.update(CHI_HEADINGS_TABLE, values, selection, selectionArgs);
        if (rows_updated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows_updated;
    }


    private int updateExercises(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // if there are no values quit
        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int rows_updated = db.update(CHI_EXERCISES_TABLE, values, selection, selectionArgs);
        if (rows_updated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows_updated;
    }


}
