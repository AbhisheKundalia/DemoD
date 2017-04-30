package com.awiserk.kundalias.demo2.data;

/**
 * Created by akundalia on 4/30/2017.
 */

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * {@link ContentProvider} for Item app.
 */
public class ItemProvider extends ContentProvider {

    /** URI matcher code for the content URI for a ring sizes in the allsizes table */
    private static final int RINGS_ALLSIZES = 100;
    
    /** URI matcher code for the content URI for the rings table */
    private static final int RINGS = 101;

    /** URI matcher code for the content URI for a single ring in the rings table */
    private static final int RING_ID = 102;

    /** URI matcher code for the content URI for a bangle sizes in the allsizes table */
    private static final int BANGLES_ALLSIZES = 200;

    /** URI matcher code for the content URI for the bangles table */
    private static final int BANGLES = 201;

    /** URI matcher code for the content URI for a single bangle in the bangles table */
    private static final int BANGLE_ID = 202;

    /** URI matcher code for the content URI for a chain sizes in the allsizes table */
    private static final int CHAINS_ALLSIZES = 300;

    /** URI matcher code for the content URI for the chains table */
    private static final int CHAINS = 301;

    /** URI matcher code for the content URI for a single chain in the chains table */
    private static final int CHAIN_ID = 302;

    /** URI matcher code for the content URI for a necklace sizes in the allsizes table */
    private static final int NECKLACES_ALLSIZES = 400;

    /** URI matcher code for the content URI for the necklaces table */
    private static final int NECKLACES = 401;

    /** URI matcher code for the content URI for a single necklace in the necklaces table */
    private static final int NECKLACE_ID = 402;

    

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        /**
         *  add URI as :com.awiserk.kundalias.items/rings
         */
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_RINGS, RINGS);
        
        /**
         *  add URI as :com.awiserk.kundalias.items/rings/*
         *  * for unique id to be string
         */
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_RINGS + "/*", RING_ID);
        
        /**
         *  add URI as :com.awiserk.kundalias.items/allsizes/rings
         */
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_ALL_SIZES + "/" + ItemContract.PATH_RINGS,RINGS);

        /**
         *  add URI as :com.awiserk.kundalias.items/bangles
         */
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_BANGLES, BANGLES);
        
        /**
         *  add URI as :com.awiserk.kundalias.items/bangles/*
         *  * for unique id to be string
         */
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_BANGLES + "/*", BANGLE_ID);
        
        /**
         *  add URI as :com.awiserk.kundalias.items/allsizes/bangles
         */
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_ALL_SIZES + "/" + ItemContract.PATH_BANGLES,BANGLES);

        /**
         *  add URI as :com.awiserk.kundalias.items/chains
         */
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_CHAINS, CHAINS);

        /**
         *  add URI as :com.awiserk.kundalias.items/chains/*
         *  * for unique id to be string
         */
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_CHAINS + "/*", CHAIN_ID);

        /**
         *  add URI as :com.awiserk.kundalias.items/allsizes/chains
         */
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_ALL_SIZES + "/" + ItemContract.PATH_CHAINS,CHAINS);


        /**
         *  add URI as :com.awiserk.kundalias.items/necklaces
         */
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_NECKLACES, NECKLACES);

        /**
         *  add URI as :com.awiserk.kundalias.items/necklaces/*
         *  * for unique id to be string
         */
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_NECKLACES + "/*", NECKLACE_ID);

        /**
         *  add URI as :com.awiserk.kundalias.items/allsizes/necklaces
         */
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_ALL_SIZES + "/" + ItemContract.PATH_NECKLACES,NECKLACES);



    }

    /** Database helper object */
    //private PetDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
       // mDbHelper = new PetDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}