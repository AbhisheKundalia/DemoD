package com.awiserk.kundalias.demo2.data;

import com.awiserk.kundalias.demo2.R;

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
    private static final String urlNavHeaderBg = "https://scontent.fblr2-1.fna.fbcdn.net/v/t1.0-9/17155643_604619189727656_6470350059459558370_n.jpg?oh=1cbf62b6ed6d76c6e18e81449ec21aee&oe=5981EFD8";
    private static final String urlProfileImg = "https://scontent.fblr2-1.fna.fbcdn.net/v/t1.0-9/17155643_604619189727656_6470350059459558370_n.jpg?oh=1cbf62b6ed6d76c6e18e81449ec21aee&oe=5981EFD8";
    private static final String userName = "Abhishek Kundalia";
    private static final String userEmail = "abhishekundalia@gmail.com";
    private static final String cat1CoverImg = "https://img.clipartfox.com/6a7ba8a6429e1b6ae50adb34acee6957_nature-google-the-vineyard-google-clipart-nature_1600-1200.jpeg";
    private static final String cat2CoverImg = "http://www.pageresource.com/wallpapers/wallpaper/beautiful-love-nature.jpg";
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
    public static String[] ringsSizes = new String[]{"xl","abc", "asd", "adf"};

    public static String[] banglesSizes = new String[]{"xjbl","abc", "asd", "adf", "asdf", "efw23"};

    public static String[] chainsSizes = new String[]{"xl","abc", "asd", "adf", "asewe2", "q"};

    public static String[] necklaceSizes = new String[]{"xl","abc", "asd", "adf"};

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

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

    private static Item createDummyItem(int position) {
        return new Item(String.valueOf(position), (int) (position * 1000000 + 7), covers[position]);
    }

    private static void writeOnline(Item item)
    {

    }

    public static List<Item> getITEMS() {
        return ITEMS;
    }


    /**
     * A dummy item representing a piece of content.
     */

    public static class Item {
        private String id;
        private int cost;
        private int thumbnail;

        public Item() {
        }

        public Item(String id, int cost, int thumbnail) {
            this.id = id;
            this.cost = cost;
            this.thumbnail = thumbnail;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCost() {
            return Integer.toString(cost);
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public int getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(int thumbnail) {
            this.thumbnail = thumbnail;
        }
    }
}