package com.example.matt.calisthenicsappv1.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.matt.calisthenicsappv1.Adapters.*;
import com.example.matt.calisthenicsappv1.R;

/**
 * Created by Matt on 11/08/2017.
 */

public class SelectObjectsTab3Fragment extends Fragment {
    private static final String TAG = "SelectObjectsTab3Fragment";

    String[] exercisesList = {"Sit-up", "Crunch", "Leg raise", "Knee Raise"};

    private Button btrTEST;
    private ListView lvTEST;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment3,container,false);

        //btrTEST = (Button) view.findViewById(R.id.button3);

        ListAdapter myListAdapter = new SelectObjCustomAdapter3(getContext(), exercisesList);
        lvTEST = (ListView) view.findViewById(R.id.listview3);
        lvTEST.setAdapter(myListAdapter);


        return view;
    }
}
