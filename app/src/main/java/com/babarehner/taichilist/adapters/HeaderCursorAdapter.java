package com.babarehner.taichilist.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.babarehner.taichilist.R;

import static com.babarehner.taichilist.data.TaiChiListContract.ChiHeadings.C_CHI_HEADINGS;
import static com.babarehner.taichilist.data.TaiChiListContract.ChiHeadings._IDH;

/**
 * Project Name: Tai Chi List
 * <p>
 * Copyright 1/13/20 by Mike Rehner
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

public class HeaderCursorAdapter extends BaseCursorAdapter<HeaderCursorAdapter.HeaderViewHolder> {

    public static final String TAG = "HeaderCursorAdapter";

    // Use interface- implemented in MainActivity
    public interface RecyclerViewClickListener{
        void onItemClick(int pos, long id);
        void onImageClick(long id, int pos,  View v, String columnHeader);
    }

    private Context mContext;
    private RecyclerViewClickListener mListener;
    private long mRowIdl;
    private ImageView mImageView;
    private int pos;

    private String headerString;  // holds current headercolumn for passthrough to HeaderDialogFrag

    public HeaderCursorAdapter(RecyclerViewClickListener listener){
        super(null);
        this.mListener = listener;
    }


    @Override
    @NonNull
    public HeaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View formNameView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_horizontal_item, parent, false);
        mContext = parent.getContext();
        return new HeaderViewHolder(formNameView, mListener);
    }


    @Override
    public void onBindViewHolder(HeaderViewHolder holder, Cursor cursor) {

        int columnIndex_ID = cursor.getColumnIndex(_IDH);
        int mColumnIndexName = cursor.getColumnIndex(C_CHI_HEADINGS);

        String header = cursor.getString(mColumnIndexName);

        holder.rowID = cursor.getLong(columnIndex_ID);
        holder.headerTextView.setText(header);

    }


    @Override
    public void swapCursor(Cursor newCursor) {
        super.swapCursor(newCursor);
    }


    class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener listener;
        private TextView headerTextView;
        private long rowID;
        private String headerString;


        HeaderViewHolder(View v, RecyclerViewClickListener listener){
            super(v);

            headerTextView = itemView.findViewById(R.id.idHeader);
            mImageView = itemView.findViewById(R.id.idAddEdit);

            this.listener = listener;

            // set the click listener to the header
            headerTextView.setOnClickListener(this);
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.idAddEdit:
                    if (listener != null) {
                        int position = getAdapterPosition();
                        // int position = getLayoutPosition();
                        // get current string and pass to HeaderDialogFrag
                        headerString = headerTextView.getText().toString();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onImageClick(rowID, position,  view, headerString);
                        }
                    }
                    break;
                case R.id.idHeader:
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position, rowID);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }




}
