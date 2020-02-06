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

public class HeaderAddDialogFrag extends DialogFragment {

    public interface AddColumnClickListener{
        void onAddColumnPositiveClick(String s);
    }


    public HeaderAddDialogFrag() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static HeaderAddDialogFrag newInstance(String title) {
        HeaderAddDialogFrag frag = new HeaderAddDialogFrag();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        String title = getArguments().getString("title");
        alertDialogBuilder.setTitle(title);

        final EditText input= new EditText(getContext());
        input.setHint("Add Column");
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        alertDialogBuilder.setView(input);

        final AddColumnClickListener listener = (AddColumnClickListener) getContext();

        alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s = input.getText().toString();
                listener.onAddColumnPositiveClick(s);
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
