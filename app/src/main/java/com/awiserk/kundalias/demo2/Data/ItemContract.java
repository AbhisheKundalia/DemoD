/*
package com.awiserk.kundalias.demo2.Data;

import android.net.Uri;
import android.content.ContentResolver;
import android.provider.BaseColumns;

*/
/**
 * API Contract for the Items app.
 *//*


public final class ItemContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ItemContract(){}

    */
/**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     *//*

    public static final String CONTENT_AUTHORITY = "com.awiserk.kundalias.items";

    */
/**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     *//*

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    */
/**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.awiserk.kundalias.items/rings/ is a valid path for
     * looking at ring data. content://com.example.android.items/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     *//*

    public static final String PATH_RINGS = "rings";

    public static final String PATH_BANGLES = "bangles";

    public static final String PATH_CHAINS = "chains";

    public static final String PATH_NECKLACE = "necklace";

    */
/**
     * Inner class that defines constant values for the items database table.
     * Each entry in the table represents a single item.
     *//*

    public static final class ItemEntry implements BaseColumns {

        */
/** The content URI to access the ring data in the provider *//*

        public static final Uri RINGS_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_RINGS);

        */
/** The content URI to access the bangle data in the provider *//*

        public static final Uri BANGLES_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BANGLES);

        */
/** The content URI to access the chains data in the provider *//*

        public static final Uri CHAINS_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CHAINS);

        */
/** The content URI to access the necklace data in the provider *//*

        public static final Uri NECKLACE_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_NECKLACE);


        */
/**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         *//*

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        */
/**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         *//*

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        */
/** Name of database table for pets *//*

        public final static String TABLE_NAME = "pets";

        */
/**
         * Unique ID number for the pet (only for use in the database table).
         *
         * Type: INTEGER
         *//*

        public final static String _ID = BaseColumns._ID;

        */
/**
         * Name of the pet.
         *
         * Type: TEXT
         *//*

        public final static String COLUMN_PET_NAME ="name";

        */
/**
         * Breed of the pet.
         *
         * Type: TEXT
         *//*

        public final static String COLUMN_PET_BREED = "breed";

        */
/**
         * Gender of the pet.
         *
         * The only possible values are {@link #GENDER_UNKNOWN}, {@link #GENDER_MALE},
         * or {@link #GENDER_FEMALE}.
         *
         * Type: INTEGER
         *//*

        public final static String COLUMN_PET_GENDER = "gender";

        */
/**
         * Weight of the pet.
         *
         * Type: INTEGER
         *//*

        public final static String COLUMN_PET_WEIGHT = "weight";

        */
/**
         * Possible values for the gender of the pet.
         *//*

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        */
/**
         * Returns whether or not the given gender is {@link #GENDER_UNKNOWN}, {@link #GENDER_MALE},
         * or {@link #GENDER_FEMALE}.
         *//*

        public static boolean isValidGender(int gender) {
            if (gender == GENDER_UNKNOWN || gender == GENDER_MALE || gender == GENDER_FEMALE) {
                return true;
            }
            return false;
        }
    }


}









*/
