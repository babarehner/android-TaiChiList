package com.babarehner.taichilist.data;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.babarehner.taichilist.R;


/**
 * Project Name: Tai Chi List
 * <p>
 * Copyright 2/9/20 by Mike Rehner
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

public class AddEditExerciseActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG = AddEditExerciseActivity.class.getSimpleName();

    public static final int LOADER_EXERCISE = 2;
    private Uri mCurrentExercisedUri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_exercise);

        Intent intent = getIntent();
        mCurrentExercisedUri = intent.getData();

        if (mCurrentExercisedUri == null) {
            // set pager header to add record
            setTitle(getString(R.string.add_exercise));
            invalidateOptionsMenu();
        } else {
            setTitle(getString(R.string.edit_exercise));
        }

        // LoaderManager.getInstance(AddEditExerciseActivity.this).initLoader(LOADER_EXERCISE, null, AddEditExerciseActivity.this);

    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}

