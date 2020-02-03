package com.babarehner.taichilist.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.babarehner.taichilist.R;


/**
 * Project Name: Tai Chi List
 * <p>
 * Copyright 2/3/20 by Mike Rehner
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

public class VerticalCursorAdapter extends BaseCursorAdapter<VerticalCursorAdapter.VerticalViewHolder> {

    public static final String TAG = "HeaderCursorAdapter";

    private Context mContext;
    private HeaderCursorAdapter.RecyclerViewClickListener mListener;
    private long mRowIdl;


    public VerticalCursorAdapter(HeaderCursorAdapter.RecyclerViewClickListener listener) {
        super(null);
        this.mListener = listener;
    }


    @Override
    public void onBindViewHolder(VerticalCursorAdapter.VerticalViewHolder holder, Cursor cursor) {

    }

    @NonNull
    @Override
    public VerticalCursorAdapter.VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public void swapCursor(Cursor newCursor) {
        super.swapCursor(newCursor);
    }


    class VerticalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private HeaderCursorAdapter.RecyclerViewClickListener listener;
        TextView headerTextView;
        long rowID;

        VerticalViewHolder(View v, HeaderCursorAdapter.RecyclerViewClickListener listener) {
            super(v);

            headerTextView = itemView.findViewById(R.id.idHeader);

            this.listener = listener;

            // set the click listener to the header
            headerTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
