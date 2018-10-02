package com.qulix.gifsearcher.model;


import java.util.ArrayList;

public class GifModel implements GifLoadUrlsInterface, GifRecyclerViewInterface {

    private GifModelInterface contract;
    private Thread threadGetUrls;

    public GifModel (GifModelInterface contract){
        this.contract = contract;
    }

    @Override
    public void returnResult(ArrayList<String> urls) {
        contract.returnUrls(urls);
    }

    //trend's
    @Override
    public void getListUrlsTrending() {
        threadGetUrls = new GifLoadUrls(this);
        threadGetUrls.start();
    }

    //search
    @Override
    public void getListUrlsByName(String name) {
        if(name != null){
            threadGetUrls = new GifLoadUrls(this,name);
            threadGetUrls.start();
        }
    }
}
