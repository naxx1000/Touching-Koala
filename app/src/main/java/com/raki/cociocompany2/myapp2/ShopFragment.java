package com.raki.cociocompany2.myapp2;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ShopFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop, container, false);
        Button UpgradeDuckLeaf = (Button)view.findViewById(R.id.UpgradeDucksLeaf);
        // Inflate the layout for this fragment

        if(MainActivity1.DuckWorker){
            UpgradeDuckLeaf.setBackgroundResource(R.drawable.burnedleaf);
        }
        return view;
    }
}
