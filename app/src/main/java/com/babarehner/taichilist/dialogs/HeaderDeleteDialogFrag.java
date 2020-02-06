package com.babarehner.taichilist.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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

public class HeaderDeleteDialogFrag extends DialogFragment {

    // although interface in Java is always public, Android Studio seems a little confused about this
    public interface DeleteColumnClickListener{
        void onDeleteColumnPositiveClick();
    }



    public HeaderDeleteDialogFrag() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static HeaderDeleteDialogFrag newInstance(String title) {
        HeaderDeleteDialogFrag frag = new HeaderDeleteDialogFrag();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String title = getArguments().getString("title");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);

        final TextView tv = new TextView(getContext());
        tv.setPadding(16,16,16,16);
        tv.setText("\n" + "Click OK to delete this column");
        alertDialogBuilder.setView(tv);

        final DeleteColumnClickListener listener = (DeleteColumnClickListener) getContext();

        alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onDeleteColumnPositiveClick();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null && ((Dialog) dialog).isShowing()) {
                    dialog.dismiss();
                }
            }

        });

        return alertDialogBuilder.create();
    }
}
