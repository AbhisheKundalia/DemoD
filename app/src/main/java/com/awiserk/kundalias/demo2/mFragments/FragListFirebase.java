package com.awiserk.kundalias.demo2.mFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.awiserk.kundalias.demo2.utils.EndlessRecyclerOnScrollListener;
import com.awiserk.kundalias.demo2.MyItemRecyclerViewAdapter;
import com.awiserk.kundalias.demo2.R;
import com.awiserk.kundalias.demo2.data.Item;
import com.awiserk.kundalias.demo2.utils.GridSpacingItemDecorator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private AVLoadingIndicatorView mAnimationProgress;
    private RecyclerView recyclerView;
    private static final long INITTIME = 240;
    private long timestamp = INITTIME;
    public long ptimestamp = timestamp;

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
        //mProgressBar.setVisibility(View.VISIBLE);
        mAnimationProgress.smoothToShow();
        loadDataController();
    }

    private void init()
    {
        //Reference
        rv = (RecyclerView) v.findViewById(R.id.list_rv);
        //mProgressBar = (ProgressBar) v.findViewById(R.id.progressbarloading);
        mAnimationProgress = (AVLoadingIndicatorView) v.findViewById(R.id.progressbarloadingC) ;

        //Layout Manager to display items in reverse order i.e. newest first
        GridLayoutManager mLayoutManager = new GridLayoutManager(this.getActivity(), GridSpacingItemDecorator.calculateNoOfColumns(this.getActivity()));
        rv.setLayoutManager(mLayoutManager);

        //Decorator and animator add with 10 spacing in DP
        rv.addItemDecoration(new GridSpacingItemDecorator(this.getActivity(), GridSpacingItemDecorator.calculateNoOfColumns(this.getActivity()), 10, true));
        rv.setItemAnimator(new DefaultItemAnimator());


        mAdapter = new MyItemRecyclerViewAdapter(getActivity(), itemList);

        //Adapter
        rv.setAdapter(mAdapter);
        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                loadMoreData();
            }
        });

    }

    private void loadDataController() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("catalog").child(mCategory);
        /*
        .limitToFirst(TOTAL_ITEM_EACH_LOAD)
                .startAt(currentPage * TOTAL_ITEM_EACH_LOAD)
         */
        if(timestamp == INITTIME)
        {
            mDatabase.getRoot().child("timestamp").child(mCategory).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    timestamp = Long.parseLong(dataSnapshot.getValue().toString());
                    Log.d("TIMESTAMP1", timestamp+"");
                    loadData();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else
        {
            loadData();
        }
    }

    private void loadData() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("catalog").child(mCategory);
        if(timestamp != INITTIME)
        {
            Query query = mDatabase.limitToFirst(TOTAL_ITEM_EACH_LOAD)
                    .startAt(timestamp)
                    .orderByChild("timestamp");
            Log.d("TIMESTAMP2", timestamp+"");
            // Read from the database
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                        //Toast.makeText(getContext(), "You have reached the End!", Toast.LENGTH_SHORT).show();
                    if(mAnimationProgress.getVisibility() == View.VISIBLE)
                    {
                        mAnimationProgress.smoothToHide();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

                    query.addChildEventListener(new ChildEventListener() {
                        public boolean removed = false;

                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            if(!removed)
                            {
                               /* Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                                Log.d(TAG, "Value is: " + newPost);
                                */
                               if(mAnimationProgress.getVisibility() != View.VISIBLE)
                                {
                                    mAnimationProgress.smoothToShow();
                                }
                                //Log.d(TAG, "Value is: " + data);
                                Item item = dataSnapshot.getValue(Item.class);
                                //timestamp = item.getTimestamp();
                                //Log.d(TAG, "\nITEM is: " + item.getTimestamp());
                                itemList.add(item);
                                mAdapter.notifyDataSetChanged();

                            }

                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            Item item = dataSnapshot.getValue(Item.class);
                            for (Item object : itemList) {
                                if (object.getName().equals(item.getName())) {
                                    itemList.set(itemList.indexOf(object), item);
                                    mAdapter.notifyDataSetChanged();
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                            Log.d(TAG, "Removed is: " + newPost);
                            removed =true;
                            Item item = dataSnapshot.getValue(Item.class);
                            for (Item object : itemList) {
                                if (object.getName().equals(item.getName())) {
                                    itemList.remove(itemList.indexOf(object));
                                    mAdapter.notifyDataSetChanged();
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });/*
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

                                //Log.d(TAG, "Value is: " + data);
                                Item item = data.getValue(Item.class);
                                //timestamp = item.getTimestamp();
                                //Log.d(TAG, "\nITEM is: " + item.getTimestamp());
                                itemList.add(item);
                                mAdapter.notifyDataSetChanged();
                            }
                            mProgressBar.setVisibility(RecyclerView.GONE);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            mProgressBar.setVisibility(RecyclerView.GONE);
                        }
                    });*/
        }

    }

    private void loadMoreData() {
        timestamp = itemList.get(itemList.size() - 1).getTimestamp() + 1;
        loadDataController();
        mAnimationProgress.smoothToShow();
        //mProgressBar.setVisibility(RecyclerView.VISIBLE);
    }

}
