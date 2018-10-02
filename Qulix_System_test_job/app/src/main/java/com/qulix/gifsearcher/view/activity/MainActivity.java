package com.qulix.gifsearcher.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.qulix.gifsearcher.R;
import com.qulix.gifsearcher.view.fragment.GifRecyclerViewFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            GifRecyclerViewFragment blankFragment = new GifRecyclerViewFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_container,blankFragment);
            fragmentTransaction.addToBackStack(blankFragment.getTag());
            fragmentTransaction.commit();
        }

    }
}
