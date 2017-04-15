package com.awiserk.kundalias.demo2.mFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awiserk.kundalias.demo2.Data.DataProvider;
import com.awiserk.kundalias.demo2.MyItemRecyclerViewAdapter;
import com.awiserk.kundalias.demo2.R;
import com.awiserk.kundalias.demo2.Utils.GridSpacingItemDecorator;

/**
 * Created by Abhishek on 4/12/2017.
 */

public class FragList2 extends android.support.v4.app.Fragment {
    private RecyclerView rv;

    public static FragList2 newInstance() {
        FragList2 fragList2 = new FragList2();
        return fragList2;
    }

    @Override
    public String toString() {
        return "Frag_list2";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.frag_list2, null);

        //Reference
        rv = (RecyclerView) rootview.findViewById(R.id.list2_rv);

        //Layout Manager
        rv.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));

        //Decorator and animator add with 10 spacing in DP
        rv.addItemDecoration(new GridSpacingItemDecorator(this.getActivity(), 2, 10, true));
        rv.setItemAnimator(new DefaultItemAnimator());

        //Adapter
        rv.setAdapter(new MyItemRecyclerViewAdapter(getActivity(), DataProvider.getITEMS()));
        return rootview;
    }
}
