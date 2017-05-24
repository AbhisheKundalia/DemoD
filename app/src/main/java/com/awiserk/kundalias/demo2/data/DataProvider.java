package com.awiserk.kundalias.demo2.data;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.awiserk.kundalias.demo2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class DataProvider {



    /**
     * An array of sample (dummy) items.
     */
    public static final List<Item> ITEMS = new ArrayList<Item>();
    /**
     * Possible values for the categories of the item.
     */
    public static final int CATEGORY_UNKNOWN = 0;
    public static final int CATEGORY_1 = 1;
    public static final int CATEGORY_2 = 2;
    public static final int CATEGORY_3 = 3;
    public static final int CATEGORY_4 = 4;
    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "https://scontent.fblr2-1.fna.fbcdn.net/v/t31.0-8/s960x960/17635235_103116343569649_6671627571124085145_o.jpg?oh=1db848d5f072c71d5c4db6db823f17c6&oe=59834384";
    private static final String urlProfileImg = "http://www.phpbb-es.com/foro/styles/FLATBOOTS/theme/images/user4.png";
    private static final String userName = "Abhishek Kundalia";
    private static final String userEmail = "abhishekundalia@gmail.com";
    private static final String cat1CoverImg = "https://pixabay.com/get/e83db1062afc073ed1534705fb0938c9bd22ffd41db8154091f3c87da3/rings-1809286_1920.jpg";
    private static final String cat2CoverImg = "http://i.huffpost.com/gen/857659/images/o-FABULOUS-FINDS-APPRAISING-GOLD-JEWELRY-facebook.jpg";
    private static final String cat3CoverImg = "https://lh3.googleusercontent.com/TtI6JoY58rWbxfsHeycrJEIM2nNGK6GySQhZIUU5FtNyd-TasX9xwcmhEqBCOuPW7e0=h1264";
    private static final String cat4CoverImg = "https://theamplifierfoundation.org/site/wp-content/uploads/2017/02/OurAncestorsDreamedOfUs26x50.jpg";
    private static final int COUNT = 10;
    private static int[] covers = new int[]{
            R.drawable.album9,
            R.drawable.album10,
            R.drawable.album11,
            R.drawable.album1,
            R.drawable.album2,
            R.drawable.album3,
            R.drawable.album4,
            R.drawable.album5,
            R.drawable.album6,
            R.drawable.album7,
            R.drawable.album8,
    };
    public static String[] ringsSizes = new String[]{"xl", "abc", "asd", "adf"};

    public static String[] banglesSizes = new String[]{"xjbl", "abc", "asd", "adf", "asdf", "efw23"};

    public static String[] chainsSizes = new String[]{"xl", "abc", "asd", "adf", "asewe2", "q"};

    public static String[] necklaceSizes = new String[]{"xl", "abc", "asewe2", "q"};


    private static FirebaseDatabase mFirebaseDatabase;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private int mPageEndOffset;
    private int mPageLimit;
    private String tag = "DataProvider";


    public static String getCatCoverImg(int index) {
        switch (index) {
            case 0:
                // Category1
                return cat1CoverImg;
            case 1:
                // Category2
                return cat2CoverImg;
            case 2:
                // Category3
                return cat3CoverImg;
            case 3:
                // Category4
                return cat4CoverImg;
            default:
                return cat3CoverImg;
        }
    }

    public static String getUserName() {
        return userName;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static String getUrlNavHeaderBg() {
        return urlNavHeaderBg;
    }

    public static String getUrlProfileImg() {
        return urlProfileImg;
    }

    private static void addItem(Item item) {
        ITEMS.add(item);
    }
    public static Uri getUrl(int res){
        return Uri.parse("android.resource://com.awiserk.kundalias.demo2/" + res);
    }
    private static Item createDummyItem(int position) {
        List<String> necklaceSizes = new ArrayList<String>();
        necklaceSizes.add("asdf");
        necklaceSizes.add("abc");
        return new Item("TestCategory", String.valueOf(position), getUrl(covers[position]).toString(),(int) (position * 1000000 + 7), necklaceSizes);
    }

    public static List<Item> getITEMS(String category) {
        return ITEMS;
    }
    public static void initFirebase() {
        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    private void getItem(String category) {

        mPageEndOffset = 0;
        mPageLimit = 10;


        mPageEndOffset += mPageLimit;

        Query itemListQuery = mFirebaseDatabase.getReference().child("catalog").child(category)
                .limitToLast(mPageLimit).startAt(mPageEndOffset);



        ChildEventListener videosChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String date = dataSnapshot.getKey();
                String temp = date;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.d(tag, "database error");
            }
        };


        ValueEventListener itemValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Item item = (Item) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(tag, "database error");
            }
        };
//        videosQuery.addChildEventListener(videosChildEventListener);
        itemListQuery.addValueEventListener(itemValueEventListener);

    }


    /**
     * A dummy item representing a piece of content.
     */


}