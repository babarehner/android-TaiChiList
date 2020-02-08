package com.babarehner.taichilist.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.babarehner.taichilist.Konstants;
import com.babarehner.taichilist.R;

import static com.babarehner.taichilist.Konstants.HeaderDialogType.ADD;
import static com.babarehner.taichilist.Konstants.HeaderDialogType.DELETE;
import static com.babarehner.taichilist.Konstants.HeaderDialogType.EDIT;
import static com.babarehner.taichilist.R.layout.header_dialog;


/**
 * Project Name: Tai Chi List
 * <p>
 * Copyright 2/6/20 by Mike Rehner
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

    public interface ColumnClickListener{
        void onEditColumnPositiveClick(String s);
        void onAddColumnPositiveClick(String s);
        void onDeleteColumnPositiveClick();
    }


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
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String positiveButton = "";

        String title = getArguments().getString("title");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);

        // Edit or Add Column from xml file header_dialog.xml
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(header_dialog, null);
        final EditText input = dialogView.findViewById(R.id.column_name);
        if ((Konstants.dialogType == EDIT) || (Konstants.dialogType == ADD)) {
            alertDialogBuilder.setView(dialogView);
            positiveButton = "OK";

            if (Konstants.dialogType == EDIT){
                input.setText(getTag());    // Column header passed in through Tag
                input.setSelection(input.getText().length());   // place cursor at end of text
            }
            if (Konstants.dialogType == ADD){
                input.setHint("Click here to add column");
            }
        }

        // Delete Column programmatically
        final TextView tv = new TextView(getContext());
        if (Konstants.dialogType == DELETE){
            alertDialogBuilder.setMessage("\n Click DELETE to delete this column");
            positiveButton = "Delete";
        }

        final ColumnClickListener listener = (ColumnClickListener) getContext();

        alertDialogBuilder.setPositiveButton(positiveButton,  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (Konstants.dialogType){
                    case EDIT:
                        String s = input.getText().toString();
                        listener.onEditColumnPositiveClick(s);
                        break;
                    case ADD:
                        String s2 = input.getText().toString();
                        listener.onAddColumnPositiveClick(s2);
                        break;
                    case DELETE:
                        listener.onDeleteColumnPositiveClick();
                        break;
                    default:
                        Toast.makeText(getContext(), "oops, something failed in the dialogue fragment",
                                Toast.LENGTH_LONG).show();
                }

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



