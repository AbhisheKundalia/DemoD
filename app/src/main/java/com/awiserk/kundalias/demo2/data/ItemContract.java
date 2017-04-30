package com.awiserk.kundalias.demo2.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * API Contract for the Pets app.
 */
public final class ItemContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ItemContract(){}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.awiserk.kundalias.items";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.awiserk.kundalias.items/rings/ is a valid path for
     * looking at ring data. content://com.example.android.items/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_ALL_SIZES = "allsizes";

    public static final String PATH_RINGS = "rings";

    public static final String PATH_BANGLES = "bangles";

    public static final String PATH_CHAINS = "chains";

    public static final String PATH_NECKLACES = "necklaces";

    /**
     * Inner class that defines constant values for the items database table.
     * Each entry in the table represents a single item.
     */
    public static final class ItemEntry implements BaseColumns {

        /** The content URI to access the All sizes data in the provider */
        public static final Uri ALL_SIZES_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ALL_SIZES);

        /** The content URI to access the ring data in the provider */
        public static final Uri RINGS_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_RINGS);

        /** The content URI to access the ring sizes data in the provider */
        public static final Uri RINGS_ALL_SIZES_CONTENT_URI = Uri.withAppendedPath(ALL_SIZES_CONTENT_URI, PATH_RINGS);

        /** The content URI to access the bangle data in the provider */
        public static final Uri BANGLES_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BANGLES);

        /** The content URI to access the ring sizes data in the provider */
        public static final Uri BANGLES_ALL_SIZES_CONTENT_URI = Uri.withAppendedPath(ALL_SIZES_CONTENT_URI, PATH_BANGLES);

        /** The content URI to access the chains data in the provider */
        public static final Uri CHAINS_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CHAINS);

        /** The content URI to access the ring sizes data in the provider */
        public static final Uri CHAINS_ALL_SIZES_CONTENT_URI = Uri.withAppendedPath(ALL_SIZES_CONTENT_URI, PATH_CHAINS);

        /** The content URI to access the necklace data in the provider */
        public static final Uri NECKLACE_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_NECKLACES);

        /** The content URI to access the ring sizes data in the provider */
        public static final Uri NECKLACE_ALL_SIZES_CONTENT_URI = Uri.withAppendedPath(ALL_SIZES_CONTENT_URI, PATH_NECKLACES);



        /** Name of database table for ALL SIZES */
        public final static String ALLSIZES_TABLE_NAME = "allsizes";

        /** Name of database table for rings */
        public final static String RINGS_TABLE_NAME = "rings";

        /** Name of database table for bangles */
        public final static String BANGLES_TABLE_NAME = "bangles";

        /** Name of database table for chains */
        public final static String CHAINS_TABLE_NAME = "chains";

        /** Name of database table for rings */
        public final static String NECKLACE_TABLE_NAME = "necklace";


        /**
         * Unique ID number for the item (only for use in the database table).
         *
         * Type: String
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * ID of the Item manually entered by the owner.
         *
         * Type: TEXT
         */
        public final static String COLUMN_ITEM_ID="id";

        /**
         * Price of the item.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_ITEM_PRICE = "price";

        /**
         * Available Sizes of the item.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_ITEM_AVAILABLE_SIZES = "sizes";

        /**
         * Image of the ITEM.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_ITEM_IMAGE = "image";

    }

}
