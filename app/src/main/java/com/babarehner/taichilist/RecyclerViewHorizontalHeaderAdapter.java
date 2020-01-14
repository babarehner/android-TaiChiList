package com.babarehner.taichilist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


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

public class RecyclerViewHorizontalHeaderAdapter extends RecyclerView.Adapter<RecyclerViewHorizontalHeaderAdapter.HeaderViewHolderX> {

    private List<ExerciseHeader>  horizontalHeaderList;
    Context context;

    public RecyclerViewHorizontalHeaderAdapter(List<ExerciseHeader> horizontalHeaderList, Context context){
        this.horizontalHeaderList = horizontalHeaderList;
        this.context = context;
    }


    @NonNull
    @Override
    public HeaderViewHolderX onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the layout file
        View headerExerciseView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_horizontal_item, parent, false);
        HeaderViewHolderX hvh = new HeaderViewHolderX(headerExerciseView);
        return hvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHorizontalHeaderAdapter.HeaderViewHolderX holder, final int position) {
        holder.txtview.setText(horizontalHeaderList.get(position).getExerciseHeader());
        holder.txtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String header = horizontalHeaderList.get(position).getExerciseHeader().toString();
                Toast.makeText(context, header + " is selected", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return horizontalHeaderList.size();
    }

    public class HeaderViewHolderX extends RecyclerView.ViewHolder {
        TextView txtview;

        public HeaderViewHolderX(View view) {
            super(view);
            txtview = view.findViewById(R.id.idHeader);
        }
    }
}
