package com.babarehner.taichilist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


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

public class HeaderDialogFrag extends DialogFragment {

    private TextView text1;
    private TextView text2;

    public HeaderDialogFrag() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static HeaderDialogFrag newInstance(String title) {
        HeaderDialogFrag frag = new HeaderDialogFrag();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.header_dialog_frag, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        text1 = view.findViewById(R.id.edit_column);
        // Fetch arguments from bundle and set title
        text2 = view.findViewById(R.id.add_column);
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);

    }
}
