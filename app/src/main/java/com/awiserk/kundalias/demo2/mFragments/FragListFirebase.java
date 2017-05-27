package com.awiserk.kundalias.demo2.mFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.awiserk.kundalias.demo2.MyItemRecyclerViewAdapter;
import com.awiserk.kundalias.demo2.R;
import com.awiserk.kundalias.demo2.data.Item;
import com.awiserk.kundalias.demo2.utils.GridSpacingItemDecorator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Abhishek on 4/12/2017.
 */

public class FragListFirebase extends android.support.v4.app.Fragment{
    private static final int TOTAL_ITEM_EACH_LOAD = 5;
    private static final String TAG = "Fraglist" ;
    static String DB_URL = null;
    private static String mCategory;
    final List<Item> itemList = new ArrayList<>();
    private RecyclerView rv;
    private View v;
    private DatabaseReference mFirebaseDatabase, mDatabase;
    private MyItemRecyclerViewAdapter mAdapter;
    private int currentPage = 0;
    private ProgressBar mProgressBar;
    private RecyclerView recyclerView;

    public FragListFirebase() {
        super();
    }

    public static FragListFirebase newInstance(String category) {
        FragListFirebase fragList = new FragListFirebase();
        mCategory = category;
        return fragList;
    }

    @Override
    public String toString() {
        return mCategory;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.frag_list, container, false);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v = view;
        init();
        mProgressBar.setVisibility(View.VISIBLE);
        loadData(mCategory);
    }

    private void init()
    {
        //Reference
        rv = (RecyclerView) v.findViewById(R.id.list_rv);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progressbarloading);


        //Layout Manager to display items in reverse order i.e. newest first
        GridLayoutManager mLayoutManager = new GridLayoutManager(this.getActivity(), GridSpacingItemDecorator.calculateNoOfColumns(this.getActivity()));
        rv.setLayoutManager(mLayoutManager);

        //Decorator and animator add with 10 spacing in DP
        rv.addItemDecoration(new GridSpacingItemDecorator(this.getActivity(), GridSpacingItemDecorator.calculateNoOfColumns(this.getActivity()), 10, true));
        rv.setItemAnimator(new DefaultItemAnimator());


        mAdapter = new MyItemRecyclerViewAdapter(getActivity(), itemList);

        //Adapter
        rv.setAdapter(mAdapter);
        /*rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                loadMoreData(mCategory);
            }
        });*/

    }

    private void loadData(String category) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("catalog").child(mCategory);
        /*
        .limitToFirst(TOTAL_ITEM_EACH_LOAD)
                .startAt(currentPage * TOTAL_ITEM_EACH_LOAD)
         */
        mDatabase.limitToLast(TOTAL_ITEM_EACH_LOAD)
                .orderByChild("timestamp")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                                    Log.d(TAG, "Value is: " + newPost);
                        if (!dataSnapshot.hasChildren()) {
                            Toast.makeText(getContext(), "You have reached the End!", Toast.LENGTH_SHORT).show();
                            currentPage--;
                        }
                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                            Log.d(TAG, "Value is: " + data);
                            Item item = data.getValue(Item.class);
                            Log.d(TAG, "ITEM is: " + item);
                            itemList.add(item);
                            mAdapter.notifyDataSetChanged();
                        }
                        mProgressBar.setVisibility(RecyclerView.GONE);
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        mProgressBar.setVisibility(RecyclerView.GONE);
                    }
                });

        // Read from the database
       /* mDatabase.limitToLast(2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
                Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();

                Log.d(TAG, "Value is: " + newPost);
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Log.d(TAG, "Value is: " + data);
                    Item item = data.getValue(Item.class);
                    Log.d(TAG, "ITEM is: " + item);
                    itemList.add(item);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/
    }

    private void loadMoreData(String category) {
        currentPage++;
        loadData(category);
        mProgressBar.setVisibility(RecyclerView.VISIBLE);
    }

}
