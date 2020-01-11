package com.babarehner.taichilist.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Project Name: Tai Chi List
 * <p>
 * Copyright 1/10/20 by Mike Rehner
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

public class TaiChiListContract {

    // To prevent someone from accidentally instantiating the contract class
    private TaiChiListContract() { }

    // use package name for convenience for the Content Authority
    public static final String TAI_CHI_LIST_AUTHORITY = "com.babarehner.taichilist";

    // Use TaiChiList_Authority to create the base of all Uri's which apps use
    // to contact the content provider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" +
            TAI_CHI_LIST_AUTHORITY);
    public static final String PATH_CHI_EXERCISES_TABLE_NAME = "TChiExercises";
    public static final String PATH_CHI_HEADINGS_TABLE_NAME ="TChiHeadings";


    // Inner class that defines Chi Headings table and columns
    public static final class ChiHeadings implements BaseColumns {

        // MIME type of the (@link #CONTENT_URI for a TChiHeadings database table
        public static final String CHI_HEADINGS_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + TAI_CHI_LIST_AUTHORITY + "/" + PATH_CHI_HEADINGS_TABLE_NAME;
        // MIME type of the (@link #CONTENT_URI for a single record
        public static final String CHI_HEADINGS_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + TAI_CHI_LIST_AUTHORITY + "/" + PATH_CHI_HEADINGS_TABLE_NAME;
        // Content URI to access the table data in the provider
        public static final Uri CHI_HEADINGS_URI = Uri.withAppendedPath(BASE_CONTENT_URI,
                PATH_CHI_HEADINGS_TABLE_NAME);

        public static final String CHI_HEADING_TABLE_NAME = "TChiHeadings";

        // the globals for the columns in the TChiHeading table
        public static final String _IDH = BaseColumns._ID;
        public static final String C_CHI_HEADINGS = "CHeading";
    }


    // Inner class that defines Chi Exercises table and columns
    public static final class MachineEntry implements BaseColumns {

        // MIME type of the (@link #CONTENT_URI for a Chi Exercises database table
        public static final String CHI_HEADINGS_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + TAI_CHI_LIST_AUTHORITY + "/" + PATH_CHI_EXERCISES_TABLE_NAME;
        // MIME type of the (@link #CONTENT_URI for a single record
        public static final String CHI_HEADINGS_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + TAI_CHI_LIST_AUTHORITY + "/" + PATH_CHI_EXERCISES_TABLE_NAME;
        // Content URI to access the table data in the provider
        public static final Uri CHI_HEADINGS_URI = Uri.withAppendedPath(BASE_CONTENT_URI,
                PATH_CHI_EXERCISES_TABLE_NAME);

        public static final String CHI_EXERCISES_TABLE_NAME = "TChiExercises";

        //the globals for the columns in the TChiExercises table
        public static final String _IDX = BaseColumns._ID;
        public static final String FK_CHI_HEADINGS = "FKXHeadings";
        public static final String C_X_NAME = "CXName";
        public static final String C_SORT_ORDER = "CSortOrder";
        public static final String C_FILE_NAME = "CFileName";
        public static final String C_DATE = "CDate";
        public static final String C_NOTES = "CNotes";
    }

}
