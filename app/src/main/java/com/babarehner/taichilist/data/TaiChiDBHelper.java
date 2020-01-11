package com.babarehner.taichilist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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

    // To allow for changes in DB versioning and keeping user data
    private static final int DB_VERSION = 1;

    static final String DB_NAME = "TaiChiExerciseList.db";

    public TaiChiDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number of your application.
        // Currently the next line wipes out all user data and starts with a fresh DB Table
    }
}
