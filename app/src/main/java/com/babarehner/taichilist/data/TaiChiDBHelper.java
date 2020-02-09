package com.babarehner.taichilist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.babarehner.taichilist.data.TaiChiListContract.ChiExercises;

import static com.babarehner.taichilist.data.TaiChiListContract.*;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiExercises.FK_CHI_HEADINGS;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiHeadings.CHI_HEADINGS_TABLE;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiHeadings.C_CHI_HEADINGS;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiHeadings.C_HEADINGS_SORT_ORDER;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiHeadings._IDH;


/**
 * Project Name: Tai Chi List
 * <p>
 * Copyright 1/10/20 by Mike Rehner
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

public class TaiChiDBHelper extends SQLiteOpenHelper {

    private final String LOG_TAG = TaiChiDBHelper.class.getSimpleName();

    // To allow for changes in DB versioning and keeping user data
    private static final int DB_VERSION = 1;

    static final String DB_NAME = "TaiChiExerciseList.db";

    public TaiChiDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_CHI_HEADINGS_TABLE = "CREATE TABLE " +
                CHI_HEADINGS_TABLE + " (" +
                _IDH + " INTEGER PRIMARY KEY, " +
                C_CHI_HEADINGS + " TEXT NOT NULL, "  +
                C_HEADINGS_SORT_ORDER + " INTEGER);";

        Log.v(LOG_TAG, "xyzxyzxyz");
        Log.v(LOG_TAG, SQL_CREATE_CHI_HEADINGS_TABLE);

        sqLiteDatabase.execSQL(SQL_CREATE_CHI_HEADINGS_TABLE);


        // TODO remove initialization and add clickable link in program to enter headings
        // TODO add sortorder field to table
        String[] headings = {"Click + to add/edit heading"};
        for (String each : headings) {
            sqLiteDatabase.execSQL("INSERT INTO " + CHI_HEADINGS_TABLE
                    + " ( " + C_CHI_HEADINGS + " ) "
                    + " VALUES( "
                    + "'" + each + "'" + ");");
        }


        final String SQL_CREATE_CHI_EXERCISES_TABLE = "CREATE TABLE " +
                ChiExercises.CHI_EXERCISES_TABLE + " (" +
                ChiExercises._IDX + " INTEGER PRIMARY KEY, " +
                FK_CHI_HEADINGS + " INTEGER NOT NULL, " +

                ChiExercises.C_X_NAME + " TEXT , " +
                ChiExercises.C_SORT_ORDER + " INTEGER, " +
                ChiExercises.C_FILE_NAME + " TEXT, " +
                ChiExercises.C_DATE + " TEXT, " +        // Store date as text value
                ChiExercises.C_NOTES + " TEXT );";
                //Create Foreign Key Constraint - need to double check syntax
               //"FOREIGN KEY (" + FK_CHI_HEADINGS + ") REFERENCES " + CHI_HEADINGS_TABLE +
               // " ( " + _IDH + " ) ); " ;

        sqLiteDatabase.execSQL(SQL_CREATE_CHI_EXERCISES_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number of your application.
        // Currently the next line wipes out all user data and starts with a fresh DB Table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ChiExercises.CHI_EXERCISES_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CHI_HEADINGS_TABLE);
        onCreate(sqLiteDatabase);
    }

}
