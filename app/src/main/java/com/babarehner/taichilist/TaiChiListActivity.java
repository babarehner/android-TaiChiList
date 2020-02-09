package com.babarehner.taichilist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import com.babarehner.taichilist.adapters.HeaderCursorAdapter;
import com.babarehner.taichilist.data.TaiChiProvider;
import com.babarehner.taichilist.dialogs.HeaderDialogFrag;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import static com.babarehner.taichilist.Konstants.HeaderDialogType.ADD;
import static com.babarehner.taichilist.Konstants.HeaderDialogType.DELETE;
import static com.babarehner.taichilist.Konstants.HeaderDialogType.EDIT;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiHeadings.CHI_HEADINGS_URI;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiHeadings.C_CHI_HEADINGS;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiHeadings._IDH;
import static com.babarehner.taichilist.data.TaiChiListContract.PATH_CHI_HEADINGS_TABLE_NAME;
import static com.babarehner.taichilist.data.TaiChiListContract.TAI_CHI_LIST_AUTHORITY;

public class TaiChiListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
    HeaderCursorAdapter.RecyclerViewClickListener, PopupMenu.OnMenuItemClickListener,  HeaderDialogFrag.ColumnClickListener {

    private final String TAG = TaiChiListActivity.class.getSimpleName();

    private static final int HEADER_LOADER_ID = 1;

    private RecyclerView headerRecyclerView;
    HeaderCursorAdapter headerAdapter;

    private Uri mCurrentRecordUri;
    private long columnHeaderId;    // the header _ID that is to be updated or deleted
    private String columnHeaderStr; // the actual text of the header that is showing
    private int position; // the position in recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taichilist);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        mCurrentRecordUri = intent.getData();


        headerRecyclerView = findViewById(R.id.recycler_view_horizontal);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager layoutManagerHorizontal = new LinearLayoutManager(TaiChiListActivity.this, LinearLayoutManager.HORIZONTAL, false);
        headerRecyclerView.setLayoutManager(layoutManagerHorizontal);
        headerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        headerAdapter = new HeaderCursorAdapter(this);
        headerRecyclerView.setAdapter(headerAdapter);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        LoaderManager.getInstance(this).initLoader(HEADER_LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        Log.e(TAG, String.valueOf(CHI_HEADINGS_URI));

        //if (id == HEADER_LOADER_ID){
         //   Uri headerUri = CHI_HEADINGS_URI;

            String[] projection = {_IDH, C_CHI_HEADINGS};

            String selection = null;
            String[] selectionArgs = {};

        Log.e(TAG, "After projection: " + projection[1] + " None");

        return new CursorLoader(this,
                CHI_HEADINGS_URI,
                projection,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
  //      Log.e(LOG_TAG, String.valueOf(data));
        headerAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        headerAdapter.swapCursor(null);
    }

    //TODO Have addHeaderColumn go to new column header
    private void addHeaderColumn(String s){

        ContentValues values = new ContentValues();
        values.put(C_CHI_HEADINGS, s);

        Uri newUri = getContentResolver().insert(CHI_HEADINGS_URI, values);
        if (newUri == null){
            Toast.makeText(this, "Insert new column failed", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Insert new column succeeded", Toast.LENGTH_LONG).show();

            // gets position of header column that added the new column, not the column where the new header is
            Toast.makeText(this, "Position= " + position, Toast.LENGTH_LONG).show();
            headerRecyclerView.smoothScrollToPosition(position);
        }



    }


    //TODO Refactor deleteHeaderColumn and editHeaderColumn static helper class emthod????
    //TODO bring focus to new header column
    private void deleteHeaderColumn(long id){

        String strId = Long.toString(id);
        Toast.makeText(this, Long.toString(id),
                Toast.LENGTH_LONG).show();

        Uri.Builder builtUri = new Uri.Builder();
        builtUri.scheme("content")
                .authority(TAI_CHI_LIST_AUTHORITY)
                .appendPath(PATH_CHI_HEADINGS_TABLE_NAME)
                .appendPath(strId);

        Uri newUri = builtUri.build();

        int rowsDeleted = getContentResolver().delete(newUri, null, null);
        if (rowsDeleted == 0) {
            Toast.makeText(this, "Column header delete failed",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Column header deleted",
                    Toast.LENGTH_LONG).show();
        }
    }

    // TODO Check will need update currently dpends on DESC sortOrder to go to added Header
    private void editHeaderColumn(String s){

        ContentValues values = new ContentValues();
        values.put(C_CHI_HEADINGS, s);


        String strId = Long.toString(columnHeaderId);
        Uri.Builder builtUri = new Uri.Builder();
        builtUri.scheme("content")
                .authority(TAI_CHI_LIST_AUTHORITY)
                .appendPath(PATH_CHI_HEADINGS_TABLE_NAME)
                .appendPath(strId);

        Uri newUri = builtUri.build();

        int rowsAffected = getContentResolver().update(newUri, values, null, null);
        if (rowsAffected == 0) {
            Toast.makeText(this, "Column header update failed",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Column header updated",
                    Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onItemClick(int pos, long id) {
        Log.d(TAG, "id is : " + id);
       // Intent intent = new Intent(MainActivity.this, ScanPictureActivity.class);
        //Uri currentRecyclerUri = ContentUris.withAppendedId(WALLABY_URI, id);
        //intent.setData(currentRecyclerUri);
        //Toast.makeText(v.getContext(), "id: " + id, Toast.LENGTH_LONG).show();
        //Toast.makeText(v.getContext(), "Uri: " + currentRecyclerUri, Toast.LENGTH_LONG).show();
        //startActivity(intent);
    }

    @Override
    public void onImageClick(long id, int pos, View v, String columnHeader) {
        Log.d(TAG, "id is : " + id);
        columnHeaderId = id;  // get the _ID of the header column
        columnHeaderStr = columnHeader;
        position = pos;
        showPopupMenu(v);
    }

    public void showPopupMenu(View v ){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener( this);
        popup.inflate(R.menu.menu_popup);
        popup.show();
    }


    // TODO add 'column header value' string to text of edit column
    @Override
    public boolean onMenuItemClick(MenuItem item) {

        FragmentManager fm = getSupportFragmentManager();
        switch(item.getItemId()){
            case R.id.edit_column_name:
                Konstants.dialogType = EDIT;
                HeaderDialogFrag editColumnDialogFragment = HeaderDialogFrag.newInstance("Edit Column Name");
                editColumnDialogFragment.show(fm, columnHeaderStr); // pass column header to dialog
                return true;
            case R.id.add_column:
                Konstants.dialogType = ADD;
                HeaderDialogFrag addColumnDialogFragment = HeaderDialogFrag.newInstance("Add New Column");
                addColumnDialogFragment.show(fm, "fragment_delete_column");
                return true;
            case R.id.delete_column:
                Konstants.dialogType = DELETE;
                HeaderDialogFrag deleteColumnDialogFragment = HeaderDialogFrag.newInstance("Delete This Column");
                deleteColumnDialogFragment.show(fm, "fragment_delete_column");
                return true;
        }
        return false;
    }


    @Override
    public void onAddColumnPositiveClick(String s) { addHeaderColumn(s); }


    @Override
    public void onDeleteColumnPositiveClick() {
        deleteHeaderColumn(columnHeaderId);
    }


    @Override
    public void onEditColumnPositiveClick(String s) {
        editHeaderColumn(s);
    }
}
