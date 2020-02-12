package com.babarehner.taichilist;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import static com.babarehner.taichilist.data.TaiChiListContract.ChiExercises.C_FILE_NAME;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiExercises.C_SORT_ORDER;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiExercises.C_X_NAME;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiExercises.FK_CHI_HEADINGS;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiExercises._IDX;


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

    private EditText mEditText;
    private ImageView mImageViewDirection;  // The cover image that goes over the bagua

    private boolean mHomeChecked; //Back button

    // Touch listener to check if changes made to a record
    private boolean mExerciseChanged = false;
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event){
            mExerciseChanged = true;
            return false;
        }
    };



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

        mEditText = findViewById(R.id.editExercise);
        mImageViewDirection = findViewById(R.id.imageView2);

        mEditText.setOnTouchListener(mTouchListener);
        mImageViewDirection.setOnTouchListener(mTouchListener);




        // LoaderManager.getInstance(AddEditExerciseActivity.this).initLoader(LOADER_EXERCISE, null, AddEditExerciseActivity.this);

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection = {_IDX,
                FK_CHI_HEADINGS,
                C_X_NAME,
                C_SORT_ORDER,
                C_FILE_NAME};

        Loader<Cursor> loader = new CursorLoader(this, mCurrentExercisedUri, projection, null,
                null, C_SORT_ORDER + " ASC");
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor c) {

        if (c.moveToFirst()){
            long foreignKeyChiHeading = c.getLong(c.getColumnIndex(FK_CHI_HEADINGS));
            String exercise = c.getString(c.getColumnIndex(C_X_NAME));
            int sortOrder = c.getInt(c.getColumnIndex(C_SORT_ORDER));
            String directionFile = c.getString((c.getColumnIndex(C_FILE_NAME)));
        }



    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mEditText.setText("");

    }


    @Override   // inflate the menu.xml file
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_edit_exercise, menu);
        return true;
    }


    @Override   // hide delete menu items when adding a new exercise
    public boolean onPrepareOptionsMenu(Menu m) {
        super.onPrepareOptionsMenu(m);
        // if this is add an exercise, hide "delete" menu item

        if (mCurrentExercisedUri == null) {
            MenuItem deleteItem = m.findItem(R.id.action_delete);
            deleteItem.setVisible(false);
        }
        return true;
    }


    @Override   // handle menu item choices
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_save:
                //saveExercise();
                finish();
                return true;
            case R.id.action_delete:
                //showDeleteConfirmationDialogFrag();
            case android.R.id.home:
                // exercise has not changed
                if (!mExerciseChanged) {
                    NavUtils.navigateUpFromSameTask((AddEditExerciseActivity.this));
                    return true;
                }
                mHomeChecked = true;
                // showUnsavedChangesDialogFragment();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setDirection(View v){

        ImageView imageViewDirection = findViewById(R.id.imageView2);
        imageViewDirection.setImageResource(0); // set to none

        switch (v.getId()){
            case (R.id.imageViewN) :
                imageViewDirection.setImageResource(R.drawable.ic_arrow_north_24dp);
                break;
            case (R.id.imageViewNE):
                imageViewDirection.setImageResource(R.drawable.ic_arrow_northeast_24dp);
                break;
            case (R.id.imageViewE):
                imageViewDirection.setImageResource(R.drawable.ic_arrow_east_24dp);
                break;
            case (R.id.imageViewSE):
                imageViewDirection.setImageResource(R.drawable.ic_arrow_southeast_24dp);
                break;
            case (R.id.imageViewS):
                imageViewDirection.setImageResource(R.drawable.ic_arrow_south_24dp);
                break;
            case (R.id.imageViewSW):
                imageViewDirection.setImageResource(R.drawable.ic_arrow_southwest_24dp);
                break;
            case (R.id.imageViewW):
                imageViewDirection.setImageResource(R.drawable.ic_arrow_west_24dp);
                break;
            case (R.id.imageViewNW):
                imageViewDirection.setImageResource(R.drawable.ic_arrow_northwest_24dp);
                break;
            default:
                Toast.makeText(this, LOG_TAG + " Unable to get Direction Arrow",
                        Toast.LENGTH_LONG).show();
                break;
        }

    }

}

