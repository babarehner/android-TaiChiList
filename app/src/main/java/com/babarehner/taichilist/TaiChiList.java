package com.babarehner.taichilist;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import static com.babarehner.taichilist.data.TaiChiListContract.ChiHeadings.CHI_HEADINGS_URI;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiHeadings.C_CHI_HEADINGS;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiHeadings._IDH;

public class TaiChiList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
    HeaderCursorAdapter.RecyclerViewClickListener {

    private static final int HEADER_LOADER_ID = 1;

    private RecyclerView headerRecyclerView;
    HeaderCursorAdapter headerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        headerRecyclerView = findViewById(R.id.recycler_view_horizontal);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager layoutManagerHorizontal = new LinearLayoutManager(TaiChiList.this, LinearLayoutManager.HORIZONTAL, false);
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

        LoaderManager.getInstance(TaiChiList.this).initLoader(HEADER_LOADER_ID, null, TaiChiList.this);
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
        //if (id == HEADER_LOADER_ID){
         //   Uri headerUri = CHI_HEADINGS_URI;

            String[] projection = {_IDH, C_CHI_HEADINGS};
            String selection = null;
            String[] selectionArgs = {};
            String sortOrder = null;

            return new CursorLoader(getApplicationContext(),
                    CHI_HEADINGS_URI,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder);
        //}
        // return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        headerAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        headerAdapter.swapCursor(null);
    }


    @Override
    public void onItemClick(int pos, long id) {
       // Intent intent = new Intent(MainActivity.this, ScanPictureActivity.class);
        //Uri currentRecyclerUri = ContentUris.withAppendedId(WALLABY_URI, id);
        //intent.setData(currentRecyclerUri);
        //Toast.makeText(v.getContext(), "id: " + id, Toast.LENGTH_LONG).show();
        //Toast.makeText(v.getContext(), "Uri: " + currentRecyclerUri, Toast.LENGTH_LONG).show();
        //startActivity(intent);
    }
}
