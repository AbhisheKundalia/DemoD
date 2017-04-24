package com.awiserk.kundalias.demo2.mFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.awiserk.kundalias.demo2.Data.DataProvider;
import com.awiserk.kundalias.demo2.MyItemRecyclerViewAdapter;
import com.awiserk.kundalias.demo2.R;
import com.awiserk.kundalias.demo2.Utils.GridSpacingItemDecorator;

/**
 * Created by Abhishek on 4/12/2017.
 */

public class FragList1 extends android.support.v4.app.Fragment {
    private RecyclerView rv;

    public FragList1() {
        super();
    }

    public static FragList1 newInstance() {
        FragList1 fragList1 = new FragList1();
        return fragList1;
    }

    @Override
    public String toString() {
        return "Frag_list1";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.frag_list1, null);

        //Reference
        rv = (RecyclerView) rootview.findViewById(R.id.list1_rv);

        //Layout Manager
        rv.setLayoutManager(new GridLayoutManager(this.getActivity(), GridSpacingItemDecorator.calculateNoOfColumns(this.getActivity())));

        //Decorator and animator add with 10 spacing in DP
        rv.addItemDecoration(new GridSpacingItemDecorator(this.getActivity(), GridSpacingItemDecorator.calculateNoOfColumns(this.getActivity()), 10, true));
        rv.setItemAnimator(new DefaultItemAnimator());

        //Adapter
        rv.setAdapter(new MyItemRecyclerViewAdapter(getActivity(), DataProvider.getITEMS()));
        return rootview;
    }

}