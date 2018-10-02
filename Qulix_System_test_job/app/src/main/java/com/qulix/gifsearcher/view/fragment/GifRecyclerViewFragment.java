package com.qulix.gifsearcher.view.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qulix.gifsearcher.R;
import com.qulix.gifsearcher.view.adapter.GifRecyclerViewAdapter;
import com.qulix.gifsearcher.model.GifModel;
import com.qulix.gifsearcher.model.GifModelInterface;
import com.qulix.gifsearcher.model.GifRecyclerViewInterface;

import java.util.ArrayList;

public class GifRecyclerViewFragment extends Fragment implements GifModelInterface, Handler.Callback, SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private GifRecyclerViewAdapter adapter;
    private ArrayList<String> urls;
    private GifRecyclerViewInterface viewModel;
    private Handler handler;
    private RecyclerView.LayoutManager layoutManager;

    private final String LIST_STATE_KEY = "listState";

    public GifRecyclerViewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gif_recycler_view, container, false);
        setHasOptionsMenu(true);
        setDefaultSettingsActionBar("Giphy");
        initialComponents(view,savedInstanceState);
        return view;
    }

    private void setDefaultSettingsActionBar(String title){
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(title);
    }

    private void initialComponents(View view,Bundle savedInstanceState){
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_gif);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        urls = new ArrayList<>();
        if(savedInstanceState != null){
            urls.addAll(savedInstanceState.getStringArrayList(LIST_STATE_KEY));
        }

        adapter = new GifRecyclerViewAdapter(getContext(),urls);
        recyclerView.setAdapter(adapter);
        viewModel = new GifModel(this);
        if(urls.isEmpty())
            viewModel.getListUrlsTrending();
        handler = new Handler(this);
    }

    private void updateUrls(){
        adapter.addUrls(urls);
    }

    private void onClickSearchGif(String text){
        String searchText = text.replaceAll(" ","+");
        viewModel.getListUrlsByName(searchText);
    }

    @Override
    public void returnUrls(ArrayList<String> urls) {
        if(urls != null){
            this.urls = urls;
            handler.sendEmptyMessage(0);
        }else {
            handler.sendEmptyMessage(1);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if(msg.what == 0){
            updateUrls();
            return true;
        }
        if(msg.what == 1){
            Toast.makeText(getContext(),"Отсутствует интеренет соединение!",Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        onClickSearchGif(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(LIST_STATE_KEY,urls);
    }
}
