package com.qulix.gifsearcher.model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GifLoadUrls extends Thread {

    private GifLoadUrlsInterface contract;

    private OkHttpClient client = new OkHttpClient();

    private String ApiKey = "mjNGm1elmbKf8SRUWVoewT0V8txTGaCF";
    private String searchText = null;
    private int limitUrls = 25;

    private boolean loadTrend;

    public GifLoadUrls(GifLoadUrlsInterface contract, String searchText){
        this.contract = contract;
        this.searchText = searchText;
        this.loadTrend = false;
    }

    public GifLoadUrls(GifLoadUrlsInterface contract){
        this.loadTrend = true;
        this.contract = contract;
    }

    @Override
    public void run() {
        super.run();

        String url;
        if(loadTrend)
            url = "http://api.giphy.com/v1/gifs/trending?api_key="+ApiKey+"&limit="+limitUrls;
        else
            url = "http://api.giphy.com/v1/gifs/search?q="+searchText+"&api_key="+ApiKey+"&limit="+limitUrls;

        ArrayList<String> urls = getListUrlsGIF(url);

        contract.returnResult(urls);
    }


    private ArrayList<String> getListUrlsGIF(String url){

        ArrayList<String> urls = new ArrayList<>();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();

            String responseBody = response.body().string();
            if(responseBody != null) {
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++)
                    urls.add(jsonArray  .getJSONObject(i)
                                        .getJSONObject("images")
                                        .getJSONObject("fixed_height")
                                        .getString("url"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return urls;
    }

}
